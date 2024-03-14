package co.com.bancolombia.api.helper;

import co.com.bancolombia.api.dto.request.UserSaveRequestDTO;
import co.com.bancolombia.api.dto.request.UserUpdateRequestDTO;
import co.com.bancolombia.api.dto.response.UserDTO;
import co.com.bancolombia.api.dto.response.UserListResponseDTO;
import co.com.bancolombia.api.dto.response.UserResponseDTO;
import co.com.bancolombia.model.user.User;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class UserHelper {

    public static User getUserFromUserSaveRequestDto(UserSaveRequestDTO userSaveRequestDto) {
        return User.builder()
                .email(userSaveRequestDto.getUser().getEmail())
                .username(userSaveRequestDto.getUser().getUsername())
                .password(userSaveRequestDto.getUser().getPassword())
                .build();
    }


    public static User getUserFromUserUpdateRequestDto(UserUpdateRequestDTO userUpdateRequestDTO) {
        return User.builder()
                .id(userUpdateRequestDTO.getUser().getId())
                .email(userUpdateRequestDTO.getUser().getEmail())
                .username(userUpdateRequestDTO.getUser().getUsername())
                .password(userUpdateRequestDTO.getUser().getPassword())
                .build();
    }
    public static UserResponseDTO getUserResponseDtoFromUser(User user) {
        return UserResponseDTO.builder()
                .user(getUserDTOFromUser(user))
                .build();

    }

    public static UserDTO getUserDTOFromUser(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();

    }


    public static UserListResponseDTO getUserListResponseDtoFromUser(List<User> users) {
        return UserListResponseDTO.builder()
                .users(users.stream()
                        .map(UserHelper::getUserDTOFromUser).toList())
                .build();


    }
}
