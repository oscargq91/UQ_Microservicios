package co.com.bancolombia.model.user.exception;


import co.com.bancolombia.model.user.exception.message.ErrorMessage;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorMessage errorMessage;

    public BusinessException(ErrorMessage businessErrorMessageIn) {
        super(businessErrorMessageIn.getReason());
        this.errorMessage = businessErrorMessageIn;
    }
    }


