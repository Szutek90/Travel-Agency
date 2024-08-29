package com.app;

import com.app.api.router.PersonRouter;
import com.app.api.router.TourRouter;
import com.app.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static spark.Spark.initExceptionHandler;
import static spark.Spark.port;

public class App {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(AppConfig.class);


        initExceptionHandler(err -> System.out.println(err.getMessage()));
        port(8080);

        var tourRouter = context.getBean("tourRouter", TourRouter.class);
        var personRouter = context.getBean("personRouter", PersonRouter.class);
        tourRouter.routes();
        personRouter.routes();
    }
}
