package top.itning.hrms.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.itning.hrms.exception.defaults.IllegalParametersException;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * 与职工有关的工具类
 *
 * @author Ning
 */
public class StaffUtils {
    private static final Logger logger = LoggerFactory.getLogger(StaffUtils.class);

    private StaffUtils() {
    }

    /**
     * 获取校龄/工龄
     * <p>当前年-来校年+(（来校月/当前月）/12)（保留一位小数）
     *
     * @param comeDate 来校日期/工龄起始日期
     * @return 校龄/工龄
     * @throws IllegalParametersException 非法参数异常
     */
    public static String getFormatTime(Date comeDate) throws IllegalParametersException {
        Date nowData = new Date();
        if (nowData.before(comeDate)) {
            throw new IllegalParametersException("来校日期不能在当前日期之后,请检查");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(comeDate);
        int comeYear = calendar.get(Calendar.YEAR);
        double comeMonth = calendar.get(Calendar.MONTH) + 1;
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(nowData);
        int nowYear = calendar1.get(Calendar.YEAR);
        double nowMonth = calendar1.get(Calendar.MONTH) + 1;
        return new DecimalFormat("0.0").format(nowYear - comeYear + ((comeMonth / nowMonth) / 12));
    }

    /**
     * 获取应发额数
     *
     * @param moneys 钱数
     * @return 应发额
     * @throws IllegalParametersException 参数不正确的时候抛出该异常
     */
    public static String sbm(String... moneys) throws IllegalParametersException {
        if (Arrays.stream(moneys).filter(StringUtils::isNumeric).count() != moneys.length) {
            logger.warn("sbm::参数并非全部为数字->" + Arrays.toString(moneys));
            throw new IllegalParametersException("参数并非全部为数字");
        }
        int totalMoney = 0;
        for (String money : moneys) {
            totalMoney += Integer.parseInt(money);
        }
        return String.valueOf(totalMoney);
    }
}
