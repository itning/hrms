package top.itning.hrms.schedul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import top.itning.hrms.dao.StaffDao;
import top.itning.hrms.entity.Staff;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 合同到期员工计算
 *
 * @author wangn
 */
@Component
public class ContractExpires {
    /**
     * 合同到期提前几天提醒
     */
    private static final int DAY = 30;

    private final StaffDao staffDao;

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String sendFrom;

    @Autowired
    public ContractExpires(StaffDao staffDao, @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.staffDao = staffDao;
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    /**
     * 开始
     */
    public void startSend() {
        Date date = new Date();
        //一共有3次签约
        //从后往前查找
        List<Staff> staffList = staffDao.findAll();
        //查找第三次签约到期时间不足30天的员工
        List<Staff> laborContract3EndList = staffList.stream().filter(staff -> staff.getLaborContract3End() != null).filter(staff -> comparisonDateDifferenceDays(date, staff.getLaborContract3End()) <= DAY).collect(Collectors.toList());
        //查找没有第三次签约但签约第二次且到期时间不足30天的员工
        List<Staff> laborContract2EndList = staffList.stream().filter(staff -> staff.getLaborContract3End() == null && staff.getLaborContract2End() != null).filter(staff -> comparisonDateDifferenceDays(date, staff.getLaborContract2End()) <= DAY).collect(Collectors.toList());
        //查找第二三次均没有签约且第一次签约到期时间不足30天的员工
        List<Staff> laborContract1EndList = staffList.stream().filter(staff -> staff.getLaborContract2End() == null && staff.getLaborContract3End() == null && staff.getLaborContract1End() != null).filter(staff -> comparisonDateDifferenceDays(date, staff.getLaborContract1End()) <= DAY).collect(Collectors.toList());
        List<Staff> list = new LinkedList<>();
        list.addAll(laborContract1EndList);
        list.addAll(laborContract2EndList);
        list.addAll(laborContract3EndList);
        sendEmail(list);
    }

    private void sendEmail(List<Staff> list) {
        try {
            String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            Context c = new Context();
            c.setVariable("staffList", list);
            String mailTemplate = templateEngine.process("contractExpiresTemplate", c);
            helper.setFrom(sendFrom);
            helper.setTo(sendFrom);
            helper.setSubject("合同签约到期提醒名单-" + time);
            helper.setText(mailTemplate, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 比较日期相差天数
     *
     * @param first  第一个日期
     * @param second 第二个日期
     * @return 相差天数
     */
    private int comparisonDateDifferenceDays(Date first, Date second) {
        return (int) ((second.getTime() - first.getTime()) / (1000 * 3600 * 24));
    }

}
