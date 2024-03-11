package co.com.bancolombia.model.exception;

import co.com.bancolombia.model.exception.message.BusinessErrorMessage;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private final BusinessErrorMessage businessErrorMessage;

    public BusinessException(BusinessErrorMessage businessErrorMessageIn) {
        super(businessErrorMessageIn.getReason());
        this.businessErrorMessage = businessErrorMessageIn;
    }

    public BusinessException(Throwable cause, String errorMessage, BusinessErrorMessage businessErrorMessage) {
        super(errorMessage, cause);
        this.businessErrorMessage = businessErrorMessage;
    }
}
