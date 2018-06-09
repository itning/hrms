package top.itning.hrms.schedul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import top.itning.hrms.entity.Wage;
import top.itning.hrms.entity.WageEmail;
import top.itning.hrms.entity.search.SearchWage;
import top.itning.hrms.exception.json.JsonException;
import top.itning.hrms.service.WageService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 工资每月发送
 *
 * @author wangn
 */
@Component
public class PayrollSendingTask {
    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    private final WageService wageService;

    @Value("${spring.mail.username}")
    private String sendFrom;

    @Autowired
    public PayrollSendingTask(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") JavaMailSender mailSender, TemplateEngine templateEngine, WageService wageService) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.wageService = wageService;
    }

    /**
     * 开始
     */
    void startSend() {
        //假设本月工资已经导入
        //查找当前年,月,的工资信息
        //获取Wage实体集合
        //拼装WageEmail实体集合
        String year = new SimpleDateFormat("yyyy").format(new Date());
        String month = new SimpleDateFormat("yyyy-M").format(new Date());
        SearchWage searchWage = new SearchWage();
        searchWage.setMonth(new String[]{month});
        searchWage.setYear(new String[]{year});
        try {
            Map<String, Object> map = wageService.searchWage(searchWage);
            @SuppressWarnings("unchecked")
            List<Wage> wageList = (List<Wage>) map.get("wageList");
            if (wageList != null) {
                List<WageEmail> emailList = wageList.stream().map(wage -> {
                    WageEmail wageEmail = new WageEmail();
                    wageEmail.setFields(wage);
                    return wageEmail;
                }).collect(Collectors.toList());
                emailList.forEach(wageEmail -> {
                    try {
                        sendPayrollInfo(wageEmail.getAllFiledMap(), month + "工资信息", wageEmail.getEmail());
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (JsonException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送邮件
     *
     * @param map 模板值
     * @throws MessagingException MessagingException
     */
    private void sendPayrollInfo(Map<String, Object> map, String subject, String sendTo) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        Context c = new Context();
        c.setVariables(map);
        String mailTemplate = templateEngine.process("payrollMailTemplate", c);
        helper.setFrom(sendFrom);
        helper.setTo(sendTo);
        helper.setSubject(subject);
        helper.setText(mailTemplate, true);
        mailSender.send(mimeMessage);
    }

}
