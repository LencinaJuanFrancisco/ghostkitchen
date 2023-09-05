package edu.polo.ghostkitchen.validations;

import edu.polo.ghostkitchen.dto.CategoryDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CategoryValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CategoryDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CategoryDto categoryDto = (CategoryDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "category", "field.required");

    }
}
