package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BookingDatesModel {

    private String checkin;
    private String checkout;
}
