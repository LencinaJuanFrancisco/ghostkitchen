package edu.polo.ghostkitchen.validations;

import edu.polo.ghostkitchen.dto.*;
import jakarta.validation.*;

public class ConfirmValidator implements ConstraintValidator<Confirm, Object> {

    @Override
    public void initialize(final Confirm constraintAnnotation) {

    }

    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext context) {
        final RegisterDto register = (RegisterDto) object;
        boolean isVal = register.getPassword().equals(register.getConfirm());

        if (!isVal) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode("confirm").addConstraintViolation();
        }

        return isVal;
    }
}
