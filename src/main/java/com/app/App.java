package com.app;

import com.app.api.router.PersonRouter;
import com.app.api.router.CountryRouter;
import com.app.api.router.ReservationRouter;
import com.app.api.router.TourWithCountryRouter;
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
        countryRouter.routes();
        personRouter.routes();
        reservationRouter.routes();
        tourWithCountryRouter.routes();
    }
}
