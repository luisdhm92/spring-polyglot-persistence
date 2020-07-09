package rest.mvc.example.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String expertiseArea;

    private String technicalSkills;

    @OneToOne(mappedBy = "profile")
    private AppUser appUser;
}
