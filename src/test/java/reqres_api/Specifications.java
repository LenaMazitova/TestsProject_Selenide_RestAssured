package reqres_api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specifications {

    public static RequestSpecification requestSpec(String url) {
        return new RequestSpecBuilder()
                .setBaseUri(url)
                .setContentType(ContentType.JSON)
                .build();
    }
    public static ResponseSpecification responseOk(){
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }
    public static ResponseSpecification responseCreated(){
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .build();
    }
    public static ResponseSpecification responseNoContent(){
        return new ResponseSpecBuilder()
                .expectStatusCode(204)
                .build();
    }
    public static ResponseSpecification responseNotFound(){
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .build();
    }
    public static ResponseSpecification responseBadRequest(){
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .build();
    }

}
