package indi.nonoas.crm.component.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface StageProperty {

    String title() default "JavaFX店铺管理系统";

    String icons() default "";
}
