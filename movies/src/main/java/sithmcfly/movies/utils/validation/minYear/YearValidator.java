package sithmcfly.movies.utils.validation.minYear;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// Se nombra la validación <ValidYear> y tipo de dato que va a validar
public class YearValidator implements ConstraintValidator<MinYear, Integer> {
    private final int YEAR = 1888;
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context){
        if(value == null) 
            return true;
        return value > YEAR;
    }
}
