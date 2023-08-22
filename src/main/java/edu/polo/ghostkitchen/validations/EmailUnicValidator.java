package edu.polo.ghostkitchen.validations;

import edu.polo.ghostkitchen.dto.*;
import edu.polo.ghostkitchen.repositories.*;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailUnicValidator implements ConstraintValidator<EmailUnic, Object> {

    @Autowired
    private GhostRepository userRepository;

    @Override
    public void initialize(final EmailUnic constraintAnnotation) {

    }

    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext context) {
        final RegisterDto register = (RegisterDto) object;
        boolean isVal = !userRepository.existsByEmail(register.getEmail());

        if (!isVal) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode("email").addConstraintViolation();
        }

        return isVal;
    }
}
