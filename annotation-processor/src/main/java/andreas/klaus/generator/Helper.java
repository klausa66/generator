package andreas.klaus.generator;

public class Helper {

	/**
	 * wandelt das erste Zeichen in einen Kleinbuchstaben um 
	 * @param s
	 * @return umgewandelten String
	 */
	protected  String firstCharToLowerCase(String s)
	{
		String first=s.substring(0, 1);
		String last=s.substring(1, s.length());
		return first.toLowerCase()+last;
		
	}
	
	protected String toUpperWithUnderScore(String s)
	{
		String ret=s.replaceAll("([^_])([A-Z])", "$1_$2");
		ret=ret.toUpperCase();
		return ret;
	}	

	/**
	 * wandelt das erste Zeichen in einen Grossbuchstaben um 
	 * @param s
	 * @return umgewandelten String
	 */
	protected  String firstCharToUpperCase(String s)
	{
		String first=s.substring(0, 1);
		String last=s.substring(1, s.length());
		return first.toUpperCase()+last;
		
	}

    protected  boolean getTagTrue(String annotationString,String tag)
    {
        int index = annotationString.indexOf(tag);
        if (index<0) return false;
        String value= annotationString.substring(index+tag.length(), annotationString.length());
//        System.out.println("knoke:"+value);
        if(value.startsWith("true")) return true;
        return false;
    }


}
