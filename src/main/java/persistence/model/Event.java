package persistence.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String title;
    //todo: ville give mening at bruge predefinerede værdier
    private String category;
    private String description;
    private LocalDate date;
    private ZonedDateTime time;
    private int duration;
    private int capacity;
    private String location;
    //todo: burde det ikke være en User?
    private String instructor;
    private double price;
    private String image;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private LocalDate deletedAt;

    public Event(String title, String category, String description, LocalDate date, ZonedDateTime time, int duration, int capacity, String location, String instructor, double price, String image, LocalDate createdAt, LocalDate updatedAt, LocalDate deletedAt) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.capacity = capacity;
        this.location = location;
        this.instructor = instructor;
        this.price = price;
        this.image = image;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    enum Category {
        WORKSHOP, TALK, COURSE
    }

    enum Status {
        ACTIVE, INACTIVE, CANCELED
    }

    //Bi-directional
    @ManyToMany(mappedBy = "events", fetch = FetchType.EAGER)
    public Set<User> users = new HashSet<>();
}
