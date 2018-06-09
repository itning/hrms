package top.itning.hrms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.itning.hrms.schedul.ContractExpires;
import top.itning.hrms.schedul.PayrollSendingTask;

import javax.mail.MessagingException;
import java.text.ParseException;
import java.util.zip.DataFormatException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HrmsApplicationTests {
    @Autowired
    private PayrollSendingTask payrollSendingTask;

    @Autowired
    private ContractExpires contractExpires;

    @Test
    public void contextLoads() throws MessagingException, DataFormatException, ParseException {
        //WageEmail wageEmail = new WageEmail();
        //payrollSendingTask.sendPayrollInfo(wageEmail.getAllFiledMap(),"2018年06月工资信息","3579677768@qq.com");
        contractExpires.startSend();
    }

}
