package top.itning.hrms.schedul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 *
 * @author wangn
 */
@Component
public class ScheduledTasks {

    private final PayrollSendingTask payrollSendingTask;

    private final ContractExpires contractExpires;

    @Autowired
    public ScheduledTasks(PayrollSendingTask payrollSendingTask, ContractExpires contractExpires) {
        this.payrollSendingTask = payrollSendingTask;
        this.contractExpires = contractExpires;
    }

    /**
     * 查找合同到期的职工
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void findingTheContractExpires() {
        //TODO 合同到期员工筛选
        contractExpires.startSend();
    }

    @Scheduled(cron = "0 0 12 1 * ?")
    public void autoSendWageEmail() {
        //TODO 自动发送工资信息
        payrollSendingTask.startSend();
    }
}
