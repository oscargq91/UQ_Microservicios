package co.com.bancolombia.model.exception;


import co.com.bancolombia.model.exception.message.TechnicalErrorMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TechnicalException extends RuntimeException {

    private final TechnicalErrorMessage technicalErrorMessage;

    public TechnicalException(Throwable cause, String errorMessage, TechnicalErrorMessage technicalErrorMessage) {
        super(errorMessage, cause);
        this.technicalErrorMessage = technicalErrorMessage;
    }

    public TechnicalException(Throwable cause, TechnicalErrorMessage technicalErrorMessage) {
        super(cause.getMessage(),cause);
        this.technicalErrorMessage = technicalErrorMessage;
    }

}