package com.typicode.jsonplaceholder.test_cases.list_of_resourses;

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

public class GetPostsListTest {
    private Response response;
    List<Post> postsList;

    @BeforeClass
    void getResponseAndPostsList() {
        response = RestAssured
                .given()
                .when()
                .get("https://jsonplaceholder.typicode.com/posts");

        postsList = Arrays.asList(response.getBody().as(Post[].class));
    }

    @Test
    @Parameters({"postsNumber"})
    void testPostsListLength(int postsNumber) {
        assertThat(postsList, hasSize(postsNumber));
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
    void testPostsListObjectHasOnlyNotNullFields() {
        postsList.forEach(post -> {
            assertThat(post.getUserId(), notNullValue());
            assertThat(post.getId(), notNullValue());
            assertThat(post.getTitle(), notNullValue());
            assertThat(post.getBody(), notNullValue());
        });
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
