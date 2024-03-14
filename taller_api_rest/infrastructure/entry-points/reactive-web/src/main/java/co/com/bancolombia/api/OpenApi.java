package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.request.LoginRequestDTO;
import co.com.bancolombia.api.dto.request.UpdatePasswordRequestDTO;
import co.com.bancolombia.api.dto.request.UserSaveRequestDTO;
import co.com.bancolombia.api.dto.request.UserUpdateRequestDTO;
import co.com.bancolombia.api.dto.response.LoginResponseDTO;
import co.com.bancolombia.api.dto.response.UserListResponseDTO;
import co.com.bancolombia.api.dto.response.UserResponseDTO;

import co.com.bancolombia.api.exception.dto.ErrorDTO;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springdoc.core.fn.builders.operation.Builder;


import java.util.function.Consumer;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.content.Builder.contentBuilder;
import static org.springdoc.core.fn.builders.parameter.Builder.parameterBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.core.fn.builders.schema.Builder.schemaBuilder;

public class OpenApi {

    private static final String DESCRIPTION_BAD_REQUEST = "The server cannot or will not process the request due " +
            "to an apparent client error (e.g., malformed request syntax, size too large, invalid request message " +
            "framing, or deceptive request routing).";
    private static final String DESCRIPTION_CREATED = "User successfully created.";
    private static final String DESCRIPTION_OK = "Operation completed successfully.";

    public static Consumer<Builder> saveUser() {
        return ops -> ops
                .operationId("saveUser")
                .description("Endpoint to create a new user.")
                .tag("User")
                .summary("Create a new user.")
                .requestBody(buildUserSaveRequestDTO())
                .response(buildSuccessfullUserResponseDTOResponse(HttpStatus.CREATED.value(), DESCRIPTION_CREATED))
                .response(buildErrorResponse());
    }

    public static Consumer<Builder> getUserById() {
        return ops -> ops
                .operationId("getUserById")
                .description("Endpoint to retrieve a user by ID.")
                .parameter(parameterBuilder().in(ParameterIn.PATH)
                        .name("id")
                        .description("ID of the user to retrieve")
                        .implementation(String.class)
                        .required(Boolean.TRUE))
                .tag("User")
                .summary("Retrieve a user by ID.")
                .response(buildSuccessfullUserResponseDTOResponse(HttpStatus.OK.value(), DESCRIPTION_OK))
                .response(buildErrorResponse());
    }

    public static Consumer<Builder> deleteUserById() {
        return ops -> ops
                .operationId("deleteUserById")
                .description("Endpoint to delete a user by ID.")
                .parameter(parameterBuilder().in(ParameterIn.PATH)
                        .name("id")
                        .description("ID of the user to delete")
                        .implementation(String.class)
                        .required(Boolean.TRUE))
                .tag("User")
                .summary("Delete a user by ID.")
                .response(buildSuccessfullUserResponseDTOResponse(HttpStatus.NO_CONTENT.value(), DESCRIPTION_OK))
                .response(buildErrorResponse());
    }
    public static Consumer<Builder> updateUserById() {
        return ops -> ops
                .operationId("updateUserById")
                .description("Endpoint to update a user by ID.")
                .tag("User")
                .summary("Update a user by ID.")
                .requestBody(buildUserUpdateRequestDTO())
                .response(buildSuccessfullUserResponseDTOResponse(HttpStatus.OK.value(), DESCRIPTION_OK))
                .response(buildErrorResponse());
    }

