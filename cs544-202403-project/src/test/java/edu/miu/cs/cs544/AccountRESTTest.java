package edu.miu.cs.cs544;

import edu.miu.cs.cs544.domain.*;
import edu.miu.cs.cs544.service.contract.AccountPayload;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static io.restassured.RestAssured.given;

@SpringBootTest
public class AccountRESTTest {
    @BeforeClass
    public static void setup() {
        RestAssured.port = Integer.valueOf(8080);
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/badge-system";
    }
    @Test
    public void testCRUD() {
        // add the account to be fetched
        AccountPayload accountPayload = new AccountPayload("Tom", "Student", 15000, 15000, AccountType.DINING_POINTS);
        Response response = given()
                .auth()
                .basic("user", "123")
                .contentType("application/json")
                .body(accountPayload)
                .when().post("/accounts").then()
                .statusCode(200)
                .extract()
                .response();
        AccountPayload responseBody = response.getBody().as(AccountPayload.class);

        // test getting the accounts
        given()
                .auth()
                .basic("user", "123")
                .when()
                .get("accounts")
                .then()
                .statusCode(200);

        //test update the account
        responseBody.setName("Jerry");
        given()
                .auth()
                .basic("user", "123")
                .contentType("application/json")
                .body(responseBody)
                .when().put("/accounts/" + responseBody.getId())
                .then()
                .statusCode(200);

        //cleanup
		given()
                .auth()
                .basic("user", "123")
				.when()
				.delete("/accounts/"+responseBody.getId())
				.then()
				.statusCode(200);
    }
    
    @Test
    public void testAttendanceByAccountIdAndStartTimeAndDateTime() {
        // add the account to be fetched
        given()
                .auth()
                .basic("user", "123")
                .contentType("application/json")
                .when().get("/accounts/1/attendance/2024-01-01/2024-02-15").then()
                .statusCode(200);
    }
}
