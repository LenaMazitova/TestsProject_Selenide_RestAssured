package reqresApi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
public class RegisterUserResponse {
    private final String id;
    private final String token;

    public RegisterUserResponse(String token) {
        this.token = token;
        id = null;
    }
}
