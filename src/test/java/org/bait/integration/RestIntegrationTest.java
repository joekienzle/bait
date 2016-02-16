package org.bait.integration;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static com.jayway.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
import static org.junit.Assert.assertNotNull;

public class RestIntegrationTest {

    public static final String BAI_RESOURCE = "bai";

    public static final String BAI_ID_JSON_FIELD = BAI_RESOURCE + "Id";

    public static final String ACCOUNT_NUMBER_JSON_FIELD = "accountNumber";

    public static final String BANK_NUMBER_JSON_FIELD = "bankNumber";

    public static final String BANK_NAME_JSON_FIELD = "bankName";

    public static final String AMOUNT_JSON_FIELD = "amount";

    public static final String SUBJECT_JSON_FIELD = "subject";

    public static final String TRANSFER_RESOURCE = "transfer";

    public static final String TRANSFER_ID_JSON_FIELD = TRANSFER_RESOURCE + "Id";

    public static JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;

    @Before
    public void setUp() {
        RestAssured.basePath = "/api";
        RestAssured.port = 7070;
    }

    @Test
    public void postBankAccountInformation() throws Exception {
        given().
            contentType(MediaType.APPLICATION_JSON).
        when().
             body(buildBaiJson()).
             post("/" + BAI_RESOURCE).
        then().
            statusCode(Response.Status.CREATED.getStatusCode()).
            body(BAI_ID_JSON_FIELD, not(isEmptyOrNullString()));
    }

    @Test
    public void getNoBai() throws Exception {
        given().
            accept(MediaType.APPLICATION_JSON).
        when().
            get("/" + BAI_RESOURCE + "/" + UUID.randomUUID().toString()).
        then().
            statusCode(Response.Status.NOT_FOUND.getStatusCode());

    }

    @Test
    public void postAndGetBai() throws Exception {
        String accountNumber = "1234";
        String bankNumber = "56789";
        String bankName = "My eco bank";
        String returnJson =
            given().
                contentType(MediaType.APPLICATION_JSON).
            when().
                 body(buildBaiJson(accountNumber, bankNumber, bankName)).
            then().
                statusCode(Response.Status.CREATED.getStatusCode()).
            post("/" + BAI_RESOURCE).
                body().asString();

        final String baiId = from(returnJson).get(BAI_ID_JSON_FIELD);
        assertNotNull(baiId);

        when().
            get("/" + BAI_RESOURCE + "/" + baiId).
        then().
            statusCode(Response.Status.OK.getStatusCode()).
            body(BAI_ID_JSON_FIELD, equalTo(baiId)).
            body(ACCOUNT_NUMBER_JSON_FIELD, equalTo(accountNumber)).
            body(BANK_NUMBER_JSON_FIELD, equalTo(bankNumber)).
            body(BANK_NAME_JSON_FIELD, equalTo(bankName));

    }

    @Test
    public void postAndDeleteBai() throws Exception {
        String returnJson =
                given().
                        contentType(MediaType.APPLICATION_JSON).
                when().
                        body(buildBaiJson()).
                then().
                        statusCode(Response.Status.CREATED.getStatusCode()).
                post("/" + BAI_RESOURCE)
                        .asString();

        final String baiId = from(returnJson).get(BAI_ID_JSON_FIELD);
        assertNotNull(baiId);

        when().
                get("/" + BAI_RESOURCE + "/" + baiId).
        then().
                statusCode(Response.Status.OK.getStatusCode()).
                body(BAI_ID_JSON_FIELD, equalTo(baiId));

        when().
                delete("/" + BAI_RESOURCE + "/" + baiId).
        then().
                statusCode(Response.Status.NO_CONTENT.getStatusCode());

        when().
                get("/" + BAI_RESOURCE + "/" + baiId).
        then().
                statusCode(Response.Status.NOT_FOUND.getStatusCode());

    }

    @Test
    public void postAndGetBankTransfer() throws Exception {
        String baiReturnJson =
                given().
                        contentType(MediaType.APPLICATION_JSON).
                when().
                        body(buildBaiJson()).
                then().
                        statusCode(Response.Status.CREATED.getStatusCode()).
                post("/" + BAI_RESOURCE).
                        body().asString();

        final String baiId = from(baiReturnJson).get(BAI_ID_JSON_FIELD);
        assertNotNull(baiId);

        String amount = "142.23";
        String subject = "Bill Number 01257091725";

        String transferReturnJson =
                given().
                        contentType(MediaType.APPLICATION_JSON).
                when().
                        body(buildTransferJson(subject, amount, baiId)).
                then().
                        statusCode(Response.Status.CREATED.getStatusCode()).
                post("/" + TRANSFER_RESOURCE).
                        body().asString();

        final String transferId = from(transferReturnJson).get(TRANSFER_ID_JSON_FIELD);
        assertNotNull(transferId);

        when().
                get("/" + TRANSFER_RESOURCE + "/" + transferId).
        then().
                statusCode(Response.Status.OK.getStatusCode()).
                body(SUBJECT_JSON_FIELD, equalTo(subject)).
                body(AMOUNT_JSON_FIELD, equalTo(amount)).
                body(TRANSFER_ID_JSON_FIELD, equalTo(transferId)).
                body(BAI_ID_JSON_FIELD, equalTo(baiId));

    }

