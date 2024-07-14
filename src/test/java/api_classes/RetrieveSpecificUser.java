package api_classes;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RetrieveSpecificUser {

	@Before
    public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void testGetAllPosts() {
        Response response = given()
                .when()
                .get("/posts")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .response();

        Assert.assertNotNull(response.body().asString());
    }

    @Test
    public void testGetPostById() {
        int postId = 20;
        Response response = given()
                .when()
                .get("/posts/{id}", postId)
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(postId))
                .extract()
                .response();

        Assert.assertNotNull(response.body().asString());
    }

    @Test
    public void testGetPostByInvalidId() {
        int invalidPostId = 20;
        Response response = given()
                .when()
                .get("/posts/{id}", invalidPostId)
                .then()
                .assertThat()
                .statusCode(404)
                .extract()
                .response();

        Assert.assertEquals("", response.body().asString());
    }

    @Test
    public void testCreatePost() {
        String title = "foo";
        String body = "bar";
        int userId = 1;

        Response response = given()
                .header("Content-Type", "application/json")
                .body("{\"title\":\"" + title + "\",\"body\":\"" + body + "\",\"userId\":" + userId + "}")
                .when()
                .post("/posts")
                .then()
                .assertThat()
                .statusCode(201)
                .body("title", equalTo(title))
                .body("body", equalTo(body))
                .body("userId", equalTo(userId))
                .extract()
                .response();

        Assert.assertNotNull(response.body().asString());
    }
	
	 
}
