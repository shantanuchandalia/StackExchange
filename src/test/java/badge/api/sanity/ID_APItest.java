package badge.api.sanity;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.Random;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

// /2.2/badges/1?order=desc&sort=rank&site=stackoverflowRun

public class ID_APItest {

	public static int badge_id = 1;
	public static int badge_id_1 = 2;
	static Random r;
	
	@BeforeClass
	public void setup()
	{
		RestAssured.baseURI = "https://api.stackexchange.com";
		RestAssured.basePath = "/2.2";
	}
	
	@Test(description = "[API][ID] Verify the status code.")
	public void StatusCodeVerification()
	{
		given()
				.param("order","desc")
				.param("sort", "rank")
				.param("site","stackoverflow")
		.when()
				.get("/badges/"+generateStringWithRandomNumberswithDelimiter(1))
		.then()
				.statusCode(200);
	}
	
	@Test(description = "[API][ID] Verify the status code when the parameters are not provided [automatically takes the default value]")
	public void StatusCodeVerificationWithoutParameters()
	{
		given()
				.param("order","desc")
				//.param("sort", "rank")
				.param("site","stackoverflow")
		.when()
				.get("/badges/"+generateStringWithRandomNumberswithDelimiter(1))
		.then()
				.statusCode(200);
	}
	
	@Test(description = "[API][ID] Verify the response body")
	public void getResponseBody()
	{
		Response res =
		given()
				.param("order","desc")
				.param("sort", "rank")
				.param("site","stackoverflow")
		.when()
				.get("/badges/"+badge_id);
		
		System.out.println(res.getBody().prettyPrint());
	}
	
	@Test(description = "[API][ID] Verify that the response is in JSON")
	public void getResponseContentType()
	{
		given()
				.param("order","desc")
				.param("sort", "rank")
				.param("site","stackoverflow")
		.when()
				.get("/badges/"+badge_id)
		.then()
				.contentType(ContentType.JSON);
	}
	
	@Test(description = "[API][ID] Verify that the data is picked correctly as per the badge_id")
	public void verifyBadgeIDMapping()
	{
		given()
				.param("order","desc")
				.param("sort", "rank")
				.param("site","stackoverflow")
		.when()
				.get("/badges/"+badge_id)
		.then()
				.body("items[0].badge_id",equalTo(badge_id));
	}
	
	@Test(description = "[API][ID] Verify that by sending multiple IDs [2], multiple items are present in json ")
	public void verifySemicolonDelimiter()
	{
		given()
				.param("order","desc")
				.param("sort", "rank")
				.param("site","stackoverflow")
		.when()
				.get("/badges/"+badge_id+";"+badge_id_1)
		.then()
				.body("items[1].badge_id",equalTo(badge_id_1));		
	}
	
	@Test(description = "[API][ID] Verify that error is thrown when the badge_id is not sent as an int")
	public void verifyBadgeIDInputNegative()
	{
		given()
				.param("order","desc")
				.param("sort", "rank")
				.param("site","stackoverflow")
		.when()
				.get("/badges/"+"negative")
		.then()
				.body("error_id",equalTo(404));
	}
	
	@Test(description = "[API][ID] Verify the status code when 100 badge_ids are to be processed")
	public void multipleBadgeIDLimit100()
	{
		given()
				.param("order","desc")
				.param("sort", "rank")
				.param("site","stackoverflow")
		.when()
				.get("/badges/"+generateStringWithRandomNumberswithDelimiter(100))
		.then()
				.statusCode(200);
	}
	
	@Test(description = "[API][ID] Verify the status code when 101 badge_ids are to be processed")
	public void multipleBadgeIDLimit101()
	{
		given()
				.param("order","desc")
				.param("sort", "rank")
				.param("site","stackoverflow")
		.when()
				.get("/badges/"+generateStringWithRandomNumberswithDelimiter(101))
		.then()
				.statusCode(400);
	}
	
	//Method to generate random numbers as per the parameter <count> and converted to a string with a delimiter ";" 
	public static String generateStringWithRandomNumberswithDelimiter(int count)
	{
		r = new Random();
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<count;i++)
		{
			sb.append(r.nextInt(200));
			sb.append(";");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
}
