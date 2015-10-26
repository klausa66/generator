package andreas.klaus.generator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated method is a bound property.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface ValidationField 
{
	boolean notEmpty() default true;
	boolean shortDateFormat() default false;
	boolean integer() default false;
	boolean currency() default false;
	boolean gebueHCat() default false;
	String regex() default "";
	int length() default 0;
}