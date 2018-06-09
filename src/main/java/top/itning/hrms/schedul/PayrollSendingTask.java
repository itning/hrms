package top.itning.hrms.schedul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * 工资每月发送
 *
 * @author wangn
 */
@Component
public class PayrollSendingTask {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String sendFrom;

    /**
     * 发送邮件
     *
     * @param map 模板值
     * @throws MessagingException MessagingException
     */
    public void sendPayrollInfo(Map<String, Object> map, String subject, String sendTo) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        Context c = new Context();
        c.setVariables(map);
        String mailTemplate = templateEngine.process("mailTemplate", c);
        helper.setFrom(sendFrom);
        helper.setTo(sendTo);
        helper.setSubject(subject);
        helper.setText(mailTemplate, true);
        mailSender.send(mimeMessage);
    }

}
