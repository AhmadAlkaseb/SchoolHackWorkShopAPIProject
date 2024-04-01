package dtos;

import lombok.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
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
}
