package org.myaldoc.core.constraints;

import org.myaldoc.core.constraints.validators.MajeurConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 24/12/2018
 * @Class purposes : .......
 */
@Constraint(validatedBy = MajeurConstraintValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Majeur {
    String message() default "Le sujet doit être majeur.";

    Class<?>[] group() default {};

    Class<? extends Payload>[] payload() default {};
}
