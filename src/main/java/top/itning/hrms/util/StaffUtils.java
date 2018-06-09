package top.itning.hrms.util;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.itning.hrms.entity.Staff;
import top.itning.hrms.exception.defaults.IllegalParametersException;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.DataFormatException;

/**
 * 与职工有关的工具类
 *
 * @author Ning
 */
public class StaffUtils {
    private static final Logger logger = LoggerFactory.getLogger(StaffUtils.class);

    /**
     * 身份证号位数
     */
    private static final int ID_NUM_LENGTH = 18;

    /**
     * 身份证号结尾字符X
     */
    private static final String ID_END_CHAR = "X";

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
        if (!ObjectUtils.allNotNull((Object) moneys)) {
            return "0";
        }
        moneys = Arrays.stream(moneys).filter(s -> s != null).toArray(String[]::new);
        System.out.println(Arrays.toString(moneys));
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

    /**
     * 解析身份证号
     *
     * @param nid 身份证号
     * @return 年龄, 性别, 出生日期集合
     */
    public static Map<String, Object> parserId(String nid) throws DataFormatException {
        if (!StringUtils.isNumeric(nid.substring(0, nid.length() - 1))) {
            logger.warn("addOrModifyStaffInfo::nid不是纯数字->" + nid);
            throw new NumberFormatException("身份证号码不是纯数字,请检查!");
        }
        if (!NumberUtils.isDigits(nid) && !StringUtils.endsWith(StringUtils.upperCase(nid), ID_END_CHAR)) {
            logger.warn("addOrModifyStaffInfo::nid结尾非大写X->" + nid);
            throw new NumberFormatException("身份证号码nid结尾非大写X,请检查!");
        }
        //判断身份证号长度
        if (nid.length() != ID_NUM_LENGTH) {
            logger.warn("addOrModifyStaffInfo::nid位数为" + nid.length() + "与" + ID_NUM_LENGTH + "不匹配");
            throw new NumberFormatException("您输入的身份证号码位数为" + nid.length() + "位,不是" + ID_NUM_LENGTH + "位,请检查");
        }
        Map<String, Object> map = new HashMap<>(3);
        try {
            //根据身份证号设置出生日期
            map.put("birthday", new SimpleDateFormat("yyyyMMdd").parse(nid.substring(6, 14)));
        } catch (ParseException e) {
            logger.warn("addOrModifyStaffInfo::出生日期格式化出错,日期->" + nid.substring(6, 14) + "异常信息->" + e.getMessage());
            throw new DataFormatException("出生日期格式化出错,检查日期是否有误");
        }
        //根据身份证号设置性别
        map.put("sex", Integer.parseInt(nid.substring(16, 17)) % 2 != 0);
        //根据身份证号设置年龄
        map.put("age", String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(nid.substring(6, 10))));
        return map;
    }

    /**
     * 简单设置性别年龄生日
     *
     * @param staff 职工实体
     * @throws DataFormatException 日期格式化异常
     */
    public static void simpleSetStaffFileds(Staff staff)  {
        Map<String, Object> parserId = null;
        try {
            parserId = parserId(staff.getNid());
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
        staff.setBirthday((Date) parserId.get("birthday"));
        //根据身份证号设置性别
        staff.setSex((Boolean) parserId.get("sex"));
        //根据身份证号设置年龄
        staff.setAge((String) parserId.get("age"));
    }
}
