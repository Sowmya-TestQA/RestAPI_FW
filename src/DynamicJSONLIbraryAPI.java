import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJSONLIbraryAPI {

	
	@Test(dataProvider = "DataforBooks")
	public void addBook(String aisle, String isbn) {
		RestAssured.baseURI = "http://216.10.245.166";
		String Bookcreated = given().log().all().header("Content-Type","application/json")
				.body(Payload.addBook(aisle, isbn)).when().post("Library/Addbook.php")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(Bookcreated);
		String id = js.get("ID");
		System.out.println(id);	
	}
	
	@DataProvider(name="DataforBooks")
	public Object[][] getData() {
		Object[][] obj = new Object[][] {{"278781", "bcdw"},{"273381", "bddw"},{"998781", "jjw"}};
		return obj;
	}
}
