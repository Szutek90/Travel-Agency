package com.app;

import com.app.config.AppConfig;
import com.app.repository.impl.TravelAgencyRepositoryImpl;
import com.app.service.impl.TravelAgencyServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(AppConfig.class);
        var agencyService = new TravelAgencyServiceImpl(context
                .getBean("travelAgencyRepositoryImpl", TravelAgencyRepositoryImpl.class));
        System.out.println(agencyService.getTravelAgencyById(1));
    }
}
