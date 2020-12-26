package StepDefination;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefination  extends Utils {

	RequestSpecification res;
	ResponseSpecification resspec;
	Response respose;
	TestDataBuild data=new TestDataBuild();
	static String place_id;

@Given("Add Place Payload with {string}  {string}  {string}")
public void add_Place_Payload_with(String name, String langauage, String address) throws IOException {
	
	res=given().spec(requestSpecification())
			.body(data.addPlacePayLoad(name,langauage,address));
}

@When("user calls {string} with {string} http request")
public void user_calls_with_http_request(String resource, String method) {
	
	//constructor will be called with value of resource which you pass
	APIResources resourceAPI=APIResources.valueOf(resource);
	System.out.println(resourceAPI.getResource());
	
	
	resspec=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	if(method.equalsIgnoreCase("POST"))
	respose=res.when().post(resourceAPI.getResource());
	else if(method.equalsIgnoreCase("GET"))
		respose=res.when().get(resourceAPI.getResource());
			
}

@Then("the API call Got success with status code {int}")
public void the_API_call_Got_success_with_status_code(Integer int1) {
   
	assertEquals(respose.getStatusCode(),200);
}

@Then("{string} in response body is {string}")
public void in_response_body_is(String keyvalue, String expectedvalue) {
	
	
	assertEquals(getJsonPath(respose,keyvalue),expectedvalue);
}

@Then("verify Place_id created maps to {string} using {string}")
public void verify_Place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
	place_id=getJsonPath(respose,"place_id");
	res=given().spec(requestSpecification()).queryParam("place_id", place_id);
	user_calls_with_http_request(resource,"GET");
	String actualname=getJsonPath(respose,"name");
	
	assertEquals(actualname,expectedName);

}

@Given("DeletePlace payload")
public void deleteplace_payload() throws IOException {
	res=given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
}

}
