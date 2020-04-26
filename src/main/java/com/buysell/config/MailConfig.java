package com.buysell.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration public class MailConfig
{

    @Bean
    public static JavaMailSender mailSender(@Value("${spring.mail.adminid}") String id, @Value("${spring.mail.adminpassword}") String password){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(465);
        mailSender.setUsername(id);
        mailSender.setPassword(password);
        mailSender.setDefaultEncoding("utf-8");
        Properties prop = new Properties();


        prop.put("mail.transport.protocol", "smtp");
        prop.put("mail.smtp.debug", "true");
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.ssl.enable", true);
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.put("mail.smtp.starttls.auth",true);
        prop.put("mail.smtp.starttls.enable",true);
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mailSender.setJavaMailProperties(prop);

        return mailSender; } }

