package dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDTO {
    private Integer id;
    private String title;
    private String category;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private double duration;
    private int capacity;
    private String location;
    private String instructor;
    private double price;
    private String image;
    private String status;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private LocalDate deletedAt;
}
