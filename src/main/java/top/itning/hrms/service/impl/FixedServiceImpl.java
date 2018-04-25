package top.itning.hrms.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.itning.hrms.dao.fixed.EthnicDao;
import top.itning.hrms.dao.fixed.PoliticalStatusDao;
import top.itning.hrms.entity.fixed.Ethnic;
import top.itning.hrms.entity.fixed.PoliticalStatus;
import top.itning.hrms.service.FixedService;

import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ning
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class FixedServiceImpl implements FixedService {
    private static final Logger logger = LoggerFactory.getLogger(FixedServiceImpl.class);

    private final EthnicDao ethnicDao;

    private final PoliticalStatusDao politicalStatusDao;

    public FixedServiceImpl(EthnicDao ethnicDao, PoliticalStatusDao politicalStatusDao) {
        this.ethnicDao = ethnicDao;
        this.politicalStatusDao = politicalStatusDao;
        if (ethnicDao.count() == 0) {
            logger.info("FixedServiceImpl::初始化民族信息");
            initEthnicInfo();
        }
        if (politicalStatusDao.count() == 0) {
            logger.info("FixedServiceImpl::初始化政治面貌信息");
            initPoliticalStatusInfo();
        }
    }

    private void initPoliticalStatusInfo() {
        logger.info("init::开始初始化政治面貌信息");
        List<PoliticalStatus> politicalStatusList = new ArrayList<>();
        String[] psStr = {"中共党员", "中共预备党员", "共青团员", "民革党员", "民盟盟员", "民建会员", "民进会员", "农工党党员", "致公党党员", "九三学社社员", "台盟盟员", "无党派人士", "群众"};
        for (int i = 0; i < psStr.length; i++) {
            politicalStatusList.add(new PoliticalStatus(String.valueOf(new DecimalFormat("00").format(i + 1)), psStr[i]));
        }
        logger.info("init::初始化政治面貌信息数量->" + politicalStatusList.size());
        politicalStatusDao.save(politicalStatusList);
        politicalStatusDao.flush();
        logger.info("init::添加政治面貌信息完成");
    }

    private void initEthnicInfo() {
        logger.info("init::开始初始化民族信息");
        List<Ethnic> ethnicList = new ArrayList<>();
        String[] ethnicStr = {"汉族", "蒙古族", "回族", "藏族",
                "维吾尔族", "苗族", "彝族", "壮族", "布依族", "朝鲜族", "满族", "侗族", "瑶族", "白族",
                "土家族", "哈尼族", "哈萨克族", "傣族", "黎族", "傈僳族", "佤族", "畲族", "高山族", "拉祜族", "水族", "东乡族", "纳西族",
                "景颇族", "柯尔克孜族", "土族", "达斡尔族", "仫佬族", "羌族", "布朗族", "撒拉族", "毛难族", "仡佬族", "锡伯族", "阿昌族",
                "普米族", "塔吉克族", "怒族", "乌孜别克族", "俄罗斯族", "鄂温克族", "崩龙族", "保安族", "裕固族", "京族", "塔塔尔族",
                "独龙族", "鄂伦春族", "赫哲族", "门巴族", "珞巴族", "基诺族", "其他", "外国血统"};
        for (int i = 0; i < ethnicStr.length; i++) {
            if (i < 56) {
                ethnicList.add(new Ethnic(String.valueOf(new DecimalFormat("00").format(i + 1)), ethnicStr[i]));
            } else {
                ethnicList.add(new Ethnic(String.valueOf(i + 41), ethnicStr[i]));
            }
        }
        logger.info("init::初始化民族信息数量->" + ethnicList.size());
        ethnicDao.save(ethnicList);
        ethnicDao.flush();
        logger.info("init::添加民族信息完成");
    }

    @Override
    @Cacheable(cacheNames = "EthnicInfoList", key = "#key")
    public List<Ethnic> getAllEthnicInfoList(String key) {
        logger.debug("getAllEthnicInfoList::开始获取所有民族信息集合");
        return ethnicDao.findAll();
    }

    @Override
    @Cacheable(cacheNames = "PoliticalStatusInfoList", key = "#key")
    public List<PoliticalStatus> getAllPoliticalStatusInfoList(String key) {
        logger.debug("getAllPoliticalStatusInfoList::开始获取所有政治面貌信息");
        return politicalStatusDao.findAll();
    }
}
