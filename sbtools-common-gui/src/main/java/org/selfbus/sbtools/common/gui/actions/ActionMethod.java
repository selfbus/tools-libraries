package org.selfbus.sbtools.common.gui.actions;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.selfbus.sbtools.common.gui.misc.ImageCache;

/**
 * Annotation for an action method.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ActionMethod
{
   /**
    * The name of the action.
    */
   String name() default "";

   /**
    * The name of the action icon. The icon is accessed with {@link ImageCache#getIcon(String)}.
    */
   String icon() default "";
}
