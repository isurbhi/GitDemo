package stepDefinations;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefination extends Utils{
	RequestSpecification res ;
	ResponseSpecification resspec;
	Response ress ;
	static String placeId;

	TestDataBuild data = new TestDataBuild();
	
	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
		res = given()
				.spec(requestSpecifications())
				.body(data.addPlacePayload(name , language, address));
	}



	@When("User calls {string} with {string} http Request")
	public void user_calls_with_post_http_request(String resource, String method) {
		
		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		if(method.equalsIgnoreCase("POST"))
			ress = res.when().post(resourceAPI.getResource()).then()
					.spec(resspec)
					.extract().response();
		else if(method.equalsIgnoreCase("GET"))
			ress = res.when().get(resourceAPI.getResource()).then()
			.spec(resspec)
			.extract().response();
 
	}

	@Then("The API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) {
	    assertEquals(ress.getStatusCode(), 200);
	    
	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String ExpectedValue) {
		String resp = ress.asString();
		JsonPath js = new JsonPath(resp);
		
		assertEquals(getJsonPath(keyValue, ress), ExpectedValue);
	    
	}
	
	@Then("Verify place_Id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
	    
		placeId = getJsonPath("place_id", ress);
		
		res = given().spec(requestSpecifications()).queryParam("place_id", placeId);
		user_calls_with_post_http_request(resource, "GET");
		String actualName = getJsonPath("name", ress);
		assertEquals(actualName, expectedName);
		
	    
	}
	
	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
	    
		res = given()
		.spec(requestSpecifications())
		.body(data.deletePlacePayload(placeId));
	}
	
}
