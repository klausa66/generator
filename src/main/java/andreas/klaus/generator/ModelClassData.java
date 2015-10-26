package andreas.klaus.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeSingleton;


public class ModelClassData {
	private final String packageName;
	private final String className;
	private final boolean noPojo;
	private final SortedMap<String,ModelMethodData> methodProps;
	public ModelClassData(String className,
			String packageName, boolean noPojo,
			 SortedMap<String,ModelMethodData> methodProps) {
		super();
		this.packageName = packageName;
		this.className = className;
		this.noPojo = noPojo;
		this.methodProps = methodProps;
	}
	public String getClassName() {
		return className;
	}
	public String getJGMClassName() {
		return className+"JGM";
	}
	public String getQualifiedClassName() {
		return packageName+"."+className;
	}
	public String getPackageName() {
		return packageName;
	}
	public String getPackageJGMName() {
		return packageName+".jgmodel";
	}
	public boolean isNoPojo() {
		return noPojo;
	}
	public  List<ModelMethodData> getMethodProps() {
		List<ModelMethodData> ret= new ArrayList<ModelMethodData>();
		Iterator<ModelMethodData> it= methodProps.values().iterator();
		while (it.hasNext()) {
			ModelMethodData modelMethodData = (ModelMethodData) it.next();
			ret.add(modelMethodData);
		}
		return ret;
	}
	
	public String getPackageJGMDirectory()
	{
		String packageString=getPackageJGMName();
		return packageString.replaceAll("\\.", "/");
	}
	
	
	public static void main(String[] args) {
		ModelMethodData md1= new ModelMethodData("boolean", "dirty");
		md1.setRead(true);
		md1.setWrite(true);
		ModelMethodData md2= new ModelMethodData("java.lang.String", "firstName");
		md1.setRead(true);
		md1.setWrite(true);
		TreeMap<String,ModelMethodData> methods = new TreeMap<String, ModelMethodData>();
		methods.put(md1.getName(),md1);
		methods.put(md2.getName(),md2);
		ModelClassData clazz=new ModelClassData("Test1", "sepp.depp.dumm", true, methods);
		
		try
		{
			Velocity.setProperty(Velocity.RESOURCE_LOADER, "classpath");
			Velocity.setProperty("classpath." + Velocity.RESOURCE_LOADER + ".class",
					"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

			Velocity.setProperty("classpath." + Velocity.RESOURCE_LOADER + ".cache", "false");

			Velocity.setProperty("classpath." + Velocity.RESOURCE_LOADER
					+ ".modificationCheckInterval", "2");
			Velocity.init();
			VelocityContext context = new VelocityContext();
			Template template = RuntimeSingleton.getTemplate("templates/JGModel.vm");
			context.put("clazz", clazz);
			context.put("properties", clazz.getMethodProps());
			StringWriter sw = new StringWriter();
			template.merge(context, sw);
			String destDir="generated";
			//System.out.println(sw.toString());
			String fn=destDir+File.separator+ clazz.getPackageJGMDirectory()+File.separator+clazz.getJGMClassName()+".java";
			System.out.println(fn);
			File dir = new File(destDir+File.separator+ clazz.getPackageJGMDirectory());
			dir.mkdirs();
			File f = new File(destDir+File.separator+ clazz.getPackageJGMDirectory()+File.separator+clazz.getJGMClassName()+".java");
			FileWriter fw = new FileWriter(f);
			fw.write(sw.toString());
			fw.close();

		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
