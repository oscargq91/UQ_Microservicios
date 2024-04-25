package co.com.bancolombia.model.user.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    BAD_REQUEST_USERNAME(400, "The username field is required"),
    BAD_REQUEST_USERNAME_DUPLICATED(409, "Username already exists. Please choose a different username."),
    BAD_REQUEST_PASSWORD(400, "The password field is require"),
    BAD_REQUEST_ID(400, "Id is required"),
    BAD_REQUEST_EMAIL(400, "The email field is required"),
    TOKEN_VENCIDO(401, "Expired token"),
    TOKEN_UNSUPPORTED(401, "Token unsupported"),
    TOKEN_MALFORMED(401, "Token malformed"),
    TOKEN_ILLEGAL_ARGS(401, "Illegal args"),
    TOKEN_INVALIDO(401, "Invalid Token"),
    TOKEN_VALID_ERROR(401, "Error validating token"),
    NOT_FOUND_USER(404, "Not found user"),
    URI_NOT_FOUND(404, "No resource was found for the requested URI"),
    TOKEN_REQUERIDO(400, "Header Authorization is required"),
    INVALID_PASSWORD(401,  "Unauthorized: Incorrect password"),
    BAD_REQUEST_BODY_NULL(404, "Body is required."),
    EVENT_ERROR(500, "Generate Event Error"),
    UNEXPECTED_EXCEPTION(500, "Unexpected error");

    private final Integer code;
    private final String reason;




}

