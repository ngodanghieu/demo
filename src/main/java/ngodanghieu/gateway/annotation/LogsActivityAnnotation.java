package ngodanghieu.gateway.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogsActivityAnnotation {
        String message() default "Audit Message";
}
