package restapitests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static spec.Specification.*;

public class DeleteBooking {

    @Tag("remote")
    @DisplayName("Удаление бронирования")
    @Test
    public void deleteBookingId() {

        Integer Bookingid = 3494;


        step("Удаление бронирование по id и проверка статусу 201", () ->
                given()
                        .spec(requestSpec)
                        .when()
                        .delete("/booking/" + Bookingid)
                        .then()
                        .spec(responseSpec201)
                        .log().body());
    }

    @Tag("remote")
    @DisplayName("Удаление несуществующего бронирования")
    @Test
    public void deleteUnexcitingBookingId() {

        Integer Bookingid = 6654687;


        step("Удаление бронирование по id и проверка статусу 500", () ->
                given()
                        .spec(requestSpec)
                        .when()
                        .delete("/booking/" + Bookingid)
                        .then()
                        .spec(responseSpec405)
                        .log().body());
    }
}

