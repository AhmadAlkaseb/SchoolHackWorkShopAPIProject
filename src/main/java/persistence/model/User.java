package persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    @JsonIgnore
    //Bi-directional
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name="events_users",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private Set<Event> events = new HashSet<>();

    public void addEvent(Event event){
        if(event != null){
            events.add(event);
            event.users.add(this);
        }
    }

    @Override
    public String toString() {
        return "User - " +
                "id = " + id +
                ", name = " + name +
                ", email = " + email +
                ", password = " + password +
                ", phone = " + phone;
    }
}
