package persistence.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String title;
    private String description;
    private LocalDate date;
    private ZonedDateTime time;
    private int duration;
    private int capacity;
    private String location;
    private String instructor;
    private double price;
    private String image;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private LocalDate deletedAt;

    public Event(String title, String description, LocalDate date, ZonedDateTime time, int duration, int capacity, String location, String instructor, double price, String image) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.capacity = capacity;
        this.location = location;
        this.instructor = instructor;
        this.price = price;
        this.image = image;
    }

    enum Category {
        WORKSHOP, TALK, COURSE
    }

    enum Status {
        ACTIVE, INACTIVE, CANCELED
    }

    //Bi-directional
    @ManyToMany(mappedBy = "events")
    private Set<User> users = new HashSet<>();
}
