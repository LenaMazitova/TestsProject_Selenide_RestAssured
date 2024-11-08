package reqresApi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class RegisterUserRequest {

    private final String email;
    private final String password;
}
