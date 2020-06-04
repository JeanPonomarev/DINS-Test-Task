package com.typicode.jsonplaceholder.test_cases.post_by_id;

import io.restassured.RestAssured;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

// This test class is optional: verifying that all of the posts can be retrieved properly by id (100 test-cases)

public class GetPostsByAllValidIdTest {
    private long criticalResponseTime;
    private int postsNumber;

    @BeforeClass
    void initializeParameters(ITestContext context) {
        criticalResponseTime = Long.parseLong(context.getCurrentXmlTest().getParameter("criticalResponseTime"));
        postsNumber = Integer.parseInt(context.getCurrentXmlTest().getParameter("postsNumber"));
    }

    @Test(dataProvider = "idDataProvider")
    void testGetPostByValidId(Integer id) {
        RestAssured.given()
                .pathParam("id", id)
                .when()
                .get("https://jsonplaceholder.typicode.com/posts/{id}")
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .assertThat()
                .body("id", equalTo(id))
                .body("userId", notNullValue())
                .body("title", notNullValue())
                .body("body", notNullValue())
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .time(lessThan(criticalResponseTime))
                .header("Server", equalTo("cloudflare"))
                .header("Content-Encoding", equalTo("gzip"));
    }

    @DataProvider(name = "idDataProvider")
    Object[] getIdArray() {
        Object[] idArray = new Object[postsNumber];

        for (int i = 0; i < postsNumber; i++) {
            idArray[i] = i + 1;
        }

        return idArray;
    }
}
