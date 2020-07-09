package rest.mvc.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AppUserDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String password;

    @JsonProperty("app_user_url")
    private String url;

//    todo: add the profile object
}
