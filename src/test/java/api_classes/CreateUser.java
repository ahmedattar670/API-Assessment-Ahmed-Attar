package api_classes;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class CreateUser {
	
	    @Test
	    public void testCreateUser() throws JsonProcessingException {
	        // Set the base URI
	        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

	        // Create a new user
	        Map<String, Object> requestBody = new HashMap<>();
	        requestBody.put("title", "foo");
	        requestBody.put("body", "bar");
	        requestBody.put("userId", 1);

	     // Use ObjectMapper to serialize the request body
	        ObjectMapper objectMapper = new ObjectMapper();
	        String requestBodyJson = objectMapper.writeValueAsString(requestBody);
	        
	        Response response = RestAssured.given()
	                .contentType(ContentType.JSON)
	                .body(requestBody)
	                .post("/posts")
	                .then()
	                .extract().response();

	     // Use ObjectMapper to deserialize the response body
	        Map<String, Object> responseBody = objectMapper.readValue(response.asString(), 
	        		new TypeReference<Map<String, Object>>() {});
	        
	        // Assert on the response
	        Assert.assertEquals(201, response.getStatusCode());
	        Assert.assertEquals("foo", response.jsonPath().getString("title"));
	        Assert.assertEquals("bar", response.jsonPath().getString("body"));
	        Assert.assertEquals(1, response.jsonPath().getInt("userId"));
	    }
	    
	    @Test
	    public void testGetAllPosts() {
	        // Set the base URI
	        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

	        // Send a GET request to "/posts"
	        Response response = RestAssured.given()
	                .get("/posts")
	                .then()
	                .extract().response();

	        // Assert on the response
	        Assert.assertEquals(200, response.getStatusCode());
	        Assert.assertTrue(response.jsonPath().getList("").size() > 0);
	    }
	    
	    @Test
	    public void testGetPostById() {
	        // Set the base URI
	        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

	        // Send a GET request to "/posts/1"
	        Response response = RestAssured.given()
	                .pathParam("id", 1)
	                .get("/posts/{id}")
	                .then()
	                .extract().response();

	        // Assert on the response
	        Assert.assertEquals(200, response.getStatusCode());
	        Assert.assertEquals(1, response.jsonPath().getInt("id"));
	        Assert.assertEquals("sunt aut facere repellat provident occaecati excepturi optio reprehenderit", response.jsonPath().getString("title"));
	    }
}


