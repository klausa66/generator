package andreas.klaus.generator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated class is a class to base a JGoodies-Model
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface ModelClass { 
    boolean nopojo() default false;
}