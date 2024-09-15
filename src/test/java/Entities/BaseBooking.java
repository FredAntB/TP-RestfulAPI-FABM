package Entities;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BaseBooking {
    private String firstname;
    private Boolean depositpaid;
    private BookingDates bookingdates;
    private String additionalneeds;
}
