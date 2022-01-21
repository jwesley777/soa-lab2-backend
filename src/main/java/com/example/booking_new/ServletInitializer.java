package com.example.booking_new;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2CollectionHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;

import java.util.Arrays;
import java.util.List;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BookingNewApplication.class);
    }

//    @Bean
//    public HttpMessageConverters converters() {
//        return new HttpMessageConverters(true, Arrays.asList(
//                new MappingJackson2HttpMessageConverter(),
//                new Jaxb2RootElementHttpMessageConverter(),
//                new Jaxb2CollectionHttpMessageConverter<List>())
//        );
//    }

}
