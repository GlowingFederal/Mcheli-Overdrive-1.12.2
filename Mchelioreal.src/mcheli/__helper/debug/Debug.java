package mcheli.__helper.debug;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface Debug {
  String id() default "";
  
  String version();
  
  String target() default "";
}


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\debug\Debug.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */