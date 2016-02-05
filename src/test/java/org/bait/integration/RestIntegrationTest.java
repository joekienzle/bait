package org.bait.integration;

import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
import static org.junit.Assert.assertNotNull;

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

    @Test
    public void postAndGetBankAccountInformation() throws Exception {
        String accountNumber = "1234";
        String bankNumber = "56789";
        String bankName = "My eco bank";
        String returnJson =
            given().
                contentType(MediaType.APPLICATION_JSON).
            when().
                 body("{\"accountNumber\":\"" + accountNumber + "\",\"bankNumber\":\"" + bankNumber + "\",\"bankName\":\"" + bankName + "\"}").
            then().
                statusCode(Response.Status.CREATED.getStatusCode()).
            post()
                .asString();

        String baiId = from(returnJson).get("id");
        assertNotNull(baiId);

        given().
            accept(MediaType.APPLICATION_JSON).
        when().
            get("/" + baiId).
        then().
            statusCode(Response.Status.OK.getStatusCode()).
            body("id", equalTo(baiId));

    }
}
