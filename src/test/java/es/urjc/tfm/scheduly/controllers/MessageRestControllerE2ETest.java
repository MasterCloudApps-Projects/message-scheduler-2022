package es.urjc.tfm.scheduly.controllers;

import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.matcher.RestAssuredMatchers.*;


public class MessageRestControllerE2ETest {

	
	@BeforeEach
    public void setUp() {
    	RestAssured.baseURI = "https://scheduly-molynx.cloud.okteto.net";
    }
	
	@Test
	public void createMessageAndFindByIdTest() {
        String requestJson = "{\"messageBody\": \"Random message body\"}";

        Response messageCreated = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestJson)
                .when()
                .post("/api/message/")
                .then()
                .contentType(ContentType.JSON)
                .body("messageBody", equalTo("Random message body"))
                .extract().response();
        int messageId = messageCreated.path("id");
	    RestAssured.given()
        			.when()
        			.get("/api/message/{id}", messageId)
        			.then()
        			.contentType(ContentType.JSON)
        			.body("messageBody", equalTo("Random message body"));
	    RestAssured.given()
				    .when()
			        .delete("/api/message/{id}", messageId)
			        .then();
	    RestAssured.given()
					.when()
					.get("/api/message/{id}", messageId)
					.then()
					.body(equalTo(""));
    }

}