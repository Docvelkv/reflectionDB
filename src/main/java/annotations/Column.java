package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

    /**
     * Получение имени аннотации
     * @return String
     */
    String name();

    /**
     * Получение данных о PrimaryKey
     * @return true or false
     */
    boolean primaryKey() default false;
}