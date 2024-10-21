package com.app;

import com.app.api.router.*;
import com.app.config.AppConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static spark.Spark.initExceptionHandler;
import static spark.Spark.port;

@Log4j2
public class App {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(AppConfig.class);

        initExceptionHandler(err -> log.error(err.getMessage()));
        port(8080);

        var countryRouter = context.getBean("countryRouter", CountryRouter.class);
        var personRouter = context.getBean("personRouter", PersonRouter.class);
        var reservationRouter
                = context.getBean("reservationRouter", ReservationRouter.class);
        var tourWithCountryRouter
                = context.getBean("tourWithCountryRouter", TourWithCountryRouter.class);
        var travelAgencyRouter = context.getBean("travelAgencyRouter",
                TravelAgencyRouter.class);
        var defaultRouter = context.getBean("exceptionHandlerRouter", ExceptionHandlerRouter.class);
        countryRouter.routes();
        personRouter.routes();
        reservationRouter.routes();
        tourWithCountryRouter.routes();
        travelAgencyRouter.routes();
        defaultRouter.routes();
    }
}
