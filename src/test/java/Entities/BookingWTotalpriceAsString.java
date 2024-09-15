package Entities;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookingWTotalpriceAsString {
    private String firstname;
    private String lastname;
    private String totalprice;
    private Boolean depositpaid;
    private BookingDates bookingdates;
    private String additionalneeds;
}
