package rest.mvc.example.model;

import lombok.Data;

@Data
public class SimpleAppUserDTO {
    private Long id;

    private String firstName;

    private String lastName;
}
