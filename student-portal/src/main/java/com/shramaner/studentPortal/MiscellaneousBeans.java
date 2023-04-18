package com.shramaner.studentPortal;

import com.shramaner.studentPortal.doa.ICourseDAO;
import com.shramaner.studentPortal.model.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;


import java.util.Locale;

@Configuration
class MiscellaneousBeans {

    private static final Logger log = LoggerFactory.getLogger(MiscellaneousBeans.class);

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.UK);
        return sessionLocaleResolver;
    }

 //   @Bean
 //   CommandLineRunner initDatabase(ICourseDAO iCourseDAO) {
//        return args -> {
//
//           iCourseDAO.save(new Course(12l, "C12", "Project Management", 170));
//            iCourseDAO.save(new Course(13l, "C13", "History", 155));
//            iCourseDAO.save(new Course(14l, "C14", "Hospitality Management", 142));
//            iCourseDAO.save(new Course(15l, "C15", "Computer Science", 125));
//            iCourseDAO.save(new Course(16l, "C16", "Finance Management", 110));
//            iCourseDAO.save(new Course(17l, "C17", "Data Science", 152));
//            iCourseDAO.save(new Course(18l, "C18", "Cyber Security", 200));
//            iCourseDAO.save(new Course(19l, "C19", "Logic Programming", 150));
//            iCourseDAO.save(new Course(20l, "C20", "Software and Systems", 100));
//            iCourseDAO.save(new Course(21l, "C21", "Managing Information in Digital Environment", 12));
//            iCourseDAO.save(new Course(22l, "C22", "Research Method", 120));
//
//
//
//        };
//    }
}