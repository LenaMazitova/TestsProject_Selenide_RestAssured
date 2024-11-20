package reqresApi;

import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import reqresApi.models.*;
import selenide.TestListener;
import java.time.Instant;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@Tag("API")
@ExtendWith(TestListener.class)
@Feature("API-тестирование")
@Epic("Тестирование эндпоинтов сайта reqres.in")
public class ReqresTest {
    private final String BASE_URL = "https://reqres.in/";

    /**
     * Проверяем, что все аватары представляют из себя URL-пути на jpg файлы
     */
    @Issue(value = "FAT-128628")
    @Link(name = "https://reqres.in/", url = "https://reqres.in/")
    @Owner(value = "Lena Mazitova")
    @DisplayName("GET-запрос - все аватары представляют из себя URL-пути на jpg файлы")
    @Description("""
            GET-запрос с параметром page=2, получаем JSON-строку, проверяем корректность 
            содержания поля data.avatar
            """)
    @Story("Позитивные")
    @Test
    public void listUsersCheckAvatar(){
        List<String> listAvatars = given()
                .spec(Specifications.requestSpec(BASE_URL))
                .queryParam("page", 2)
                .when()
                .get("api/users")
                .then().log().ifError()
                .spec(Specifications.responseOk())
                .extract().body().jsonPath().getList("data.avatar", String.class);
        boolean checkAvatars = listAvatars.stream()
                .allMatch(el -> el.startsWith("https://reqres.in") && el.endsWith(".jpg"));
        assertTrue(checkAvatars);
    }

    /**
     * Проверяем, что количество всех элементов в поле ответа data соответсвует 6
     */
    @Issue(value = "FAT-128628")
    @Link(name = "https://reqres.in/", url = "https://reqres.in/")
    @Owner(value = "Lena Mazitova")
    @DisplayName("GET-запрос - количество всех элементов в поле ответа data соответсвует 6")
    @Description("""
            GET-запрос с параметром delay=3, получаем JSON-строку, проверяем количество элементов 
            в поле data
            """)
    @Story("Позитивные")
    @Test
    public void listUsersDelayParam(){
        List<User> listUsers = given()
                .spec(Specifications.requestSpec(BASE_URL))
                .queryParam("delay", 3)
                .when()
                .get("api/users")
                .then().log().ifError()
                .spec(Specifications.responseOk())
                .extract().body().jsonPath().getList("data", User.class);
        Long countUsers = listUsers.stream()
                .count();
        assertEquals(6L, countUsers);
    }

    /**
     * Проверяем, что все id присутствуют разные и другие поля data не пустые
     */
    @Issue(value = "FAT-128628")
    @Link(name = "https://reqres.in/", url = "https://reqres.in/")
    @Owner(value = "Lena Mazitova")
    @DisplayName("GET-запрос - все id присутствуют разные и другие поля data не пустые")
    @Description("""
            GET-запрос с параметром page=2, получаем JSON-строку, проверяем содержание поля data:
            все id должны быть уникальные, все другие элементы не являются пустыми строками
            """)
    @Story("Позитивные")
    @Test
    public void listUsersCheckData(){
        List<User> users = given()
                .spec(Specifications.requestSpec(BASE_URL))
                .param("page", 2)
                .when()
                .get("api/users")
                .then().log().ifError()
                .spec(Specifications.responseOk())
                .extract().body().jsonPath().getList("data", User.class);
        long allCount = users.stream()
                .map(User::getId)
                .count();
        long distinctCount = users.stream()
                .map(User::getId)
                .distinct()
                .count();
        assertEquals(allCount, distinctCount);
        boolean allFieldsChecked = users.stream()
                .allMatch(el -> !el.getEmail().equals("")
                        && !el.getFirst_name().equals("")
                        && !el.getLast_name().equals("")
                        && !el.getAvatar().equals(""));
        assertTrue(allFieldsChecked);
    }

