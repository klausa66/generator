package andreas.klaus.generator;

import java.util.Iterator;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

class MethodProperties extends Helper
{
	private String type;
	private String name;
	private Types typeUtils;
	private boolean read=false;
	private boolean write=false;

	public MethodProperties(ExecutableElement exe, Types typeUtils) {
		this.typeUtils = typeUtils;
		String retTypeName = getMethodReturnType(exe);
		if (!retTypeName.equals("void")) {
			this.type = retTypeName;
		} else {
			List<? extends VariableElement> pars = exe.getParameters();
			Iterator<? extends VariableElement> it = pars.iterator();
			if (it.hasNext()) {
				VariableElement variableElement = it.next();
				type = getParameterType(variableElement);

			} else {
				throw new IllegalStateException("no Type found");
			}
		}
		String methodName = exe.getSimpleName().toString();
		methodNameToPropertyName(methodName);

	}

	private String methodNameToPropertyName(String methodName) {
		if (methodName.startsWith("get") || methodName.startsWith("set")
				|| methodName.startsWith("is")) {
			if(methodName.startsWith("set")){
				this.name=firstCharToLowerCase(methodName.substring(3));
				this.write=true;
			}
			if(methodName.startsWith("get")){
				this.name=firstCharToLowerCase(methodName.substring(3));
				this.read=true;
			}
			if(methodName.startsWith("is")){
				this.name=firstCharToLowerCase(methodName.substring(2));
				this.read=true;
			}
			return null;

		} else {
			return null;
		}
	}

	private String getParameterType(VariableElement ve) {
		TypeMirror typeMirror = ve.asType();
		if (typeMirror.getKind() == TypeKind.DECLARED) {
			Element element = typeUtils.asElement(typeMirror);
			// instanceof implies null-ckeck
			return element.toString();
		} else if (typeMirror.getKind().isPrimitive()) {
			return typeUtils.getPrimitiveType(typeMirror.getKind())
					.toString();
		}
		return "void";
	}

	private String getMethodReturnType(ExecutableElement method) {
		TypeMirror returnTypeMirror = method.getReturnType();
		if (returnTypeMirror.getKind() == TypeKind.DECLARED) {
			Element returnTypeElement = typeUtils.asElement(method
					.getReturnType());
			return returnTypeElement.toString();
		} else if (returnTypeMirror.getKind() == TypeKind.VOID) {
			return "void";

		} else {
			return typeUtils.getPrimitiveType(
					method.getReturnType().getKind()).toString();
		}

	}

	


	public void setRead(boolean read) {
		this.read = read;
	}

	public void setWrite(boolean write) {
		this.write = write;
	}

	@Override
	public String toString() {
		return "MethodProperties [type=" + type + ", name=" + name + "]";
	}
	
	public ModelMethodData toModelMethodData(){
		ModelMethodData ret= new ModelMethodData(type, name);
		ret.setRead(read);
		ret.setWrite(write);
		return ret;
	}
	
}