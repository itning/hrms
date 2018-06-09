package top.itning.hrms.util;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

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

    /**
     * 获取集合中 JAVA Bean 的所有int或double属性的累加并返回新的JAVA Bean
     *
     * @param list  包含T类型的要累加集合
     * @param clazz 返回的实例class
     * @param <T>   JAVA Bean 类型
     * @return 累加完成的Java Bean
     * @throws IllegalAccessException IllegalAccessException
     * @throws InstantiationException InstantiationException
     */
    public static <T> T getAllFieldsSum(List<? extends T> list, Class<? extends T> clazz) throws IllegalAccessException, InstantiationException {
        logger.info("getAllFieldsSum::方法开始");
        T newInstance = clazz.newInstance();
        logger.info("getAllFieldsSum::已构建新实例");
        Class<?> newInstanceClass = newInstance.getClass();
        logger.info("getAllFieldsSum::已获取clazz的字节码,简单类名->" + newInstanceClass.getSimpleName());
        logger.info("getAllFieldsSum::开始迭代集合,集合大小->" + list.size());
        list.forEach(t -> {
            Class<?> tClass = t.getClass();
            logger.info("getAllFieldsSum::已获取字节码");
            Field[] tClassDeclaredFields = tClass.getDeclaredFields();
            Field[] newInstanceClassDeclaredFields = newInstanceClass.getDeclaredFields();
            logger.info("getAllFieldsSum::已获取所有字段");
            logger.info("getAllFieldsSum::开始迭代字段,并累加");
            for (int i = 0; i < tClassDeclaredFields.length; i++) {
                tClassDeclaredFields[i].setAccessible(true);
                newInstanceClassDeclaredFields[i].setAccessible(true);
                try {
                    logger.info("getAllFieldsSum::字段" + tClassDeclaredFields[i].getName() + "的类型:" + tClassDeclaredFields[i].getAnnotatedType().getType().getTypeName() + "字段值:" + tClassDeclaredFields[i].get(t));
                    if (tClassDeclaredFields[i].getAnnotatedType().getType() == int.class) {
                        logger.info("getAllFieldsSum::Int类型字段->" + tClassDeclaredFields[i].getName());
                        newInstanceClassDeclaredFields[i].set(newInstance, newInstanceClassDeclaredFields[i].getInt(newInstance) + tClassDeclaredFields[i].getInt(t));
                    } else if (tClassDeclaredFields[i].getAnnotatedType().getType() == double.class) {
                        logger.info("getAllFieldsSum::Double类型字段->" + tClassDeclaredFields[i].getName());
                        newInstanceClassDeclaredFields[i].set(newInstance, newInstanceClassDeclaredFields[i].getDouble(newInstance) + tClassDeclaredFields[i].getDouble(t));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            logger.info("getAllFieldsSum::结束迭代字段");
        });
        logger.info("getAllFieldsSum::结束迭代集合");
        logger.info("getAllFieldsSum::方法结束");
        return newInstance;
    }

    /**
     * 检查字段是否全是数字
     *
     * @param fields 字段
     * @return 所有都是返回真, 否则返回假
     */
    public static boolean checkAllFieldsIsNumber(String... fields) {
        return Arrays.stream(fields).filter(NumberUtils::isDigits).count() == fields.length;
    }
}