    public static Consumer<Builder> findAllUsers() {
        return ops -> ops
                .operationId("findAllUsers")
                .description("Endpoint to retrieve all users.")
                .tag("User")
                .summary("Retrieve all users.")
                .parameter(parameterBuilder().in(ParameterIn.HEADER)
                        .name("page-number")
                        .description("Page Number: Indicates the current page number in pagination.")
                        .implementation(Integer.class)
                        .required(Boolean.FALSE))
                .parameter(parameterBuilder().in(ParameterIn.HEADER)
                        .name("page-size")
                        .description("Page Size: Specifies the number of items displayed per page in pagination.")
                        .implementation(Integer.class)
                        .required(Boolean.FALSE))
                .response(buildSuccessfullUserListResponseDTOResponse(HttpStatus.OK.value(), DESCRIPTION_OK))
                .response(buildErrorResponse());
    }
    public static Consumer<Builder> loginUser() {
        return ops -> ops
                .operationId("loginUser")
                .description("Endpoint to authenticate a user.")
                .tag("User")
                .summary("Authenticate a user.")
                .requestBody(buildLoginRequestDTO())
                .response(buildTokenesponse(HttpStatus.OK.value(), DESCRIPTION_OK))
                .response(buildErrorResponse());
    }

    public static Consumer<Builder> updatePassword() {
        return ops -> ops
                .operationId("updatePassword")
                .description("Endpoint to update a user's password.")
                .tag("User")
                .summary("Update a user's password.")
                .parameter(parameterBuilder().in(ParameterIn.HEADER)
                        .name("Authorization")
                        .description("Authentication token for accessing protected resources.")
                        .implementation(String.class)
                        .required(Boolean.FALSE))
                .requestBody(buildUpdatePasswordtDTO())
                .response(buildSuccessfullUserResponseDTOResponse(HttpStatus.OK.value(), DESCRIPTION_OK))
                .response(buildErrorResponse());
    }


    private static org.springdoc.core.fn.builders.apiresponse.Builder buildSuccessfullUserListResponseDTOResponse(int httpStatus, String description) {
        return responseBuilder()
                .responseCode(String.valueOf(httpStatus))
                .description(description)
                .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                        .schema(schemaBuilder()
                                .implementation(UserListResponseDTO.class)));
    }
    private static org.springdoc.core.fn.builders.apiresponse.Builder buildSuccessfullUserResponseDTOResponse(int httpStatus, String description) {
        return responseBuilder()
                .responseCode(String.valueOf(httpStatus))
                .description(description)
                .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                        .schema(schemaBuilder()
                                .implementation(UserResponseDTO.class)));
    }
    private static org.springdoc.core.fn.builders.apiresponse.Builder buildTokenesponse(int httpStatus, String description) {
        return responseBuilder()
                .responseCode(String.valueOf(httpStatus))
                .description(description)
                .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                        .schema(schemaBuilder()
                                .implementation(LoginResponseDTO.class)));
    }

    private static org.springdoc.core.fn.builders.apiresponse.Builder buildErrorResponse() {
        return responseBuilder()
                .responseCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .description(DESCRIPTION_BAD_REQUEST)
                .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                        .schema(schemaBuilder()
                                .implementation(ErrorDTO.class)));
    }
    private static org.springdoc.core.fn.builders.requestbody.Builder buildUserSaveRequestDTO() {
        return requestBodyBuilder()
                .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                        .schema(schemaBuilder()
                                .implementation(UserSaveRequestDTO.class)))
                .required(true);
    }
    private static org.springdoc.core.fn.builders.requestbody.Builder buildLoginRequestDTO() {
        return requestBodyBuilder()
                .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                        .schema(schemaBuilder()
                                .implementation(LoginRequestDTO.class)))
                .required(true);
    }

    private static org.springdoc.core.fn.builders.requestbody.Builder buildUserUpdateRequestDTO() {
        return requestBodyBuilder()
                .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                        .schema(schemaBuilder()
                                .implementation(UserUpdateRequestDTO.class)))
                .required(true);
    }
    private static org.springdoc.core.fn.builders.requestbody.Builder buildUpdatePasswordtDTO() {
        return requestBodyBuilder()
                .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                        .schema(schemaBuilder()
                                .implementation(UpdatePasswordRequestDTO.class)))
                .required(true);
    }

}
