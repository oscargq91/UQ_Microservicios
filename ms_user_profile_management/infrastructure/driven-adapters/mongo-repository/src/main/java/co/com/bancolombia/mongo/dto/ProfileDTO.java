package co.com.bancolombia.mongo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "profiles")
public class ProfileDTO {
    @Id
    private String username;
    private String personalPageUrl;
    private String nickname;
    private boolean contactInfoPublic;
    private String mailingAddress;
    private String biography;
    private String organization;
    private String country;
    private List<String> socialNetworks;
}
