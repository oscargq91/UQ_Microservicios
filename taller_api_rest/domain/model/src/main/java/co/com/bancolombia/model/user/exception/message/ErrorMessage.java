package co.com.bancolombia.model.user.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    BAD_REQUEST_USERNAME(400, "The username field is required"),
    BAD_REQUEST_PASSWORD(400, "The password field is require"),
    BAD_REQUEST_ID(400, "The path variable id is required"),
    BAD_REQUEST_EMAIL(400, "The email field is required"),
    TOKEN_VENCIDO(401, "Expired token"),
    TOKEN_INVALIDO(401, "Invalid Token"),
    NOT_FOUND_USER(404, "Not found user"),
    URI_NOT_FOUND(404, "No resource was found for the requested URI"),
    TOKEN_REQUERIDO(400, "Header Authorization is required"),
    UNEXPECTED_EXCEPTION(500, "Unexpected error");

    private final Integer code;
    private final String reason;




}

