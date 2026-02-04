package sithmcfly.movies.utils.validation.MaxYear;

import java.time.LocalDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// Se nombra la validación <ValidYear> y tipo de dato que va a validar
public class YearValidator implements ConstraintValidator<MaxYear, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context){
        if(value == null) 
            return true;
        return value <= LocalDate.now().getYear();
    }
}