    /**
     * Проверяем, что полученные данные пользователя соответствуют ожидаемым
     */
    @Issue(value = "FAT-128628")
    @Link(name = "https://reqres.in/", url = "https://reqres.in/")
    @Owner(value = "Lena Mazitova")
    @DisplayName("GET-запрос - полученные данные пользователя соответствуют ожидаемым")
    @Description("""
            GET-запрос api/users/2, получаем JSON-строку, проверяем содержание поля data
            на полное совпадение ожидаемым значениям
            """)
    @Story("Позитивные")
    @Test
    public void singleUserCheckData() {
        User user = given()
                .spec(Specifications.requestSpec(BASE_URL))
                .when()
                .get("api/users/2")
                .then().log().body()
                .spec(Specifications.responseOk())
                .extract().body().jsonPath().getObject("data", User.class);
        assertAll("Single user assertions",
                () -> assertEquals(2, user.getId(), "ID не совпадает"),
                () -> assertEquals("janet.weaver@reqres.in", user.getEmail(), "Email не совпадает"),
                () -> assertEquals("Janet", user.getFirst_name(), "First_name не совпадает"),
                () -> assertEquals("Weaver", user.getLast_name(), "Last_name не совпадает"),
                () -> assertEquals("https://reqres.in/img/faces/2-image.jpg", user.getAvatar(), "Avatar не совпадает")
        );
    }

    /**
     * Проверяем, что по невалидному id пользователь не найден
     */
    @Issue(value = "FAT-128628")
    @Link(name = "https://reqres.in/", url = "https://reqres.in/")
    @Owner(value = "Lena Mazitova")
    @DisplayName("GET-запрос - по невалидному id пользователь не найден")
    @Description("""
            GET-запрос api/users/23, 23 - невалидный id пользователя, проверяем ответ 404 NotFound
            """)
    @Story("Негативные")
    @Test
    public void singleUserNotFound() {
        String responseBody = given()
                .spec(Specifications.requestSpec(BASE_URL))
                .when()
                .get("api/users/23")
                .then().log().ifError()
                .spec(Specifications.responseNotFound())
                .extract()
                .body()
                .asString();
        Assertions.assertEquals("{}", responseBody);
    }

    /**
     * Проверяем, что ответ содержит те же данные, что были отправлены, а также
     * присвоен id и время, совпадающее с локальным временем (до мин. вкл.)
     */
    @Issue(value = "FAT-128628")
    @Link(name = "https://reqres.in/", url = "https://reqres.in/")
    @Owner(value = "Lena Mazitova")
    @DisplayName("POST-запрос - при создании записи ответ содержит корректные данные")
    @Description("""
            POST-запрос, получаем JSON-строку, проверяем содержание всего ответа на полное 
            совпадение ожидаемым значениям, id должен присутствовать, createdAt должно
            соответствовать времени (до мин. вкл.) локальной машины  
            """)
    @Story("Позитивные")
    @Test
    public void createUser() {
        CreatedUserResponse response = given()
                .spec(Specifications.requestSpec(BASE_URL))
                .body(new CreatedUserRequest("morpheus", "leader"))
                .log().body()
                .when()
                .post("api/users")
                .then().log().body()
                .spec(Specifications.responseCreated())
                .extract()
                .as(CreatedUserResponse.class);
        String now = Instant.now().toString();
        String expectedCreatedAt = now.replaceFirst(":\\d{2}\\.\\d{3,}Z$", "");
        String actual = response.getCreatedAt().replaceFirst(":\\d{2}\\.\\d{3,}Z$", "");

        assertAll("Create user assertions",
                () -> assertEquals("morpheus", response.getName(), "Name не совпадает"),
                () -> assertEquals("leader", response.getJob(), "Job не совпадает"),
                () -> assertNotNull(response.getId(), "Id отсутствует"),
                () -> assertEquals(expectedCreatedAt, actual, "CreatedAt не совпадает")
        );
    }

