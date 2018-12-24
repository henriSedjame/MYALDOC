package org.myaldoc.core.constraints.validators;

import org.myaldoc.core.constraints.Majeur;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Objects;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 24/12/2018
 * @Class purposes : .......
 */
public class MajeurConstraintValidator implements ConstraintValidator<Majeur, ChronoLocalDate> {

    public boolean isValid(ChronoLocalDate date, ConstraintValidatorContext context) {

        if (Objects.isNull(date)) return false;

        return date.isBefore(LocalDate.now());
    }
}
