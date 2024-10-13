package com.app;

import com.app.persistence.model.agency.TravelAgenciesData;
import com.app.persistence.model.country.CountriesData;
import com.app.persistence.model.tour.ToursData;
import com.app.persistence.xml.deserializer.impl.CountriesDataXmlDeserializer;
import com.app.persistence.xml.deserializer.impl.ToursDataXmlDeserializer;
import com.app.persistence.xml.deserializer.impl.TravelAgenciesDataXmlDeserializerImpl;
import lombok.extern.log4j.Log4j2;

import java.nio.file.Paths;

import static spark.Spark.port;

@Log4j2
public class App {
    public static void main(String[] args) {
//        var context = new AnnotationConfigApplicationContext(AppConfig.class);
//
//        initExceptionHandler(err -> log.error(err.getMessage()));
//        port(8080);
//
//        var countryRouter = context.getBean("countryRouter", CountryRouter.class);
//        var personRouter = context.getBean("personRouter", PersonRouter.class);
//        var reservationRouter
//                = context.getBean("reservationRouter", ReservationRouter.class);
//        var tourWithCountryRouter
//                = context.getBean("tourWithCountryRouter", TourWithCountryRouter.class);
//        var travelAgencyRouter = context.getBean("travelAgencyRouter",
//                TravelAgencyRouter.class);
//        var defaultRouter = context.getBean("exceptionHandlerRouter", ExceptionHandlerRouter.class);
//        countryRouter.routes();
//        personRouter.routes();
//        reservationRouter.routes();
//        tourWithCountryRouter.routes();
//        travelAgencyRouter.routes();
//        defaultRouter.routes();

        var countriesXmlDeserializer = new CountriesDataXmlDeserializer();
        var agenciesXmlDeserializer = new TravelAgenciesDataXmlDeserializerImpl();
        var toursXmlDeserializer = new ToursDataXmlDeserializer();

        var countries = new CountriesData(countriesXmlDeserializer
                .deserializeFromFile(Paths.get("countries.xml")).getCountries());
        var agencies = new TravelAgenciesData(agenciesXmlDeserializer
                .deserializeFromFile(Paths.get("agencies.xml")).getTravelAgencies());
        var tours = new ToursData(toursXmlDeserializer.deserializeFromFile(Paths.get("tours.xml"))
                .getTours());

        countries.getCountries().forEach(System.out::println);
        agencies.getTravelAgencies().forEach(System.out::println);
        tours.getTours().forEach(System.out::println);
    }
}
