import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;

public class Basics {

	public static void main(String[] args) {
		// Validate Add place API in google maps
		//given - All input details
		//when - Submit the API with resource url and http method
		//then - Validate the response
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		 String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(Payload.AddPlace())
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("server","Apache/2.4.52 (Ubuntu)").extract().response().asString();
		 System.out.println(response);
		 
		 JsonPath js = new JsonPath(response); // for parsing json response.
		 String placeid = js.getString("place_id");
		 System.out.println("Place id : "+placeid); //Till this  - it creates new place in google maps
		 
		 //Below update API which updates details for the place we created above.
		 String newAddress = "70 winter walk, USA";
		 String putresponse = given().log().all().queryParam("place_id", placeid).queryParam("key", "qaclick123").header("Content-Type","application/json")
		 .body("{\r\n"
		 		+ "\"place_id\":\""+placeid+"\",\r\n"
		 		+ "\"address\":\""+newAddress+"\",\r\n"
		 		+ "\"key\":\"qaclick123\"\r\n"
		 		+ "}")
		 .when().put("maps/api/place/update/json").then().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated")).extract().asString();
		 System.out.println(putresponse);
		 
		 //Get API to validate whether the above update is done or not.
		 
		String getResponse = given().log().all().queryParam("place_id", placeid).queryParam("key", "qaclick123").
		 when().get("maps/api/place/get/json")
		 .then().assertThat().log().all().extract().asString();
		
		JsonPath js1 = new JsonPath(getResponse); 
		String validateaddress = js1.getString("address");
		System.out.println(validateaddress);
		Assert.assertEquals(validateaddress, newAddress);
	}

}
