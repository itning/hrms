package top.itning.hrms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * 对象工具类
 *
 * @author Ning
 */
public class ObjectUtils {
    private static final Logger logger = LoggerFactory.getLogger(ObjectUtils.class);

    private ObjectUtils() {
    }

    /**
     * 检查对象中属性是否为空
     *
     * @param obj    要检查的对象
     * @param allStr 要检查的属性全为String时为true
     * @return 如果有字段为空则返回true, 所有属性都不为空返回false
     */
    public static boolean checkObjFieldIsNull(Object obj, boolean allStr) {
        for (Field f : obj.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            logger.debug("checkObjFieldIsNull::检查" + obj.getClass().getSimpleName() + "对象的" + f.getName() + "属性是否为NULL");
            try {
                if (f.get(obj) == null) {
                    return true;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (allStr) {
                logger.debug("checkObjFieldIsNull::检查" + obj.getClass().getSimpleName() + "对象的" + f.getName() + "属性是否为空字符串");
                try {
                    if (" ".equals(f.get(obj).toString()) || "".equals(f.get(obj).toString())) {
                        return true;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
