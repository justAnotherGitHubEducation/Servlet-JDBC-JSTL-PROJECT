package main.com.simpleonlineshop.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import main.com.simpleonlineshop.dto.CreateItemDto;
import main.com.simpleonlineshop.util.LocalDateFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateItemValidator implements Validator<CreateItemDto>{

    private static final CreateItemValidator INSTANCE = new CreateItemValidator();
    public static CreateItemValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(CreateItemDto object) {
        ValidationResult validationResult = new ValidationResult();

        if(object.getQuantity().isEmpty())
            validationResult.add( Error.of("invalid.Quantity","Quantity is invalid"));

        return  validationResult;
    }
}
