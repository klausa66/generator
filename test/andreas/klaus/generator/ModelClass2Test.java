package andreas.klaus.generator;

import andreas.klaus.generator.annotations.ModelClass;
import andreas.klaus.generator.annotations.ModelMethod;

@ModelClass
public class ModelClass2Test
{
	private String deppString;
	private String noDeppString;
    private boolean kack;
    @ModelMethod
    public boolean isKack() {
        return kack;
    }
    @ModelMethod
    public void setKack(boolean sack) {
        this.kack = sack;
    }
    @ModelMethod
	public String getDeppString()
	{
		return deppString;
	}
	@ModelMethod
	public void setDeppString(String deppString)
	{
		this.deppString = deppString;
	}
	public String getNoDeppString()
	{
		return noDeppString;
	}
	public void setNoDeppString(String noSeppString)
	{
		this.noDeppString = noSeppString;
	}
	
	@ModelMethod
	public String getReadWriteString(){
	    return "";
	}
	
}
