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
     * 每天12点执行,查找合同到期的职工
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void findingTheContractExpires() {
        contractExpires.startSend();
    }

    /**
     * 自动发送工资条,每月1日12时执行
     */
    @Scheduled(cron = "0 0 12 1 * ?")
    public void autoSendWageEmail() {
        payrollSendingTask.startSend();
    }
}
