package persistence.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
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

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    @Column(name = "duration_in_hours", nullable = false)
    private double duration;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private String location;
    //todo: burde det ikke være en User?

    @Column(nullable = false)
    private String instructor;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String image;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDate updatedAt;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    public enum Category {
        workshop, talk, course
    }

    public enum Status {
        active, inactive, canceled
    }

    public Event(String title, Category category, String description, LocalDate date, LocalTime time, double duration, int capacity, String location, String instructor, double price, String image, Status status) {
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
        this.status = status;
    }

    @PrePersist
    private void onCreate() {
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();
    }

    @PreUpdate
    private void onUpdate() {
        updatedAt = LocalDate.now();
    }

    @PreRemove
    private void onDelete() {
        deletedAt = LocalDate.now();
    }

    //Bi-directional
    @ManyToMany(mappedBy = "events", fetch = FetchType.EAGER)
    public Set<User> users = new HashSet<>();

    public void updateStatus(Status status) {
        this.status = status;
    }
}
