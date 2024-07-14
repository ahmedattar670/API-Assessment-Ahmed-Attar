package api_classes;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;
import java.util.Map;

public class RetrieveAllUsers {
	
	 @Test
	    public void testGetAllPosts() {
	        Response response = RestAssured.given()
	                .baseUri("https://jsonplaceholder.typicode.com")
	                .basePath("/posts")
	                .when()
	                .get();

	        // Assert the HTTP status code
	        Assert.assertEquals(200, response.getStatusCode());

	        // Assert the response body
	        List<Map<String, ?>> posts = response.jsonPath().getList("$");
	        Assert.assertEquals(100, posts.size());
	    }

	    @Test
	    public void testGetPostById() {
	        int postId = 1;
	        Response response = RestAssured.given()
	                .baseUri("https://jsonplaceholder.typicode.com")
	                .basePath("/posts/{id}")
	                .pathParam("id", postId)
	                .when()
	                .get();

	        // Assert the HTTP status code
	        Assert.assertEquals(200, response.getStatusCode());

	        // Assert the response body
	        Map<String, ?> post = response.jsonPath().getMap("$");
	        Assert.assertEquals(postId, post.get("id"));
	        Assert.assertEquals("sunt aut facere repellat provident occaecati excepturi optio reprehenderit", post.get("title"));
	    }

	    @Test
	    public void testCreatePost() {
	    	
	        Map<String, Object> requestBody = Map.of( "title", "foo","body", "bar", "userId", 1);

	        Response response = RestAssured.given()
	                .baseUri("https://jsonplaceholder.typicode.com")
	                .basePath("/posts")
	                .contentType(ContentType.JSON)
	                .body(requestBody)
	                .when()
	                .post();

	        // Assert the HTTP status code
	        Assert.assertEquals(201, response.getStatusCode());

	        // Assert the response body
	        Map<String, ?> createdPost = response.jsonPath().getMap("$");
	        Assert.assertEquals("foo", createdPost.get("title"));
	        Assert.assertEquals("bar", createdPost.get("body"));
	        Assert.assertEquals(1, createdPost.get("userId"));
	    }

}
