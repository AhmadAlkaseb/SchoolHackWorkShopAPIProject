package dtos;

import lombok.*;
import persistence.model.User;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO
{
    private int id;
    private String name;
    private String email;
    private String password;
    private int phone;
    private String role;

    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserDTO(User user)
    {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phone = user.getPhone();
        this.role = user.getRole().toString().toLowerCase();
    }
}
