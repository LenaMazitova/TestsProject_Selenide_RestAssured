package reqresApi;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
//import org.hamcrest.Matchers;

public class Specifications {

    public static RequestSpecification requestSpec(String url) {
        return new RequestSpecBuilder()
                .addFilter(new AllureRestAssured())
                .setBaseUri(url)
                .setContentType(ContentType.JSON)
                .build();
    }
    public static ResponseSpecification responseOk(){
        return new ResponseSpecBuilder()
//                .expectResponseTime(Matchers.lessThan(3000L))
                .expectStatusCode(200)
                .build();
    }
    public static ResponseSpecification responseCreated(){
        return new ResponseSpecBuilder()
//                .expectResponseTime(Matchers.lessThan(3000L))
                .expectStatusCode(201)
                .build();
    }
    public static ResponseSpecification responseNoContent(){
        return new ResponseSpecBuilder()
//                .expectResponseTime(Matchers.lessThan(3000L))
                .expectStatusCode(204)
                .build();
    }
    public static ResponseSpecification responseNotFound(){
        return new ResponseSpecBuilder()
//                .expectResponseTime(Matchers.lessThan(3000L))
                .expectStatusCode(404)
                .build();
    }
    public static ResponseSpecification responseBadRequest(){
        return new ResponseSpecBuilder()
//                .expectResponseTime(Matchers.lessThan(3000L))
                .expectStatusCode(400)
                .build();
    }

}
