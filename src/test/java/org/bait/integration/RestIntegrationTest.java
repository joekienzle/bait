package org.bait.integration;

import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.UUID;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
import static org.junit.Assert.assertNotNull;

public class RestIntegrationTest {

    public static final String BAI_ID = "baiId";

    @Before
    public void setUp() {
        RestAssured.basePath = "/api/bai";
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
            body(BAI_ID, not(isEmptyOrNullString())).
        post();
    }

    @Test
    public void getNoBankAccountInformation() throws Exception {
        given().
            accept(MediaType.APPLICATION_JSON).
        when().
            get("/" + UUID.randomUUID().toString()).
        then().
            statusCode(Response.Status.NOT_FOUND.getStatusCode());

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

        String baiId = from(returnJson).get(BAI_ID);
        assertNotNull(baiId);

        given().
            accept(MediaType.APPLICATION_JSON).
        when().
            get("/" + baiId).
        then().
            statusCode(Response.Status.OK.getStatusCode()).
            body(BAI_ID, equalTo(baiId));

    }

    @Test
    public void postAndDeleteBankAccountInformation() throws Exception {
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

        String baiId = from(returnJson).get(BAI_ID);
        assertNotNull(baiId);

        given().
                accept(MediaType.APPLICATION_JSON).
                when().
                get("/" + baiId).
                then().
                statusCode(Response.Status.OK.getStatusCode()).
                body(BAI_ID, equalTo(baiId));

        given().
                accept(MediaType.APPLICATION_JSON).
                when().
                delete("/" + baiId).
                then().
                statusCode(Response.Status.NO_CONTENT.getStatusCode());

        given().
                accept(MediaType.APPLICATION_JSON).
                when().
                get("/" + baiId).
                then().
                statusCode(Response.Status.NOT_FOUND.getStatusCode());

    }
}
