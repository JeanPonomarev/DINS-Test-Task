package com.typicode.jsonplaceholder.test_cases.posts_by_user_id;

import com.typicode.jsonplaceholder.entities.Post;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetPostsByValidUserIdTest {
    Response response;
    List<Post> postsList;

    @BeforeClass
    @Parameters({"validUserId"})
    void getResponseAndPostsList(int validUserId) {
        response = RestAssured.given()
                .queryParam("userId", validUserId)
                .when()
                .get("https://jsonplaceholder.typicode.com/posts");

        postsList = Arrays.asList(response.getBody().as(Post[].class));
    }

    @Test
    @Parameters({"validUserId"})
    void testPostsListHasCorrectFields(int validUserId) {
        postsList.forEach(post -> {
            assertThat(post.getUserId(), equalTo(validUserId));
            assertThat(post.getId(), notNullValue());
            assertThat(post.getTitle(), notNullValue());
            assertThat(post.getBody(), notNullValue());
        });
    }

    @Test
    @Parameters({"userPostsNumber"})
    void testPostListsLength(int userPostsNumber) {
        assertThat(postsList, hasSize(userPostsNumber));
    }

    @Test
    void testPostsListIsSortedById() {
        int previousId = postsList.get(0).getId();
        int currentId;

        for (int i = 1; i < postsList.size(); i++) {
            currentId = postsList.get(i).getId();
            assertThat(currentId, greaterThan(previousId));
            previousId = currentId;
        }
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
}