    @Test
    public void postAndDeleteBankTransfer() throws Exception {
        String baiReturnJson =
                given().
                        contentType(MediaType.APPLICATION_JSON).
                when().
                        body(buildBaiJson()).
                then().
                        statusCode(Response.Status.CREATED.getStatusCode()).
                post("/" + BAI_RESOURCE).
                        body().asString();

        final String baiId = from(baiReturnJson).get(BAI_ID_JSON_FIELD);
        assertNotNull(baiId);

        String transferReturnJson =
                given().
                        contentType(MediaType.APPLICATION_JSON).
                when().
                        body(buildTransferJson("Bill Number 01257091725", "142.23", baiId)).
                then().
                        statusCode(Response.Status.CREATED.getStatusCode()).
                post("/" + TRANSFER_RESOURCE).
                        body().asString();

        final String transferId = from(transferReturnJson).get(TRANSFER_ID_JSON_FIELD);
        assertNotNull(transferId);

        when().
                get("/" + TRANSFER_RESOURCE + "/" + transferId).
        then().
                statusCode(Response.Status.OK.getStatusCode());

        when().
                delete("/" + TRANSFER_RESOURCE + "/" + transferId).
        then().
                statusCode(Response.Status.NO_CONTENT.getStatusCode());

        when().
                get("/" + TRANSFER_RESOURCE + "/" + transferId).
        then().
                statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void postBankTransferAndTryToDeleteBai() throws Exception {
        String baiReturnJson =
                given().
                        contentType(MediaType.APPLICATION_JSON).
                when().
                        body(buildBaiJson()).
                then().
                        statusCode(Response.Status.CREATED.getStatusCode()).
                post("/" + BAI_RESOURCE).
                        body().asString();

        final String baiId = from(baiReturnJson).get(BAI_ID_JSON_FIELD);
        assertNotNull(baiId);

        String transferReturnJson =
                given().
                        contentType(MediaType.APPLICATION_JSON).
                when().
                        body(buildTransferJson("Bill Number 01257091725", "142.23", baiId)).
                then().
                        statusCode(Response.Status.CREATED.getStatusCode()).
                post("/" + TRANSFER_RESOURCE).
                        body().asString();

        final String transferId = from(transferReturnJson).get(TRANSFER_ID_JSON_FIELD);
        assertNotNull(transferId);

        when().
                delete("/" + BAI_RESOURCE + "/" + baiId).
        then().
                statusCode(Response.Status.PRECONDITION_FAILED.getStatusCode()).
                body(containsString(transferId));
    }

    @Test
    public void postBankTransferAndDeleteBai() throws Exception {
        String baiReturnJson =
                given().
                        contentType(MediaType.APPLICATION_JSON).
                when().
                        body(buildBaiJson()).
                then().
                        statusCode(Response.Status.CREATED.getStatusCode()).
                post("/" + BAI_RESOURCE).
                        body().asString();

        final String baiId = from(baiReturnJson).get(BAI_ID_JSON_FIELD);
        assertNotNull(baiId);

        String transferReturnJson =
                given().
                        contentType(MediaType.APPLICATION_JSON).
                when().
                        body(buildTransferJson("Bill Number 01257091725", "142.23", baiId)).
                then().
                        statusCode(Response.Status.CREATED.getStatusCode()).
                post("/" + TRANSFER_RESOURCE).
                        body().asString();

        final String transferId = from(transferReturnJson).get(TRANSFER_ID_JSON_FIELD);
        assertNotNull(transferId);

        when().
                delete("/" + TRANSFER_RESOURCE + "/" + transferId).
        then().
                statusCode(Response.Status.NO_CONTENT.getStatusCode());

        when().
                delete("/" + BAI_RESOURCE + "/" + baiId).
        then().
                statusCode(Response.Status.NO_CONTENT.getStatusCode());

        when().
                get("/" + TRANSFER_RESOURCE + "/" + transferId).
        then().
                statusCode(Response.Status.NOT_FOUND.getStatusCode());

        when().
                get("/" + BAI_RESOURCE + "/" + baiId).
        then().
                statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    private String buildTransferJson(final String subject, final String amount, final String baiId) {
        return jsonNodeFactory.objectNode().
                put(SUBJECT_JSON_FIELD, subject).
                put(AMOUNT_JSON_FIELD, amount).
                put(BAI_ID_JSON_FIELD, baiId).
                toString();
    }

    private String buildBaiJson() {
        return buildBaiJson("1234", "56789", "My eco bank");
    }

    private String buildBaiJson(final String accountNumber, final String bankNumber, final String bankName) {
        return jsonNodeFactory.objectNode().
                put(ACCOUNT_NUMBER_JSON_FIELD, accountNumber).
                put(BANK_NUMBER_JSON_FIELD, bankNumber).
                put(BANK_NAME_JSON_FIELD, bankName).
                toString();
    }
}
