package reqresApi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreatedUserResponse {
    private final String name;
    private final String job;
    private final String id;
    private final String createdAt;
}
