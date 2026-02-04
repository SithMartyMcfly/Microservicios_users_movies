package sithmcfly.movies.utils.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;;

@Target({ElementType.FIELD}) // La usamos en los campos
@Retention(RetentionPolicy.RUNTIME) // Se mantiene en tiempo de ejecución
@Constraint(validatedBy = YearValidator.class) // Clase que valida
public @interface ValidYear {
    // Mensaje de error
    String message() default "El año no puede ser mayour que el actual";
    // Standard
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};

}
