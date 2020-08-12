package badge.api.sanity;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class Recipients_APItest {

	@BeforeClass
	public void setup()
	{
		RestAssured.baseURI = "https://api.stackexchange.com";
		RestAssured.basePath = "/2.2";
		///2.2/badges/recipients?page=2&site=stackoverflow
	}
	
	@Test(description = "[API][Recipients] Verify the status code [Without ID]")
	public void StatusCodeVerification()
	{
		given()
				.param("site","stackoverflow")
		.when()
				.get("/badges/recipients")
		.then()
				.statusCode(200);
	}
	
	@Test(description = "[API][Recipients] Verify the response body")
	public void getResponseBody()
	{
		Response res =
		given()
			.param("site","stackoverflow")
		.when()
			.get("/badges/recipients");
		
		System.out.println(res.getBody().prettyPrint());
	}
	
	@Test(description = "[API][Recipients] Verify the response content is JSON")
	public void getResponseContent()
	{
		given()
			.param("page","2")
			.param("site","stackoverflow")
		.when()
			.get("/badges/recipients")
		.then()
			.contentType(ContentType.JSON);
	}
	
	@Test(description = "[API][Recipients] Verify that the content is displayed as per the page size")
	public void verifyContentWithPageSize()
	{
		given()
				.param("pagesize","3")
				.param("site","stackoverflow")
		.when()
				.get("/badges/recipients")
		.then()
				.body("items[2].badge_type", equalTo("named"));
	}
	
	@Test(description = "[API][Recipients] Verify correctness of badge ID in the response [ID provided]")
	public void verifyCorrectBadgeID()
	{
		String generatedBadgeID = ID_APItest.generateStringWithRandomNumberswithDelimiter(1);
		given()
				.param("pagesize","2")
				.param("site","stackoverflow")
		.when()
				.get("/badges/"+generatedBadgeID+"/recipients")
		.then()
				.body("items[1].badge_id", equalTo(Integer.parseInt(generatedBadgeID)));
	}
	
	@Test(description = "[API][Recipients] Verify the status code when 101 badge_ids are to be processed")
	public void verifyStatusCodeWith101()
	{
		String generatedBadgeID = ID_APItest.generateStringWithRandomNumberswithDelimiter(101);
		given()
				.param("pagesize","2")
				.param("site","stackoverflow")
		.when()
				.get("/badges/"+generatedBadgeID+"/recipients")
		.then()
				.statusCode(400);
	}
}