    /**
     * Проверяем, что ответ содержит те же данные, что были отправлены, а также
     * есть поле времени обновления
     */
    @Issue(value = "FAT-128628")
    @Link(name = "https://reqres.in/", url = "https://reqres.in/")
    @Owner(value = "Lena Mazitova")
    @DisplayName("PUT-запрос - при обновлении ответ содержит корректные данные")
    @Description("""
            PUT-запрос с параметром id=2, получаем JSON-строку, проверяем содержание всего ответа на полное 
            совпадение ожидаемым значениям, createdAt должно присутствовать
            """)
    @Story("Позитивные")
    @Test
    public void updateUser() {
        UpdatedUserResponse response = given()
                .spec(Specifications.requestSpec(BASE_URL))
                .body(new CreatedUserRequest("morpheus", "zion resident"))
                .log().body()
                .pathParam("id", 2)
                .when()
                .put("api/users/{id}")
                .then().log().body()
                .spec(Specifications.responseOk())
                .extract()
                .body().as(UpdatedUserResponse.class);

        assertAll("Update user assertions",
                () -> assertEquals("morpheus", response.getName(), "Name не совпадает"),
                () -> assertEquals("zion resident", response.getJob(), "Job не совпадает"),
                () -> assertNotNull(response.getUpdatedAt(), "CreatedAt отсутствует")
        );
    }

    /**
     * Проверяем, что по запросу на удаление пользователя по id получаем ответ-статус 204
     */
    @Issue(value = "FAT-128628")
    @Link(name = "https://reqres.in/", url = "https://reqres.in/")
    @Owner(value = "Lena Mazitova")
    @DisplayName("DELETE-запрос - при удалении записи получаем ответ-статус NoContent")
    @Description("""
            DELETE-запрос с параметром id=2, получаем только статус, проверяем ожидаемый статус NoContent
            """)
    @Story("Позитивные")
    @Test
    public void deleteUser() {
        given()
                .spec(Specifications.requestSpec(BASE_URL))
                .pathParam("id", 2)
                .when()
                .delete("api/users/{id}")
                .then()
                .spec(Specifications.responseNoContent());
    }

    /**
     * Проверяем, что при регистрации пользователя поступает ответ,
     * содержащий поле id и поле токен с валидным значением (17 символов: буквы, цифры)
     */
    @Issue(value = "FAT-128628")
    @Link(name = "https://reqres.in/", url = "https://reqres.in/")
    @Owner(value = "Lena Mazitova")
    @DisplayName("POST-запрос - при создании записи ответ содержит корректные данные")
    @Description("""
            POST-запрос на api/register, получаем JSON-строку, проверяем содержание всего ответа на
            совпадение ожидаемым значениям, поле id createdAt должно присутствовать, поле token
            должно соответствовать шаблону (17 символов: лат. буквы, цифры)
            """)
    @Story("Позитивные")
    @Test
    public void registerUser() {
       RegisterUserResponse response = given()
                .spec(Specifications.requestSpec(BASE_URL))
                .body(new RegisterUserRequest("eve.holt@reqres.in", "pistol"))
                .log().body()
                .when()
                .post("api/register")
                .then().log().body()
                .spec(Specifications.responseOk())
                .extract()
                .body().as(RegisterUserResponse.class);

        assertAll("Register user assertions",
                () -> assertTrue(response.getToken().matches("[0-9a-zA-Z]{17}"),
                        "Token не соответствует регулярному выражению"),
                () -> assertNotNull(response.getId(), "Id отсутствует")
        );
    }

    /**
     * Проверяем, что при регистрации пользователя с невалидным паролем
     * поступает статус 400
     */
    @Issue(value = "FAT-128628")
    @Link(name = "https://reqres.in/", url = "https://reqres.in/")
    @Owner(value = "Lena Mazitova")
    @DisplayName("POST-запрос - при регистрации пользователя с невалидным паролем ответ BadRequest")
    @Description("""
            POST-запрос на api/register с паролем = null, проверяем ответ BadRequest
            """)
    @Story("Негативные")
    @Test
    public void registerInvalidPasswordUser() {
        given()
                .spec(Specifications.requestSpec(BASE_URL))
                .body(new RegisterUserRequest("eve.holt@reqres.in", null))
                .log().body()
                .when()
                .post("api/register")
                .then().log().status()
                .spec(Specifications.responseBadRequest());
    }

