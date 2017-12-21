package top.itning.hrms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.itning.hrms.util.StaffUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HrmsApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println(StaffUtils.sbm("10", "11"));
    }

}
