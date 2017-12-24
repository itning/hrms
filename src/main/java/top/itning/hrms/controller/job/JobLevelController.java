package top.itning.hrms.controller.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.itning.hrms.service.JobService;

/**
 * 职称级别控制器
 *
 * @author Ning
 */
@RestController
@RequestMapping("/jobLevel")
public class JobLevelController {
    private static final Logger logger = LoggerFactory.getLogger(JobLevelController.class);

    @Autowired
    private JobService jobService;

}
