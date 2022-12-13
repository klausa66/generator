package andreas.klaus.generator;

import java.io.Writer;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeSingleton;

import andreas.klaus.generator.annotations.ModelClass;
import andreas.klaus.generator.annotations.ModelMethod;
import andreas.klaus.generator.annotations.ValidationDefClass;
import andreas.klaus.generator.annotations.ValidationField;

@SupportedAnnotationTypes({"andreas.klaus.generator.annotations.ModelClass",
    "andreas.klaus.generator.annotations.ValidationDefClass"})
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class Generator extends AbstractProcessor {

  private String fqClassName = null;
  private String className = null;
  private String packageName = null;
  private boolean noPojo;
  // private Map<String, VariableElement> fields = new HashMap<String, VariableElement>();
  // private Map<String, ExecutableElement> methods = new HashMap<String, ExecutableElement>();
  private Types typeUtils;

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    typeUtils = processingEnv.getTypeUtils();
    doJGMAnnotations(roundEnv);
    doValidationDefClass(roundEnv);
    return true;
  }

  private void doJGMAnnotations(RoundEnvironment roundEnv) {
    for (Element elem : roundEnv.getElementsAnnotatedWith(ModelClass.class)) {
      ModelClass mc = elem.getAnnotation(ModelClass.class);
      noPojo = mc.nopojo();
      note("setting nopojo to " + noPojo, elem);
      TypeElement classElement = (TypeElement) elem;
      PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();

      note("annotated class: " + classElement.getQualifiedName(), elem);
      fqClassName = classElement.getQualifiedName().toString();
      className = classElement.getSimpleName().toString();
      packageName = packageElement.getQualifiedName().toString();
      List<? extends Element> children = elem.getEnclosedElements();
      SortedMap<String, ModelMethodData> modelMethodMap = new TreeMap<String, ModelMethodData>();
      for (Element child : children) {
        ModelMethod a = child.getAnnotation(ModelMethod.class);
        if (a != null) {
          ExecutableElement exeElement = (ExecutableElement) child;
          MethodProperties mp = new MethodProperties(exeElement, typeUtils);
          ModelMethodData moMeDa = mp.toModelMethodData();
          if (a.write() || noPojo) {
            moMeDa.setWrite(true);
          }
          // Register Property in Map
          ModelMethodData mmFromMap = modelMethodMap.get(moMeDa.getName());
          if (mmFromMap == null) {
            modelMethodMap.put(moMeDa.getName(), moMeDa);
          } else {
            // eventuell Modus ergaenzen
            if (moMeDa.isRead()) {
              mmFromMap.setRead(true);
            }
            if (moMeDa.isWrite()) {
              mmFromMap.setWrite(true);
            }
          }
          note(mp.toString(), exeElement);
        }

      }
      ModelClassData model = new ModelClassData(this.className, this.packageName, this.noPojo, modelMethodMap);
      this.generateJGM(model, elem);
    }
  }

  private void doValidationDefClass(RoundEnvironment roundEnv) {
    for (Element elem : roundEnv.getElementsAnnotatedWith(ValidationDefClass.class)) {
      TypeElement classElement = (TypeElement) elem;
      PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();

      note("annotated class: " + classElement.getQualifiedName(), elem);
      fqClassName = classElement.getQualifiedName().toString();
      className = classElement.getSimpleName().toString();
      packageName = packageElement.getQualifiedName().toString();
      List<? extends Element> children = elem.getEnclosedElements();
      SortedMap<String, ModelValidationFieldData> modelFieldMap = new TreeMap<String, ModelValidationFieldData>();
      for (Element child : children) {
        ValidationField a = child.getAnnotation(ValidationField.class);
        if (a != null) {
          VariableElement varEl = (VariableElement) child;
          ModelValidationFieldData mvfd =
              new ModelValidationFieldData(getParameterType(varEl), varEl.getSimpleName().toString(), a.notEmpty(),
                  a.shortDateFormat(), a.integer(), a.currency(), a.gebueHCat(), a.length());
          note(mvfd.toString(), varEl);
          modelFieldMap.put(mvfd.getName(), mvfd);
        }
      }
      ModelValidationDefClassData classData=new ModelValidationDefClassData(this.packageName, this.className,modelFieldMap);
      generateValidator(classData, elem);
    }
  }

  private String getParameterType(VariableElement ve) {
    TypeMirror typeMirror = ve.asType();
    if (typeMirror.getKind() == TypeKind.DECLARED) {
      Element element = typeUtils.asElement(typeMirror);
      // instanceof implies null-ckeck
      return element.toString();
    } else if (typeMirror.getKind().isPrimitive()) {
      return typeUtils.getPrimitiveType(typeMirror.getKind()).toString();
    }
    return "void";
  }


  private void generateJGM(ModelClassData model, Element curClassElement) {
    try {
      Velocity.setProperty(Velocity.RESOURCE_LOADER, "classpath");
      Velocity.setProperty("classpath." + Velocity.RESOURCE_LOADER + ".class",
          "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

      Velocity.setProperty("classpath." + Velocity.RESOURCE_LOADER + ".cache", "false");

      Velocity.setProperty("classpath." + Velocity.RESOURCE_LOADER + ".modificationCheckInterval", "2");
      Velocity.init();
      VelocityContext context = new VelocityContext();
      Template template = null;
      if (model.isNoPojo()) {
        template = RuntimeSingleton.getTemplate("templates/JGModelNoPojo.vm");

      } else {
        template = RuntimeSingleton.getTemplate("templates/JGModel.vm");

      }
      context.put("clazz", model);
      context.put("properties", model.getMethodProps());

      JavaFileObject jfo = processingEnv.getFiler().createSourceFile(packageName + ".jgmodel." + className + "JGM");

      processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "creating source file: " + jfo.toUri());

      Writer writer = jfo.openWriter();

      processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
          "applying velocity template: " + template.getName());

      template.merge(context, writer);

      writer.close();
      processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "ready");


    } catch (Exception e) {
      e.printStackTrace();
      note("Error Generating File "+model.getJGMClassName()+":" + e.getClass().getName() + " Message:" + e.getMessage(), curClassElement);
    }

  }

  private void generateValidator(ModelValidationDefClassData model, Element curClassElement) {
    try {
      Velocity.setProperty(Velocity.RESOURCE_LOADER, "classpath");
      Velocity.setProperty("classpath." + Velocity.RESOURCE_LOADER + ".class",
          "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

      Velocity.setProperty("classpath." + Velocity.RESOURCE_LOADER + ".cache", "false");

      Velocity.setProperty("classpath." + Velocity.RESOURCE_LOADER + ".modificationCheckInterval", "2");
      Velocity.init();
      VelocityContext context = new VelocityContext();
      Template template = RuntimeSingleton.getTemplate("templates/ValidationClass.vm");
      context.put("clazz", model);
      context.put("properties", model.getFieldProps());

      JavaFileObject jfo = processingEnv.getFiler().createSourceFile(packageName +"." + model.getValidatorClassname());

      processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "creating source file: " + jfo.toUri());

      Writer writer = jfo.openWriter();

      processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
          "applying velocity template: " + template.getName());

      template.merge(context, writer);

      writer.close();
      processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "ready");


    } catch (Exception e) {
      e.printStackTrace();
      note("Error Generating File "+ model.getValidatorClassname()+": "+ e.getClass().getName() + " Message:" + e.getMessage(), curClassElement);
    }

  }

  private void note(String msg, Element element) {
    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg, element);
  }
}
