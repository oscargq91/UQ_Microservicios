package co.com.bancolombia.model.exception.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum BusinessErrorMessage {
    BAD_REQUEST_USUARIO(400, "El usuario es requerido"),
    BAD_REQUEST_CLAVE(400, "La clave es requerida"),
    TOKEN_VENCIDO(401, "Token vencido"),
    TOKEN_INVALIDO(401, "Token invalido"),
    TOKEN_USERNAME_INVALID(401, "Credenciales incorrectas"),
    TOKEN_REQUERIDO(400, "Header Authorization requerido"),
    PARAM_NAME_IS_REQUIRED(400, "Solicitud no valida: El nombre es obligatorio");
    private final Integer code;
    private final String reason;
}
