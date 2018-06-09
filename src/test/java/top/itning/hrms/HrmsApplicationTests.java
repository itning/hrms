package top.itning.hrms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.itning.hrms.schedul.PayrollSendingTask;
import top.itning.hrms.util.StaffUtils;

import javax.mail.MessagingException;
import java.util.Map;
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
        Map<String, Object> map = StaffUtils.parserId("23230119980508541x");
        map.forEach((a, b) -> {
            System.out.println(a + "->" + b);
        });
    }

}
