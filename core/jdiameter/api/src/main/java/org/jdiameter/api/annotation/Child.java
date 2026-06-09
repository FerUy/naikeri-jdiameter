package org.jdiameter.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation allow describe information about child of command / group avp
 *
 * @author erick.svenson@yahoo.com
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Child {

  /**
   * Return points to child class or interface with AvpDscr annotation
   * @return points to child class or interface with AvpDscr annotation
   */
  Class<?> ref();

  /**
   * Return child position
   * @return child position
   */
  int pos() default -1;

  /**
   * Return minimum count of this child in parent avp set
   * @return  minimum count of this child in parent avp set
   */
  int min() default 0;

  /**
   * Return maximum count of this child in parent avp set
   * @return maximum count of this child in parent avp set s
   */
  int max() default 1;
}