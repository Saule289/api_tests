package restapitests;

import models.BookingDatesModel;
import models.ClientInformationModel;
import models.CreateBookingModel;
import models.CreateBookingResponseModel;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static spec.Specification.*;

public class CreateBooking {


    @Tag("remote")
    @DisplayName("Бронирование c дополнительными опциями")
    @Test
    public void createBookingWithAdditionalNeeds() {
        BookingDatesModel bookingDatesModel = new BookingDatesModel();
        bookingDatesModel.setCheckin("2023-01-13");
        bookingDatesModel.setCheckout("2023-01-13");


        CreateBookingModel createBook = new CreateBookingModel();
        createBook.setFirstname("Saule");
        createBook.setLastname("Zhan");
        createBook.setTotalprice(1000);
        createBook.setDepositpaid(true);
        createBook.setBookingdates(bookingDatesModel);
        createBook.setAdditionalneeds("Lunch");


        CreateBookingResponseModel responseModel = step("Отправить post-запрос на создание бронирования с доплнительными опциями", () ->
                given()
                        .spec(requestSpec)
                        .body(createBook)
                        .when()
                        .post("/booking")
                        .then()
                        .spec(responseSpec200)
                        .extract().as(CreateBookingResponseModel.class));

        step("Проверка, что значения у bookingid есть значение", () -> {
            assertThat(responseModel.getBookingid()).isNotNull();
        });
    }

    @DisplayName("Бронирование без дополнительной опции")
    @Test
    public void createBookingWithoutAdditionalNeeds() {
        BookingDatesModel bookingDatesModel = new BookingDatesModel();
        bookingDatesModel.setCheckin("2023-01-13");
        bookingDatesModel.setCheckout("2023-01-13");


        CreateBookingModel createBook = new CreateBookingModel();
        createBook.setFirstname("Test");
        createBook.setLastname("Test");
        createBook.setTotalprice(500);
        createBook.setDepositpaid(true);
        createBook.setBookingdates(bookingDatesModel);

        ClientInformationModel clientInformation = step("Отправить post-запрос для создания брониования без дополнительных опций", () ->
                given()
                        .spec(requestSpec)
                        .body(createBook)
                        .when()
                        .post("/booking")
                        .then()
                        .spec(responseSpec200)
                        .extract().as(ClientInformationModel.class));

        step("Проверка, что в ответе нет дополнительной опции ", () -> {
            assertThat(clientInformation.getAdditionalneeds()).isNull();
        });
    }


    @DisplayName("Неуспешное бронирование без имени и фамилии клиента")
    @Test
    public void createBookingWithoutSendingNames() {
        BookingDatesModel bookingDatesModel = new BookingDatesModel();
        bookingDatesModel.setCheckin("2023-01-13");
        bookingDatesModel.setCheckout("2023-01-13");


        CreateBookingModel createBook = new CreateBookingModel();
        createBook.setTotalprice(500);
        createBook.setDepositpaid(true);
        createBook.setBookingdates(bookingDatesModel);
        createBook.setAdditionalneeds("Breakfast");

        step("Отправить post-запрос на создание бронирование без имени и фамилии клиента и получение 500 ответа", () ->
                given()
                        .spec(requestSpec)
                        .body(createBook)
                        .when()
                        .post("/booking")
                        .then()
                        .spec(responseSpec500));

    }
}