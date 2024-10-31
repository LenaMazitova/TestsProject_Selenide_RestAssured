package reqres_api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class UpdatedUserResponse {
    private final String name;
    private final String job;
    private final String updatedAt;
}
