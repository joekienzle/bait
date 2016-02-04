package org.bait.integration;

import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;

public class RestIntegrationTest {

    @Before
    public void setUp() {
        RestAssured.basePath = "/api/bait";
        RestAssured.port = 7070;
    }

    @Test
    public void postBankAccountInformation() throws Exception {
        given().
            contentType(MediaType.APPLICATION_JSON).
        when().
             body("{\"accountNumber\":\"1234\",\"bankNumber\":\"56789\",\"bankName\":\"My eco bank\"}").
        then().
            statusCode(Response.Status.CREATED.getStatusCode()).
            body("id", not(isEmptyOrNullString())).
        post();
    }
}
