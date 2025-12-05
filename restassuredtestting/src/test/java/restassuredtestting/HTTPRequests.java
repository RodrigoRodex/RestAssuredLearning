package restassuredtestting;

import org.apache.http.HttpStatus;
import org.testng.annotations.*;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

public class HTTPRequests {
	
	@BeforeClass
	public static void setup() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		baseURI = "https://api.restful-api.dev";
	}
	
	String id; //usando string pq o site passa a id como string, não como int
	
	@Test (priority=1)
	void getUsers() {
		//pegando um user qualquer e verificando se contem o id
		given()
			.when()
				.get("/objects")
			.then()
				.statusCode(HttpStatus.SC_OK)
				.body("id",hasItem("3"));
	}
	@Test(priority=2)
	void createUser() {
		//criando um user de forma bruta com o HashMap e pegando a id como uma variável
		HashMap data=new HashMap();
		data.put("name", "android");
		id=given()
			.contentType("application/json")
			.body(data)
			
			.when()
				.post("/objects")
				.jsonPath().getString("id")
				;
				
			//.then()
				//.statusCode(HttpStatus.SC_OK)
				//.log().all();
	}
	
	@Test(priority=3,dependsOnMethods= {"createUser"})
	void updateUser() {
		//usando o id do user criado anteriormente para verificar o user
		HashMap data=new HashMap();
		data.put("name", "androids");

		given()
			.contentType("application/json")
			.body(data)
			
			.when()
				.put("/objects/"+id)
				
			.then()
			.statusCode(HttpStatus.SC_OK);
				
	}
	@Test (priority=4)
	void deleteUser() {
		given()
		//apagando o user criado pelo id
			.when()
				.delete("/objects/"+id)
			.then()
				.statusCode(HttpStatus.SC_OK);
			;
	}
	
}
