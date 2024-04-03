package dtos;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegistrationDTO {
    private int regId;
    private int userId;
    private String userName;
    private int eventId;
    private String eventName;
}
