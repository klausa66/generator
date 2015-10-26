package andreas.klaus.generator;

public class ModelValidationFieldData extends Helper
{
	private final String type;
	private final String name;
	private final boolean notEmpty;
	private final boolean shortDateFormat;
	private final boolean integer;
	private final boolean currency;
	private final boolean gebueHCat;
	private final int length;
	
//	static private final String NOT_EMPTY_TAG="notEmpty=";
//	static private final String INTEGER_TAG="integer=";
//	static private final String SHORT_DATE_FORMAT_TAG="shortDateFormat=";
//	static private final String LENGTH_TAG="length=";
//	static private final String CURRENCY_TAG="currency=";
//	static private final String GEBUEH_CAT_TAG="gebueHCat=";
	

	public ModelValidationFieldData(String type, String name, boolean notEmpty,
			boolean shortDateFormat, boolean integer, boolean currency,
			boolean gebueHCat, int length) {
		super();
		this.type = type;
		this.name = name;
		this.notEmpty = notEmpty;
		this.shortDateFormat = shortDateFormat;
		this.integer = integer;
		this.currency = currency;
		this.gebueHCat = gebueHCat;
		this.length = length;
	}

	public String getType()
	{
		return type;
	}
	public String getName()
	{
		return name;
	}
	public String getNameFirstUpper()
	{
		return firstCharToUpperCase(name);
	}
	public boolean isNotEmpty()
	{
		return notEmpty;
	}
	public boolean isShortDateFormat()
	{
		return shortDateFormat;
	}
	public boolean isInteger()
	{
		return integer;
	}
	public int getLength()
	{
		return length;
	}
	
	public String getMethodName()
	{
		return  (type.equals("boolean")?"is":"get")+  firstCharToUpperCase(name);
	}
	public String setMethodName()
	{
		return  "set"+  firstCharToUpperCase(name);
	}
	

	
	@Override
	public String toString() {
		return "ValidationProperty [type=" + type + ", name=" + name
				+ ", notEmpty=" + notEmpty + ", shortDateFormat="
				+ shortDateFormat + ", integer=" + integer + ", currency="
				+ currency + ", gebueHCat=" + gebueHCat + ", length=" + length
				+ "]";
	}
	public boolean isCurrency()
	{
		return currency;
	}
	public boolean isGebueHCat()
	{
		return gebueHCat;
	}
}
