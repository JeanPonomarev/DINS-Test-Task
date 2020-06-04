package com.typicode.jsonplaceholder.test_cases.posts_by_user_id;

import io.restassured.RestAssured;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.nullValue;

public class GetPostsByInvalidUserId {
    @Test
    @Parameters({"nonexistentUserId", "criticalResponseTime"})
    void testGetPostByNonexistentUserId(int nonexistentUserId, long criticalResponseTime) {
        RestAssured.given()
                .queryParam("userId", nonexistentUserId)
                .when()
                .get("https://jsonplaceholder.typicode.com/posts")
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
    @Parameters({"invalidUserId", "criticalResponseTime"})
    void testGetPostByInvalidUserId(String invalidUserId, long criticalResponseTime) {
        RestAssured.given()
                .queryParam("userId", invalidUserId)
                .when()
                .get("https://jsonplaceholder.typicode.com/posts")
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
    @Parameters({"largeUserId", "criticalResponseTime"})
    void testGetPostByLargeUserId(long largeUserId, long criticalResponseTime) {
        RestAssured.given()
                .queryParam("userId", largeUserId)
                .when()
                .get("https://jsonplaceholder.typicode.com/posts")
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
