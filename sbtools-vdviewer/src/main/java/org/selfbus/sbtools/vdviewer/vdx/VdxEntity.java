package org.selfbus.sbtools.vdviewer.vdx;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Describes a table / entity in a VD_ file. If not present, the
 * name of the @Entity annotation is taken.
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface VdxEntity
{
   /**
    * The name of the table / entity in a VD_ file.
    */
   public String name();
}
