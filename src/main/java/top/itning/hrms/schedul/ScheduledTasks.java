package top.itning.hrms.schedul;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 *
 * @author wangn
 */
@Component
public class ScheduledTasks {
    /**
     * 查找合同到期的职工
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void findingTheContractExpires() {
        //TODO 合同到期员工筛选
    }
}
