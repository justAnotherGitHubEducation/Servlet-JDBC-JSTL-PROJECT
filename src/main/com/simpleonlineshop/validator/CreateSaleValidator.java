package main.com.simpleonlineshop.validator;

import main.com.simpleonlineshop.dto.CreateSaleDto;
import main.com.simpleonlineshop.util.LocalDateFormatter;

public class CreateSaleValidator implements Validator<CreateSaleDto>{

    private static final CreateSaleValidator INSTANCE = new CreateSaleValidator();

    private CreateSaleValidator() {
    }

    public static CreateSaleValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(CreateSaleDto object) {

       ValidationResult validationResult = new ValidationResult();
       if(!LocalDateFormatter.isValid(object.getDate()))
           validationResult.add( Error.of("invalid.date","Sale date  is invalid"));

       if(object.getUser_id().isEmpty())
           validationResult.add( Error.of("invalid.user","User is invalid"));

       return  validationResult;
    }
}
