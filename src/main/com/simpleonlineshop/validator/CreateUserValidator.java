package main.com.simpleonlineshop.validator;

import main.com.simpleonlineshop.dto.CreateUserDto;
import main.com.simpleonlineshop.entity.Role;
import main.com.simpleonlineshop.util.LocalDateFormatter;

public class CreateUserValidator implements Validator<CreateUserDto>{

    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    private  CreateUserValidator() {
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(CreateUserDto object) {

        ValidationResult validationResult = new ValidationResult();

        if(Role.find(object.getRole()).isEmpty())
            validationResult.add(Error.of("invalid.role","role is invalid"));

        if(!LocalDateFormatter.isValid(object.getBithday()))
            validationResult.add(Error.of("invalid.bithdayFomat","bithday is invalid"));

        if(object.getLogin().isEmpty())
            validationResult.add(Error.of("invalid.login","login is empty"));

        return validationResult;
    }
}
