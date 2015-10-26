package andreas.klaus.generator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated method is a bound property.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface ModelMethod { 
    boolean write() default false;

}