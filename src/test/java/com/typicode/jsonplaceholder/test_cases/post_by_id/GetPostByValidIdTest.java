package com.typicode.jsonplaceholder.test_cases.post_by_id;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class GetPostByValidIdTest {
    Response response;

    @BeforeClass
    @Parameters({"validPostId"})
    void getResponse(int validPostId) {
        response = RestAssured.given()
                .pathParam("id", validPostId)
                .when()
                .get("https://jsonplaceholder.typicode.com/posts/{id}");
    }

    @Test
    void testStatusCode() {
        response.then().statusCode(200);
    }

    @Test
    void testStatusLine() {
        response.then().statusLine("HTTP/1.1 200 OK");
    }

    @Test
    @Parameters({"validPostId"})
    void testPostFields(int validPostId) {
        response.then().assertThat()
                .body("id", equalTo(validPostId))
                .body("userId", notNullValue())
                .body("title", notNullValue())
                .body("body", notNullValue());
    }

    @Test
    void testContentType() {
        response.then().header("Content-Type", equalTo("application/json; charset=utf-8"));
    }

    @Test
    @Parameters({"criticalResponseTime"})
    void testResponseTime(long criticalResponseTime) {
        response.then().time(lessThan(criticalResponseTime));
    }

    @Test
    void testServerType() {
        response.then().assertThat().header("Server", equalTo("cloudflare"));
    }

    @Test
    void testContentEncoding() {
        response.then().assertThat().header("Content-Encoding", equalTo("gzip"));
    }

//    @Test
//    @Parameters({"validPostId", "criticalResponseTime"})
//    void testGetPostByValidId(int validPostId, long criticalResponseTime) {
//        RestAssured.given()
//                .pathParam("id", validPostId)
//                .when()
//                .get("https://jsonplaceholder.typicode.com/posts/{id}")
//                .then()
//                .statusCode(200)
//                .statusLine("HTTP/1.1 200 OK")
//                .assertThat()
//                .body("id", equalTo(validPostId))
//                .body("userId", notNullValue())
//                .body("title", notNullValue())
//                .body("body", notNullValue())
//                .header("Content-Type", equalTo("application/json; charset=utf-8"))
//                .time(lessThan(criticalResponseTime))
//                .header("Server", equalTo("cloudflare"))
//                .header("Content-Encoding", equalTo("gzip"));
//    }
}
