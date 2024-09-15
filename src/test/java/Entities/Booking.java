package Entities;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Booking extends BaseBooking {
    private String lastname;
    private Integer totalprice;
}
