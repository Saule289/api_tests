package restapitests;

import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static spec.Specification.requestSpec;
import static spec.Specification.responseSpec200;

public class UpdateBooking {

    @Tag("remote")
    @DisplayName("Изменение информации по бронированию")
    @Test
    public void updateBookingWithAdditionalNeeds() {
        Integer Bookingid = 1;
        BookingDatesModel bookingDatesModel = new BookingDatesModel();
        bookingDatesModel.setCheckin("2023-04-20");
        bookingDatesModel.setCheckout("2023-04-20");


        UpdateBookingRequest updateBook = new UpdateBookingRequest();
        updateBook.setFirstname("Saule");
        updateBook.setLastname("Zhan");
        updateBook.setTotalprice(3000);
        updateBook.setDepositpaid(true);
        updateBook.setBookingdates(bookingDatesModel);
        updateBook.setAdditionalneeds("Breakfast");


        UpdateBookingResponse updateResponse = step("Отправить запрос на изменение параметров бронирования", () ->
                given()
                        .spec(requestSpec)
                        .body(updateBook)
                        .when()
                        .put("booking/" + Bookingid)
                        .then()
                        .spec(responseSpec200)
                        .extract().as(UpdateBookingResponse.class));

        step("Проверка, что в бронировании поменялась доп.опция на Завтрак", () -> {
            assertThat(updateResponse.getAdditionalneeds()).isEqualTo("Breakfast");
        });
    }

    @Tag("remote")
    @DisplayName("Частиние изменение информации по бронированию")
    @Test
    public void partialUpdateBooking() {

        Integer Bookingid = 2;
        BookingDatesModel bookingDatesModel = new BookingDatesModel();
        bookingDatesModel.setCheckin("2023-04-20");
        bookingDatesModel.setCheckout("2023-04-20");


        UpdateBookingRequest updateBook = new UpdateBookingRequest();
        updateBook.setTotalprice(3000);
        updateBook.setDepositpaid(false);
        updateBook.setBookingdates(bookingDatesModel);


        UpdateBookingResponse updateResponse = step("Отправить post-запрос на создание бронирования с доплнительными опциями", () ->
                given()
                        .spec(requestSpec)
                        .body(updateBook)
                        .when()
                        .patch("booking/" + Bookingid)
                        .then()
                        .spec(responseSpec200)
                        .extract().as(UpdateBookingResponse.class));

        step("Проверка, что значения у bookingid есть значение", () -> {
            assertThat(updateResponse.getDepositpaid()).isEqualTo(false);
        });
    }
}
