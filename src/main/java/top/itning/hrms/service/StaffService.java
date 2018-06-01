package top.itning.hrms.service;

import org.slf4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;
import top.itning.hrms.entity.ServerMessage;
import top.itning.hrms.entity.Staff;
import top.itning.hrms.entity.search.SearchStaff;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.defaults.NullParameterException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * 职工信息服务
 *
 * @author Ning
 */
public interface StaffService {
    /**
     * 根据部门ID获取该部门的所有职工信息集合
     *
     * @param id 部门ID
     * @return 职工信息集合
     * @throws NullParameterException ID为空时抛出该异常
     * @throws NoSuchIdException      ID不存在时抛出该异常
     */
    List<Staff> getStaffInfoListByDepartmentID(String id) throws NullParameterException, NoSuchIdException;

    /**
     * 添加或修改职工信息
     *
     * @param staff 职工实体类
     * @return 添加获取修改的职工信息
     * @throws NumberFormatException  身份证ID不正确则抛出该异常
     * @throws NullParameterException 实体信息必填字段有空则抛出该异常
     * @throws DataFormatException    出生日期格式化出现问题则抛出该异常
     */
    Staff addOrModifyStaffInfo(Staff staff) throws NumberFormatException, NullParameterException, DataFormatException;

    /**
     * 根据职工ID查找该职工实体信息
     *
     * @param id 职工ID
     * @return 职工实体信息
     * @throws NoSuchIdException      如果该职工ID不存在则抛出该异常
     * @throws NullParameterException 如果ID为空则抛出该异常
     */
    Staff getStaffInfoByID(String id) throws NoSuchIdException, NullParameterException;

    /**
     * 根据职工实体删除职工信息
     *
     * @param staff 职工
     */
    void delStaffInfoByID(Staff staff);

    /**
     * 职工信息搜索
     *
     * @param searchStaff 职工信息搜索实体
     * @return 搜索到的职工信息集合
     */
    List<Staff> searchStaff(SearchStaff searchStaff);

    /**
     * 根据ID下载职工信息
     *
     * @param servletOutputStream servletOutputStream
     * @param id                  职工ID
     * @throws NoSuchIdException 如果ID没有找到则抛出该异常
     * @throws IOException       IOException
     */
    void downStaffInfoByID(ServletOutputStream servletOutputStream, String... id) throws NoSuchIdException, IOException;


    /**
     * 通过Excel文件添加职工信息
     *
     * @param file 文件
     * @return JSON服务器消息
     * @throws NullParameterException 参数为空则抛出该异常
     * @throws IOException            IOException
     */
    ServerMessage addStaffInfoByFile(MultipartFile file) throws NullParameterException, IOException;

    /**
     * 日期区间查询
     *
     * @param logger    日志工厂
     * @param list      条件集合
     * @param cb        CriteriaBuilder
     * @param root      Root Staff
     * @param field     查询字段
     * @param startDate 开始日期
     * @param endDate   结束日期
     */
    default void dateIntervalQuery(Logger logger, List<Predicate> list, CriteriaBuilder cb, Root<Staff> root, String field, Date startDate, Date endDate) {
        //有开始有结束
        if (startDate != null && endDate != null) {
            logger.info("dateIntervalQuery::已获取到开始和结束时间");
            list.add(cb.between(root.get(field), startDate, endDate));
        } else {
            Date minDate = null;
            Date maxDate = null;
            try {
                minDate = new SimpleDateFormat("yyyy-MM-dd").parse("2001-01-01");
                maxDate = new SimpleDateFormat("yyyy-MM-dd").parse("9999-12-31");
            } catch (ParseException e) {
                //不可能的异常
                logger.error("dateIntervalQuery::日期转换出现问题?" + e.getMessage());
            }
            //只有开始时间
            if (startDate != null) {
                logger.debug("dateIntervalQuery::已获取到开始时间");
                list.add(cb.between(root.get(field), startDate, maxDate));
            } else {//只有结束时间
                logger.debug("dateIntervalQuery::已获取到结束时间");
                list.add(cb.between(root.get(field), minDate, endDate));
            }
        }
    }

    /**
     * 数字区间查询
     *
     * @param logger 日志工厂
     * @param list   条件集合
     * @param cb     CriteriaBuilder
     * @param root   Root Staff
     * @param field  查询字段
     * @param start  开始
     * @param end    结束
     */
    default void intIntervalQuery(Logger logger, List<Predicate> list, CriteriaBuilder cb, Root<Staff> root, String field, Integer start, Integer end) {
        //有开始有结束
        if (start != null && end != null) {
            if (start > end) {
                logger.info("stringIntervalQuery::开始大于结束");
                return;
            }
            logger.info("stringIntervalQuery::已获取到开始和结束->" + start + "\t" + end);
            list.add(cb.between(root.get(field), start, end));
        } else {
            int min = 0;
            int max = 9999999;
            //只有开始
            if (start != null) {
                logger.debug("stringIntervalQuery::已获取到开始->" + start);
                list.add(cb.between(root.get(field), start, max));
                //只有结束
            } else {
                logger.debug("stringIntervalQuery::已获取到结束->" + end);
                list.add(cb.between(root.get(field), min, end));
            }
        }
    }

    /**
     * 多条件查询
     *
     * @param logger   日志工厂
     * @param staffDao 职工DAO
     * @param cb       CriteriaBuilder
     * @param root     Root Staff
     * @param field    查询字段
     * @param key      多查询条件关键字
     * @return Predicate
     */
    default Predicate multipleConditionsQuery(Logger logger, JpaRepository staffDao, CriteriaBuilder cb, Root<Staff> root, String field, String[] key) {
        logger.info("multipleConditionsQuery::多查询条件关键字长度->" + key.length);
        Predicate[] p = new Predicate[key.length];
        for (int i = 0; i < key.length; i++) {
            logger.info("multipleConditionsQuery::添加查询条件->" + key[i]);
            p[i] = cb.equal(root.get(field), staffDao.getOne(key[i]));
        }
        logger.info("multipleConditionsQuery::添加查询条件完成");
        return cb.or(p);
    }
}
