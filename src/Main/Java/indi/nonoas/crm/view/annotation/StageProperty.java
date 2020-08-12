package indi.nonoas.crm.view.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface StageProperty {

    String title() default "JavaFX´°¿Ú";

    String icons() default "";
}
