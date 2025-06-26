package database.mmasenheimerdbex.database.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@AllArgsConstructor
// Generates a constructor with all fields as parameters
@NoArgsConstructor

@Builder
// The DTO class is used for carrying Author data between layers of the app
public class AuthorDto {
    private Long id;

    private String name;

    private Integer age;

}
