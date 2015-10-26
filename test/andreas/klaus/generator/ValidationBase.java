package andreas.klaus.generator;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Hilfsklasse beim testen
 * @author KlausA
 *
 */
public class ValidationBase
{
    public static String validateDateString(String dateStr) 
    {
            return null;
    }
    public static Calendar convertToCalendar(String dateStr) 
    {
        return null;
    }


    public static boolean daysMaxInFuture(Calendar dayRef,int days)
    {
        return true;
    }
    public static boolean daysMaxInPast(Calendar dayRef,int days)
    {
        return true;
    }
    
    /**
     * checks that day is not greater than refDay
     * @param refDay
     * @param day
     * @return true, if day is not greater than ref Day 
     */
    public static boolean dayNotGreaterThanRefDay(Calendar refDay,Calendar day)
    {
        return true;
    }
    /**
     * checks that day is not greater than refDay
     * @param refDay
     * @param day
     * @return true, if day is not greater than ref Day 
     */
    public static boolean dayMaxLowerYearsThanRefDay(Calendar refDay,Calendar day,int years)
    {
        return true;
    }
    
    
	public String getValidationMessage(String key)
	{
		return null;
	}
    public String getValidationKey(String key)
    {
		return null;
    }
    public int getValidationInt(String key,int defaultValue)
    {
		return 0;
    }
	public static String validatePrice(String text)
	{
		return null;
	}
	
	public static String preisToString(double preis)
	{
		return null;
	}
	public static String validateGebueHCat(String text) 
	{
		return null;
	}

	public static Calendar  heute()
	{
	    return null;
	}
	
	public static void setTimeOfDayToNullUhr(Calendar day){
        day.set(Calendar.HOUR_OF_DAY, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MILLISECOND, 0);
	}
}
