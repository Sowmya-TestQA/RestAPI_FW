import org.testng.Assert;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexParseJson {
	
	public static void main(String args[]) {
		JsonPath js = new JsonPath(Payload.CoursePrice()); //mock response
		
		int count = js.getInt("courses.size()");
		System.out.println("Count of courses "+ count); //No of courses
		
		int purchaseamount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase amount "+ purchaseamount); //Purchase amount
		
		String coursetitle = js.getString("courses[0].title");
		System.out.println("Course title :"+coursetitle); //Title of first course
		
		for(int i=0; i<count; i++) {
			System.out.println(js.getString("courses["+i+"].title") +"   "+ js.getInt("courses["+i+"].price"));//Prints all course titles and its prices
		}
		
		for(int i=0; i<count; i++) {
			if(js.getString("courses["+i+"].title").equals("RPA")) {
			System.out.println(js.getInt("courses["+i+"].copies"));//Prints all course titles and its prices
			break;
		}}//No of copies sold by RPA course
		
		int mult =0;
		for(int i=0; i<count; i++) {
		  mult = mult + js.getInt("courses["+i+"].price") * js.getInt("courses["+i+"].copies");	
	}
		if(mult == js.getInt("dashboard.purchaseAmount")) {
			Assert.assertEquals(mult, js.getInt("dashboard.purchaseAmount"));
			 System.out.println("Verified and sum of all course prics matches total purchased amount  "+ mult);
		 }
		}

}
