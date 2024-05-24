package co.com.bancolombia.model.profile.exception;


import co.com.bancolombia.model.profile.exception.message.ErrorMessage;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorMessage errorMessage;

    public BusinessException(ErrorMessage businessErrorMessageIn) {
        super(businessErrorMessageIn.getReason());
        this.errorMessage = businessErrorMessageIn;
    }
    }