    /**
     * Проверяем, что при регистрации пользователя с невалидной почтой
     * поступает статус 400
     */
    @Issue(value = "FAT-128628")
    @Link(name = "https://reqres.in/", url = "https://reqres.in/")
    @Owner(value = "Lena Mazitova")
    @DisplayName("POST-запрос - при регистрации пользователя с невалидной эл. почтой ответ BadRequest")
    @Description("""
            POST-запрос на api/register с эл. почтой, содержащей недопустимые символы, проверяем ответ BadRequest
            """)
    @Story("Негативные")
    @Test
    public void registerInvalidEmailUser() {
        given()
                .spec(Specifications.requestSpec(BASE_URL))
                .body(new RegisterUserRequest(".@lll", "pistol"))
                .log().body()
                .when()
                .post("api/register")
                .then().log().status()
                .spec(Specifications.responseBadRequest());
    }

    /**
     * Проверяем, что при регистрации пользователя с отсутствующим
     * паролем поступает статус 400
     */
    @Issue(value = "FAT-128628")
    @Link(name = "https://reqres.in/", url = "https://reqres.in/")
    @Owner(value = "Lena Mazitova")
    @DisplayName("POST-запрос - при регистрации пользователя с отсутствующим паролем ответ BadRequest")
    @Description("""
            POST-запрос на api/register с телом запроса, в котором отсутствует поле "password", 
            проверяем ответ BadRequest
            """)
    @Story("Негативные")
    @Test
    public void registerMissingPasswordUser() {
        given()
                .spec(Specifications.requestSpec(BASE_URL))
                .body("{"+ "\"email\": \"eve.holt@reqres.in\""+"}")
                .log().body()
                .when()
                .post("api/register")
                .then().log().status()
                .spec(Specifications.responseBadRequest());
    }

    /**
     * Проверяем, что при отправке валидных почты и пароля пользователя поступает ответ,
     * содержащий поле токен с валидным значением (17 символов: буквы, цифры)
     */
    @Issue(value = "FAT-128628")
    @Link(name = "https://reqres.in/", url = "https://reqres.in/")
    @Owner(value = "Lena Mazitova")
    @DisplayName("POST-запрос - при аутентификации пользователя ответ содержит корректные данные")
    @Description("""
            POST-запрос на api/login с валидным телом запроса, получаем JSON-строку, проверяем 
            корректность содержания поля token (17 символов: лат. буквы, цифры)
            """)
    @Story("Позитивные")
    @Test
    public void loginUser() {
        String token = given()
                .spec(Specifications.requestSpec(BASE_URL))
                .body(new RegisterUserRequest("eve.holt@reqres.in", "cityslicka"))
                .log().body()
                .when()
                .post("api/login")
                .then().log().body()
                .spec(Specifications.responseOk())
//                .extract().as(RegisterUserResponse.class);
                .extract().body().jsonPath().getString("token");

        assertTrue(token.matches("[0-9a-zA-Z]{17}"));
    }

    /**
     * Проверяем, что при отправке невалидного запроса с отсутствующим паролем,
     * получаем ответ со статусом 400 и ответом, содержим сообщение
     */
    @Issue(value = "FAT-128628")
    @Link(name = "https://reqres.in/", url = "https://reqres.in/")
    @Owner(value = "Lena Mazitova")
    @DisplayName("POST-запрос - при аутентификации пользователя с отсутствующим паролем ответ BadRequest")
    @Description("""
            POST-запрос на api/login с телом запроса, в котором отсутствует поле "password", 
            проверяем ответ BadRequest и соответствующий ожидаемому текст сообщения
            """)
    @Story("Негативные")
    @Test
    public void loginMissingPasswordUser() {
        String error = given()
                .spec(Specifications.requestSpec(BASE_URL))
                .body("{"+ "\"email\": \"eve.holt@reqres.in\""+"}")
                .log().body()
                .when()
                .post("api/login")
                .then().log().body()
                .spec(Specifications.responseBadRequest())
                .extract().body().jsonPath().getString("error");

        assertEquals("Missing password", error);
    }

}
