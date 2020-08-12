package badge.api.sanity;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Tags_APItest {

	
	@BeforeClass
	public void setup()
	{
		RestAssured.baseURI = "https://api.stackexchange.com";
		RestAssured.basePath = "/2.2";
	}
	
	@Test(description = "[API][Tags] Verify the status code")
	public void StatusCodeVerification()
	{
		given()
				.param("order","desc")
				.param("sort", "rank")
				.param("site","stackoverflow")
		.when()
				.get("/badges/tags")
		.then()
				.statusCode(200);
	}
	@Test(description = "[API][Tags] Verify the response body")
	public void getResponseBody()
	{
		Response res =
		given()
				.param("order","desc")
				.param("sort", "rank")
				.param("site","stackoverflow")
		.when()
				.get("/badges/tags");
		System.out.println(res.getBody().prettyPrint());
	}
	
	@Test(description = "[API][Tags] Verify that the response content is JSON")
	public void verifyResponseContent()
	{
		given()
				.param("order","desc")
				.param("sort", "rank")
				.param("site","stackoverflow")
		.when()
				.get("/badges/tags")
		.then()
				.contentType(ContentType.JSON);
	}
	
	//For PageSize = 1 & Page = 1, rank would always be Bronze
	@Test(description = "[API][Tags] Verify that Bronze is the greatest, when rank sort is done")
	public void verifyRank()
	{
		given()
				.param("order","desc")
				.param("sort", "rank")
				.param("site","stackoverflow")
				.param("page", "1")
				.param("pagesize", "1")
		.when()
				.get("/badges/tags")
		.then()
				.body("items[0].rank",equalTo("bronze"));
	}
	
	//If the rank is not provided and we select the first page and pagesize = 1, we would still get bronze as the rank.
	@Test(description = "[API][Tags] Verify that the rank is the default sort")
	public void verifyDefaultSort()
	{
		given()
				.param("order","desc")
				.param("site","stackoverflow")
				.param("page", "1")
				.param("pagesize", "1")
		.when()
				.get("/badges/tags")
		.then()
				.body("items[0].rank",equalTo("bronze"));
	}
	
}
