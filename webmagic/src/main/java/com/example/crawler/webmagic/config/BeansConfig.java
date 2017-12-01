package com.example.crawler.webmagic.config;

import com.example.crawler.webmagic.jmx.thread.ThreadPoolExecutorMgd;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.security.GeneralSecurityException;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Configuration
public class BeansConfig {

    @Bean
    public ThreadPoolExecutorMgd trackingThreadPool() {
        LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        System.err.format("剩余容量: %s \n", workQueue.remainingCapacity());
        return new ThreadPoolExecutorMgd(5, 10, 5, TimeUnit.MINUTES, workQueue);
    }

    @Bean
    public JavaMailSender qqMailSender() throws GeneralSecurityException {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.qq.com");
        mailSender.setProtocol("smtp");
        mailSender.setPort(465);
        mailSender.setUsername("3060928836@qq.com");
        // 获取16位授权码 http://service.mail.qq.com/cgi-bin/help?subtype=1&&id=28&&no=1001256
        mailSender.setPassword("16位授权码");
        mailSender.setDefaultEncoding("UTF-8");

        //使用SSL
        MailSSLSocketFactory sslSocketFactory = new MailSSLSocketFactory();
        sslSocketFactory.setTrustAllHosts(true);
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.ssl.enable", true);
        props.put("mail.smtp.ssl.socketFactory", sslSocketFactory);
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                PasswordAuthentication authentication = new PasswordAuthentication(mailSender.getUsername(), mailSender.getPassword());
                return authentication;
            }
        });
        //设置session的调试模式，发布时取消
        session.setDebug(true);
        mailSender.setSession(session);

        return mailSender;
    }

    @Bean
    public JavaMailSender mailSender() throws GeneralSecurityException {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.exmail.qq.com");
        mailSender.setProtocol("smtp");
        mailSender.setPort(465);
        mailSender.setUsername("tianyi@gshopper.com");
        mailSender.setPassword("secret");
        mailSender.setDefaultEncoding("UTF-8");

        //使用SSL,企业邮箱必需
        MailSSLSocketFactory sslSocketFactory = new MailSSLSocketFactory();
        sslSocketFactory.setTrustAllHosts(true);
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.ssl.enable", true);
        props.put("mail.smtp.ssl.socketFactory", sslSocketFactory);
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                PasswordAuthentication authentication = new PasswordAuthentication(mailSender.getUsername(), mailSender.getPassword());
                return authentication;
            }
        });
        //设置session的调试模式，发布时取消
        session.setDebug(true);
        mailSender.setSession(session);

        return mailSender;
    }

    /* **************************************************************** */
    /*  THYMELEAF-SPRING                                                */
    /*  TemplateResolver <- TemplateEngine <- ViewResolver              */
    /* **************************************************************** */
    @Bean
    public ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/html/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Bean
    public ClassLoaderTemplateResolver xmlTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/xml/");
        templateResolver.setSuffix(".xml");
        templateResolver.setTemplateMode(TemplateMode.XML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Bean
    public SpringTemplateEngine xmlTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(xmlTemplateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setOrder(1);
        viewResolver.setViewNames(new String[]{".html", ".xhtml"});
        return viewResolver;
    }

}
