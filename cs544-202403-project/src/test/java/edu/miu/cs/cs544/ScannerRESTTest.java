package edu.miu.cs.cs544;

import edu.miu.common.domain.AuditData;
import edu.miu.cs.cs544.domain.*;
import edu.miu.cs.cs544.service.contract.ScannerPayload;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;

@SpringBootTest
public class ScannerRESTTest {
    @BeforeClass
    public static void setup() {
        RestAssured.port = Integer.valueOf(8080);
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/badge-system";
    }
    @Test
    public void testCRUD() {
        //test adding the scanner
        ScannerPayload scannerPayload = new ScannerPayload("01",new Location("Argiro","Dalby Hall", LocationType.CLASSROOM),AccountType.ATTENDANCE_POINTS,
                new Event("CS544", "EA",AccountType.ATTENDANCE_POINTS, LocalDateTime.now(),LocalDateTime.now()));
        Response response = given()
                .auth()
                .basic("user", "123")
                .contentType("application/json")
                .body(scannerPayload)
                .when().post("/scanners")
                .then()
                .statusCode(200)
                .extract()
                .response();
        ScannerPayload responseBody = response.getBody().as(ScannerPayload.class);
        // test getting the events
        given()
                .auth()
                .basic("user", "123")
                .when()
                .get("scanners")
                .then()
                .statusCode(200);

        //test update the scanner
        responseBody.setCode("03");
        given()
                .auth()
                .basic("user", "123")
                .contentType("application/json")
                .body(responseBody)
                .when().put("/scanners/"+responseBody.getId())
                .then()
                .statusCode(200);
        //cleanup
        given()
                .auth()
                .basic("user", "123")
                .when()
                .delete("/scanners/"+responseBody.getId())
                .then()
                .statusCode(200);
    }
    @Test
    public void testGetOneScannerRecord() {
        // test getting the records
        given()
                .auth()
                .basic("user", "123")
                .when()
                .get("scanners/01/records")
                .then()
                .contentType(ContentType.JSON)
                .and()
                .statusCode(200);
    }
}
