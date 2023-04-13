package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ClientInformationModel {
    private String firstname;
    private String lastname;
    private String additionalneeds;
    private Integer totalprice;
    private Boolean  depositpaid;
    private BookingDatesModel bookingdates;
}
