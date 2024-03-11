package co.com.bancolombia.model.exception.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TechnicalErrorMessage {
	UNEXPECTED_EXCEPTION(500, "Error Inesperado"),
	RESOURCE_NOT_FOUND(404, "Recurso no encontrado");

	private final Integer code;
	private final String description;

}
