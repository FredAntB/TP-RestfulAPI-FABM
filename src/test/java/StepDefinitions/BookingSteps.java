package StepDefinitions;

import Entities.*;
import Utils.Request;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

import java.util.List;

import static Constants.Endpoints.GET_BY_ID_ENDPOINT;
import static Constants.Endpoints.POST_ENDPOINT;
import static org.hamcrest.CoreMatchers.not;

public class BookingSteps {
    Response response;

    private BaseBooking getBookingWithData(DataTable postRequestData) {
        List<String> data = postRequestData.transpose().asList();
        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin(data.get(4));
        bookingDates.setCheckout(data.get(5));

        if(data.get(1).matches("[0-9]+"))
        {
            BookingWLastnameAsInteger booking = new BookingWLastnameAsInteger();
            booking.setLastname(Integer.parseInt(data.get(1)));
            booking.setTotalprice(Integer.parseInt(data.get(2)));

            booking.setFirstname(data.get(0));
            booking.setDepositpaid(Boolean.parseBoolean(data.get(3)));
            booking.setBookingdates(bookingDates);
            booking.setAdditionalneeds(data.get(6));

            return booking;
        }

        if(data.get(2).matches("[a-zA-Z]+"))
        {
            BookingWTotalpriceAsString booking = new BookingWTotalpriceAsString();
            booking.setLastname(data.get(1));
            booking.setTotalprice(data.get(2));

            booking.setFirstname(data.get(0));
            booking.setDepositpaid(Boolean.parseBoolean(data.get(3)));
            booking.setBookingdates(bookingDates);
            booking.setAdditionalneeds(data.get(6));

            return booking;
        }

        Booking booking = new Booking();
        booking.setLastname(data.get(1));
        booking.setTotalprice(Integer.parseInt(data.get(2)));

        booking.setFirstname(data.get(0));
        booking.setDepositpaid(Boolean.parseBoolean(data.get(3)));
        booking.setBookingdates(bookingDates);
        booking.setAdditionalneeds(data.get(6));

        return booking;
    }

    private String getPayload(BaseBooking booking) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(booking);
    }

    @Given("I perform a GET call to the booking endpoint with id {string}")
    public void getBookingById(String id) {
        response = Request.getById(GET_BY_ID_ENDPOINT, id);
    }

    @And("I verify that the status code is {int}")
    public void verifyStatusCode(int statusCode) {
        response.then().assertThat().statusCode(statusCode);
    }

    @And("I verify that the body does not have size {int}")
    public void verifyThatBodyDoesNotHaveSize(int size) {
        response.then().assertThat().body("size()", not(size));
    }

    @Given("I perform a POST call to the booking endpoint with the following data")
    public void postBooking(DataTable postRequestData) throws JsonProcessingException {
        BaseBooking booking = getBookingWithData(postRequestData);

        response = Request.post(POST_ENDPOINT, getPayload(booking));
    }

    @And("I verify the following data in the body response")
    public void verifyDataInTheBodyResponse(DataTable postRequestData) {
        List<String> data = postRequestData.transpose().asList();

        response.then().assertThat().body("booking.firstname", Matchers.equalTo(data.get(0)));
        response.then().assertThat().body("booking.lastname", Matchers.equalTo(data.get(1)));
        response.then().assertThat().body("booking.totalprice", Matchers.equalTo(Integer.parseInt(data.get(2))));
        response.then().assertThat().body("booking.depositpaid", Matchers.equalTo(Boolean.parseBoolean(data.get(3))));
        response.then().assertThat().body("booking.additionalneeds", Matchers.equalTo(data.get(6)));
    }
}
