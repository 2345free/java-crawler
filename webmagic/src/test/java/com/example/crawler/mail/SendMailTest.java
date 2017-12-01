package com.example.crawler.mail;

import com.example.crawler.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

public class SendMailTest extends BaseTest {

    @Autowired
    private JavaMailSender qqMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Test
    public void testSendmail() throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = qqMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//        helper.setFrom(((JavaMailSenderImpl) mailSender).getUsername());
        String nick = javax.mail.internet.MimeUtility.encodeText("测试邮件");
        helper.setFrom(new InternetAddress(String.format("%s <%s>", nick, ((JavaMailSenderImpl) qqMailSender).getUsername())));
        helper.addTo("tianyi@gshopper.com");
        helper.addTo("272175223@qq.com");
        helper.setSubject("您的订单信息");

        Context context = new Context();
        context.setVariable("tid", "t123456");
        context.setVariable("logo", "spring");
        String orderHtml = templateEngine.process("mail/order", context);
        helper.setText(orderHtml, true);

        ClassPathResource resource = new ClassPathResource("static/images/spring.png");
        String cid = "cid_order_img";
        helper.addInline(cid, resource);

        qqMailSender.send(message);
    }

}
