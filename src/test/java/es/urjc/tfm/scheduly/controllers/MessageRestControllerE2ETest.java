package es.urjc.tfm.scheduly.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

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
		String requestJson = "{\"messageBody\": \"Random message body\","
        		+ "\"year\": 2027,"
        		+ "\"month\": 9,"
        		+ "\"day\": 30,"
        		+ "\"hour\": 20,"
        		+ "\"minute\": 9}";

        Response messageCreated = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestJson)
                .when()
                .post("/api/message/schedule")
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
	
	@Test
	public void createMessagesAndFindAllTest() {
		String requestJson1 = "{\"messageBody\": \"Random message body 1\","
        		+ "\"year\": 2027,"
        		+ "\"month\": 9,"
        		+ "\"day\": 30,"
        		+ "\"hour\": 20,"
        		+ "\"minute\": 9}";

        Response messageCreated1 = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestJson1)
                .when()
                .post("/api/message/schedule")
                .then()
                .contentType(ContentType.JSON)
                .body("messageBody", equalTo("Random message body 1"))
                .extract().response();
        int messageId1 = messageCreated1.path("id");
        String requestJson2 = "{\"messageBody\": \"Random message body 2\","
        		+ "\"year\": 2025,"
        		+ "\"month\": 9,"
        		+ "\"day\": 30,"
        		+ "\"hour\": 20,"
        		+ "\"minute\": 9}";
        Response messageCreated2 = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestJson2)
                .when()
                .post("/api/message/")
                .then()
                .contentType(ContentType.JSON)
                .body("messageBody", equalTo("Random message body 2"))
                .extract().response();
        int messageId2 = messageCreated2.path("id");
        
        RestAssured.given()
        			.when()
        			.get("/api/message/")
        			.then()
        			.contentType(ContentType.JSON)
        			.body(notNullValue());
	    RestAssured.given()
				    .when()
			        .delete("/api/message/{id}", messageId1)
			        .then();
	    RestAssured.given()
					.when()
					.get("/api/message/{id}", messageId1)
					.then()
					.body(equalTo(""));
	    RestAssured.given()
				    .when()
			        .delete("/api/message/{id}", messageId2)
			        .then();
		RestAssured.given()
					.when()
					.get("/api/message/{id}", messageId2)
					.then()
					.body(equalTo(""));
    }

}