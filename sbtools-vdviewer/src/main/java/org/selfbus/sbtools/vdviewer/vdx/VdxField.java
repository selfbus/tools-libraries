package org.selfbus.sbtools.vdviewer.vdx;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Describes a field in a VD_ file.
 */
@Target({METHOD, FIELD}) 
@Retention(RUNTIME)
public @interface VdxField
{
   /**
    * The name of the field in a VD_ file.
    */
   public String name();
}
