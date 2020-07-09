package rest.mvc.example.domain;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class Topic implements Serializable {

    @NonNull
    private String name;

}
