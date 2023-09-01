package edu.polo.ghostkitchen.validations;

import edu.polo.ghostkitchen.dto.*;
import jakarta.validation.*;

public class ConfirmValidator implements ConstraintValidator<Confirm, Object> {

    @Override
    public void initialize(final Confirm constraintAnnotation) {

    }

    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext context) {
        if (object instanceof RegisterDto) {
            final RegisterDto register = (RegisterDto) object;
            return register.getPassword().equals(register.getConfirm());
        } else if (object instanceof RegisterDeliveryDto) {
            final RegisterDeliveryDto registerDelivery = (RegisterDeliveryDto) object;
            return registerDelivery.getPassword().equals(registerDelivery.getConfirm());
        }

        return true;
    }
}
