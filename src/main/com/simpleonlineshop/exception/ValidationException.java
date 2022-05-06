package main.com.simpleonlineshop.exception;

import lombok.Getter;
import main.com.simpleonlineshop.validator.Error;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends RuntimeException {

    @Getter
    private List<Error> errorList = new ArrayList<>();

    public ValidationException(List<Error> errorList) {

        this.errorList = errorList;

    }
}
