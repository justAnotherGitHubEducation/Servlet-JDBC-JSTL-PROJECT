package main.com.simpleonlineshop.validator;

public interface Validator <T>{

    ValidationResult isValid(T object);
}


