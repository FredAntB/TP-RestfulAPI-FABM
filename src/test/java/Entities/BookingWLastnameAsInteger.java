package Entities;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookingWLastnameAsInteger {
    private String firstname;
    private Integer lastname;
    private Integer totalprice;
    private Boolean depositpaid;
    private BookingDates bookingdates;
    private String additionalneeds;
}
