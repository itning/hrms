package top.itning.hrms.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.itning.hrms.dao.StaffDao;
import top.itning.hrms.dao.WageDao;
import top.itning.hrms.dao.department.DepartmentDao;
import top.itning.hrms.dao.department.GrassrootDao;
import top.itning.hrms.dao.job.JobTitleDao;
import top.itning.hrms.entity.Wage;
import top.itning.hrms.entity.search.SearchWage;
import top.itning.hrms.service.WageService;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 职工工资服务实现类
 *
 * @author Ning
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class WageServiceImpl implements WageService {
    private static final Logger logger = LoggerFactory.getLogger(WageServiceImpl.class);

    @Autowired
    private WageDao wageDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private GrassrootDao grassrootDao;

    @Autowired
    private JobTitleDao jobTitleDao;

    @Override
    public String[] getWageYear() {
        return wageDao.findYear();
    }

    @Override
    public List<Wage> searchWage(SearchWage searchWage) {
        logger.debug("searchWage::开始搜索职工");
        logger.info("searchWage::搜索实体信息->" + searchWage);
        return wageDao.findAll((root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            //查询条件:姓名(Name)
            if (StringUtils.isNoneBlank(searchWage.getName())) {
                logger.info("searchWage::查询条件 name(精确查询)->" + searchWage.getName());
                List<Predicate> predicateList = new ArrayList<>();
                staffDao.findByName(searchWage.getName()).forEach(staff -> predicateList.add(cb.equal(root.get("staff"), staff)));
                list.add(cb.or(predicateList.toArray(new Predicate[predicateList.size()])));
            }
            //查询条件:身份证号(nid)
            if (StringUtils.isNoneBlank(searchWage.getNid())) {
                logger.info("searchWage::查询条件 nid(精确查询)->" + searchWage.getNid());
                List<Predicate> predicateList = new ArrayList<>();
                staffDao.findByNid(searchWage.getNid()).forEach(staff -> predicateList.add(cb.equal(root.get("staff"), staff)));
                list.add(cb.or(predicateList.toArray(new Predicate[predicateList.size()])));
            }
            //查询条件:部门ID(department)
            if (searchWage.getDepartment() != null) {
                logger.info("searchWage::查询条件 department(多条件查询)->" + Arrays.toString(searchWage.getDepartment()));
                List<Predicate> predicateList = new ArrayList<>();
                for (int i = 0; i < searchWage.getDepartment().length; i++) {
                    staffDao.findByDepartment(departmentDao.getOne(searchWage.getDepartment()[i])).forEach(staff -> predicateList.add(cb.equal(root.get("staff"), staff)));
                }
                list.add(cb.or(predicateList.toArray(new Predicate[predicateList.size()])));
            }
            //查询条件:基层单位ID(grassroot)
            if (searchWage.getGrassroot() != null) {
                logger.info("searchWage::查询条件 grassroot(多条件查询)->" + Arrays.toString(searchWage.getGrassroot()));
                List<Predicate> predicateList = new ArrayList<>();
                for (int i = 0; i < searchWage.getGrassroot().length; i++) {
                    staffDao.findByGrassroot(grassrootDao.getOne(searchWage.getGrassroot()[i])).forEach(staff -> predicateList.add(cb.equal(root.get("staff"), staff)));
                }
                list.add(cb.or(predicateList.toArray(new Predicate[predicateList.size()])));
            }
            //查询条件:社会职称(jobTitle)
            if (searchWage.getJobTitle() != null) {
                logger.info("searchWage::查询条件 jobTitle(多条件查询)->" + Arrays.toString(searchWage.getJobTitle()));
                List<Predicate> predicateList = new ArrayList<>();
                for (int i = 0; i < searchWage.getJobTitle().length; i++) {
                    staffDao.findByJobTitle(jobTitleDao.getOne(searchWage.getJobTitle()[i])).forEach(staff -> predicateList.add(cb.equal(root.get("staff"), staff)));
                }
                list.add(cb.or(predicateList.toArray(new Predicate[predicateList.size()])));
            }
            //查询条件:年(year)
            if (searchWage.getYear() != null) {
                logger.info("searchWage::查询条件 year(精确查询)->" + Arrays.toString(searchWage.getYear()));
                List<Predicate> predicateList = new ArrayList<>();
                for (int i = 0; i < searchWage.getYear().length; i++) {
                    predicateList.add(cb.equal(root.get("year"), searchWage.getYear()[i]));
                }
                list.add(cb.or(predicateList.toArray(new Predicate[predicateList.size()])));
            }
            //查询条件:月(month)
            if (searchWage.getMonth() != null) {
                logger.info("searchWage::查询条件 month(精确查询)->" + Arrays.toString(searchWage.getMonth()));
                List<Predicate> predicateList = new ArrayList<>();
                for (int i = 0; i < searchWage.getMonth().length; i++) {
                    predicateList.add(cb.equal(root.get("month"), searchWage.getMonth()[i]));
                }
                list.add(cb.or(predicateList.toArray(new Predicate[predicateList.size()])));
            }
            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        });
    }
}
