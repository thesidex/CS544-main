package edu.miu.cs.cs544;

import edu.miu.cs.cs544.domain.*;
import edu.miu.cs.cs544.service.contract.AttendancePayload;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import static io.restassured.RestAssured.given;

@SpringBootTest
public class AttendanceRESTTest {
    @BeforeClass
    public static void setup() {
        RestAssured.port = Integer.valueOf(8080);
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/badge-system";
    }
    @Test
    public void testGetOneScannerRecord() {
        Member member = new Member(1L,"Bruce","Banner","the.hulk@miu.edu","123456789",null,null,null);
        Scanner scanner = new Scanner(702L,"01",null, AccountType.ATTENDANCE_POINTS,null,null);

        AttendancePayload attendance = new AttendancePayload(member,scanner,LocalDateTime.now());
        Response response = given()
                .auth()
                .basic("user", "123")
                .contentType("application/json")
                .body(attendance)
                .when().post("attendances/register")
                .then()
                .statusCode(200)
                .extract()
                .response();
    }
}
