package restapitests;

import models.BookingDatesModel;
import models.ClientInformationModel;
import models.CreateBookingModel;
import models.CreateBookingResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static spec.Specification.*;

public class GetBooking {

    @Tag("remote")
    @DisplayName("Получение информации о имени клиента по id")
    @Test
    public void getFirstNameAboutBooking() {

        Integer Bookingid = 1;


        ClientInformationModel clientInformation = step("Отправка запроса на получение информации о бронировании",
                () -> given()
                        .spec(requestSpec)
                        .when()
                        .get("booking/" + Bookingid)
                        .then()
                        .spec(responseSpec200)
                        .extract().as(ClientInformationModel.class));

        step("Проверка на соотвествие имения клиента с его id", () -> {
            assertThat(clientInformation.getFirstname()).isEqualTo("Jim");

        });
    }

    @DisplayName("Проверка на несуществующее бронирование")
    @Test
    public void getErrorAboutBooing() {

        Integer Bookingid = 12000;

        step("Отправка запроса по несуществующему id и получение ответа 404",
                () ->
        given()
                .spec(requestSpec)
                .when()
                .get("/booking/" + Bookingid)
                .then()
                .spec(responseSpec404));
    }

    @DisplayName("Получение полной информации о бронировании по id")
    @Test
    public void getFullInformationAboutBooing() {

        Integer Bookingid = 5;

        step("Отправка запроса по существующему id для получение полной информации по бронированию",  () ->
                given()
                .spec(requestSpec)
                .when()
                .get("/booking/" + Bookingid)
                .then()
                .spec(responseSpec200)
                .log().body());
    }
}
