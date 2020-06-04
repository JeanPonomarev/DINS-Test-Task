package com.typicode.jsonplaceholder.test_cases.post_by_id;

import io.restassured.RestAssured;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class GetPostByInvalidIdTest {
    @Test
    @Parameters({"nonexistentPostId", "criticalResponseTime"})
    void testGetPostByNonexistentId(int nonexistentPostId, long criticalResponseTime) {
        RestAssured.given()
                .pathParam("id", nonexistentPostId)
                .when()
                .get("https://jsonplaceholder.typicode.com/posts/{id}")
                .then()
                .statusCode(404)
                .statusLine("HTTP/1.1 404 Not Found")
                .assertThat()
                .body("size()", equalTo(0))
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .time(lessThan(criticalResponseTime))
                .header("Server", equalTo("cloudflare"))
                .header("Content-Encoding", nullValue());
    }


    @Test
    @Parameters({"invalidPostId", "criticalResponseTime"})
    void testGetPostByInvalidId(String invalidPostId, long criticalResponseTime) {
        RestAssured.given()
                .pathParam("id", invalidPostId)
                .when()
                .get("https://jsonplaceholder.typicode.com/posts/{id}")
                .then()
                .statusCode(400)
                .statusLine("HTTP/1.1 400 Bad Request")
                .assertThat()
                .body("size()", equalTo(0))
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .time(lessThan(criticalResponseTime))
                .header("Server", equalTo("cloudflare"))
                .header("Content-Encoding", nullValue());
    }

    @Test
    @Parameters({"largePostId", "criticalResponseTime"})
    void testGetPostByLargeId(long largePostId, long criticalResponseTime) {
        RestAssured.given()
                .pathParam("id", largePostId)
                .when()
                .get("https://jsonplaceholder.typicode.com/posts/{id}")
                .then()
                .statusCode(404)
                .statusLine("HTTP/1.1 404 Not Found")
                .assertThat()
                .body("size()", equalTo(0))
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .time(lessThan(criticalResponseTime))
                .header("Server", equalTo("cloudflare"))
                .header("Content-Encoding", nullValue());
    }
}
