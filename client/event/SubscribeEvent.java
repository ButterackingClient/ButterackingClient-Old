package client.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SubscribeEvent {
  byte value() default 2;
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\event\SubscribeEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */