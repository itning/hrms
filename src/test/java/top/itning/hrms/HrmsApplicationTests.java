package top.itning.hrms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.itning.hrms.schedul.PayrollSendingTask;

import javax.mail.MessagingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.DataFormatException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HrmsApplicationTests {
    @Autowired
    private PayrollSendingTask payrollSendingTask;

    @Test
    public void contextLoads() throws MessagingException, DataFormatException {
        //WageEmail wageEmail = new WageEmail();
        //payrollSendingTask.sendPayrollInfo(wageEmail.getAllFiledMap(),"2018年06月工资信息","3579677768@qq.com");
       payrollSendingTask.startSend();
    }
    @Test
    public void  test(){


    }

}
