package Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static Constants.Endpoints.BASE_URI;

public class Request {

    public static Response getById(String endpoint, String id)
    {
        RestAssured.baseURI = BASE_URI;
        Response response = RestAssured
                .given().pathParam("id", id)
                .when().get(endpoint);

        response.then().log().body();

        return response;
    }

    public static Response post(String endpoint, String payload)
    {
        RestAssured.baseURI = BASE_URI;

        Response response = RestAssured
                .given().contentType(ContentType.JSON).body(payload)
                .when().post(endpoint);

        response.then().log().body();

        return response;
    }
}
