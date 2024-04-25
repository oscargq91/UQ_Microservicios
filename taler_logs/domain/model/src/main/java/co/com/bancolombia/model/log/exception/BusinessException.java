package co.com.bancolombia.model.log.exception;


import co.com.bancolombia.model.log.exception.message.ErrorMessage;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorMessage errorMessage;

    public BusinessException(ErrorMessage businessErrorMessageIn) {
        super(businessErrorMessageIn.getReason());
        this.errorMessage = businessErrorMessageIn;
    }
    }


