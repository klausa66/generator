package andreas.klaus.generator;

public class ModelMethodData extends Helper{
	private final String type;
	private final String name;
	private boolean read=false;
	private boolean write=false;
	
	public ModelMethodData(String type, String name) {
		super();
		this.type = type;
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public boolean isRead() {
		return read;
	}
	public void setRead(boolean read) {
		this.read = read;
	}
	public boolean isWrite() {
		return write;
	}
	public void setWrite(boolean write) {
		this.write = write;
	}
	
	public String getterMethodName()
	{
		String prefix="get";
		if(this.type.equals("boolean"))
		{
			prefix="is";
		}
		return prefix+ firstCharToUpperCase(this.name);
	}
	public String setterMethodName()
	{
		return "set"+ firstCharToUpperCase(this.name);
	}
	
	public String getConstantName()
	{
		String post=toUpperWithUnderScore(name);
		return "PROPERTYNAME_"+post;
	}
	public String getConstantValue()
	{
		return name;
	}
	

}
