package persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    String email;
    String password;
    int phone;

    public User(String name, String email, String password, int phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    enum Role {
        INSTRUCTOR, STUDENT
    }

    //Bi-directional
    @ManyToMany()
    private Set<Event> events = new HashSet<>();
}
