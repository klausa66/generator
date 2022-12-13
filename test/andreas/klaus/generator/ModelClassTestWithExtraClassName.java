package andreas.klaus.generator;

import andreas.klaus.generator.annotations.ModelClass;
import andreas.klaus.generator.annotations.ModelMethod;

@ModelClass(nopojo=true)
public class ModelClassTestWithExtraClassName
{
	private String seppString;
	private String noSeppString;
    private boolean sack;
    @ModelMethod
    public boolean isSack() {
        return sack;
    }
    @ModelMethod
    public void setSack(boolean sack) {
        this.sack = sack;
    }
    @ModelMethod
	public String getSeppString()
	{
		return seppString;
	}
	@ModelMethod
	public void setSeppString(String seppString)
	{
		this.seppString = seppString;
	}
	public String getNoSeppString()
	{
		return noSeppString;
	}
	public void setNoSeppString(String noSeppString)
	{
		this.noSeppString = noSeppString;
	}
	
	@ModelMethod
	public String getReadWriteString(){
	    return "";
	}
	
}
