package top.itning.hrms.service.impl;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import top.itning.hrms.dao.StaffDao;
import top.itning.hrms.dao.WageDao;
import top.itning.hrms.dao.department.DepartmentDao;
import top.itning.hrms.dao.department.GrassrootDao;
import top.itning.hrms.dao.job.JobTitleDao;
import top.itning.hrms.entity.ServerMessage;
import top.itning.hrms.entity.Staff;
import top.itning.hrms.entity.Wage;
import top.itning.hrms.entity.search.SearchWage;
import top.itning.hrms.exception.defaults.IllegalParametersException;
import top.itning.hrms.exception.defaults.NoSuchIdException;
import top.itning.hrms.exception.json.JsonException;
import top.itning.hrms.service.WageService;
import top.itning.hrms.util.ObjectUtils;
import top.itning.hrms.util.StaffUtils;

import javax.persistence.criteria.Predicate;
import javax.servlet.ServletOutputStream;
import javax.transaction.Transactional;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 职工工资服务实现类
 *
 * @author Ning
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class WageServiceImpl implements WageService {
    private static final Logger logger = LoggerFactory.getLogger(WageServiceImpl.class);

    private final WageDao wageDao;

    private final DepartmentDao departmentDao;

    private final StaffDao staffDao;

    private final GrassrootDao grassrootDao;

    private final JobTitleDao jobTitleDao;

    @Autowired
    public WageServiceImpl(WageDao wageDao, DepartmentDao departmentDao, StaffDao staffDao, GrassrootDao grassrootDao, JobTitleDao jobTitleDao) {
        this.wageDao = wageDao;
        this.departmentDao = departmentDao;
        this.staffDao = staffDao;
        this.grassrootDao = grassrootDao;
        this.jobTitleDao = jobTitleDao;
    }

    @Override
    public String[] getWageYear() {
        return wageDao.findYear();
    }

    @Override
    public Map<String, Object> searchWage(SearchWage searchWage) throws JsonException {
        logger.debug("searchWage::开始搜索职工");
        logger.info("searchWage::搜索实体信息->" + searchWage);
        //如果选了多个年并且选了年下的月
        if (searchWage.getMonth() != null && searchWage.getYear().length != 1) {
            logger.info("searchWage::开始添加选中年但未选中月");
            a:
            for (String year : searchWage.getYear()) {
                for (String month : searchWage.getMonth()) {
                    if (year.equals(month.substring(0, 4))) {
                        continue a;
                    }
                }
                String[] allMonth = new String[]{year + "-1", year + "-2", year + "-3", year + "-2", year + "-4", year + "-5", year + "-6", year + "-7", year + "-8", year + "-9", year + "-10", year + "-11", year + "-12"};
                searchWage.setMonth(ArrayUtils.addAll(searchWage.getMonth(), allMonth));
            }
            logger.info("searchWage::完成添加添加选中年但未选中月");
        }
        Map<String, Object> stringObjectHashMap = new HashMap<>(2);
        List<Wage> wageList = wageDao.findAll((root, query, cb) -> {
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
        }, new Sort(Sort.Direction.DESC, "month"));
        stringObjectHashMap.put("wageList", wageList);
        try {
            Wage allFieldsSum = ObjectUtils.getAllFieldsSum(wageList, Wage.class);
            Staff staff = new Staff();
            staff.setWage(wageList.stream().mapToInt(w -> w.getStaff().getWage()).sum());
            staff.setPerformancePay(wageList.stream().mapToInt(w -> w.getStaff().getPerformancePay()).sum());
            staff.setDutyAllowance(wageList.stream().mapToInt(w -> {
                if (w.getStaff().getDutyAllowance() == null) {
                    return 0;
                } else {
                    return w.getStaff().getDutyAllowance();
                }
            }).sum());
            staff.setGrants(wageList.stream().mapToInt(w -> {
                if (w.getStaff().getGrants() == null) {
                    return 0;
                } else {
                    return w.getStaff().getGrants();
                }
            }).sum());
            allFieldsSum.setStaff(staff);
            stringObjectHashMap.put("sumWage", allFieldsSum);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new JsonException("求和出现异常,请联系管理员->" + e.getMessage(), ServerMessage.SERVICE_ERROR);
        }
        return stringObjectHashMap;
    }

    @Override
    public void downWageInfoByID(ServletOutputStream servletOutputStream, String... id) throws NoSuchIdException, IllegalAccessException, IOException, InstantiationException {
        ArrayList<Wage> wageLists = new ArrayList<>();
        for (String s : id) {
            if (!wageDao.exists(s)) {
                logger.warn("downWageInfoByID::工资ID:" + s + "没有找到");
                throw new NoSuchIdException("工资ID:" + s + "没有找到");
            } else {
                wageLists.add(wageDao.findOne(s));
            }
        }
        Wage totalWage = ObjectUtils.getAllFieldsSum(wageLists, Wage.class);
        Workbook workbook = new XSSFWorkbook();
        //创建工作簿
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        row.setHeightInPoints(40.5f);
        logger.debug("downWageInfoByID::准备标题数据");
        List<String> titleList = new ArrayList<>();
        titleList.add("年月");
        titleList.add("姓名");
        titleList.add("身份证号");
        titleList.add("考勤归属部门");
        titleList.add("部门");
        titleList.add("专业/基层单位");
        titleList.add("岗位名称");
        titleList.add("岗位类别");
        titleList.add("来校日期");
        titleList.add("校龄");
        titleList.add("岗位工资\n+绩效工资");
        titleList.add("岗位\n工资");
        titleList.add("绩效\n工资");
        titleList.add("职务\n津贴");
        titleList.add("岗位超时补助");
        titleList.add("小计1");
        titleList.add("补款");
        titleList.add("扣款");
        titleList.add("小计2");
        titleList.add("教学\n奖金");
        titleList.add("硕士\n津贴");
        titleList.add("电话\n补助");
        titleList.add("带学生津贴");
        titleList.add("奖金");
        titleList.add("校拨津贴");
        titleList.add("教师节津贴");
        titleList.add("加班补助");
        titleList.add("值班补助");
        titleList.add("其他补助");
        titleList.add("补助小计");
        titleList.add("工资\n合计");
        titleList.add("代扣养老保险");
        titleList.add("代扣医疗保险");
        titleList.add("代扣失业保险");
        titleList.add("代扣住房公积金");
        titleList.add("应发额");
        titleList.add("代扣款（扣除教师节津贴）");
        titleList.add("个人所得税");
        titleList.add("税后实发");
        titleList.add("单位承担养老保险");
        titleList.add("单位承担医疗保险");
        titleList.add("单位承担工伤保险");
        titleList.add("单位承担生育保险");
        titleList.add("单位承担失业保险");
        titleList.add("单位承担住房公积金");
        titleList.add("有/无");
        titleList.add("账号");
        titleList.add("性别");
        titleList.add("邮箱");
        logger.debug("downWageInfoByID::标题数据集合大小->" + titleList.size());
        final int[] nowCell = {0};
        logger.debug("downWageInfoByID::开始写入标题数据");
        titleList.forEach(s -> {
            Cell cell = row.createCell(nowCell[0]++);
            logger.debug("downWageInfoByID::已创建Cell->" + (nowCell[0] - 1));
            cell.setCellValue(s);
            logger.debug("downWageInfoByID::写入标题数据->" + s);
        });
        nowCell[0] = 1;
        for (String s : id) {
            Wage wage = wageDao.getOne(s);
            Row dataRow = sheet.createRow(nowCell[0]++);
            logger.debug("downWageInfoByID::已创建Cell->" + (nowCell[0] - 1));
            logger.debug("downWageInfoByID::开始写入");
            Cell monthCell = dataRow.createCell(0);
            monthCell.setCellValue(wage.getMonth());

            Cell nameCell = dataRow.createCell(1);
            nameCell.setCellValue(wage.getStaff().getName());

            Cell nidCell = dataRow.createCell(2);
            //列宽
            sheet.setColumnWidth(2, wage.getStaff().getNid().length() * 280);
            nidCell.setCellValue(wage.getStaff().getNid());

            Cell departmentCell = dataRow.createCell(3);
            departmentCell.setCellValue(wage.getStaff().getDepartment().getName());

            Cell departmentCell2 = dataRow.createCell(4);
            departmentCell2.setCellValue(wage.getStaff().getDepartment().getName());

            Cell grassrootCell = dataRow.createCell(5);
            grassrootCell.setCellValue(wage.getStaff().getGrassroot().getName());

            Cell positionTitleCell = dataRow.createCell(6);
            positionTitleCell.setCellValue(wage.getStaff().getPositionTitle().getName());

            Cell positionCategoryCell = dataRow.createCell(7);
            positionCategoryCell.setCellValue(wage.getStaff().getPositionCategory().getName());

            Cell comeDateCell = dataRow.createCell(8);
            comeDateCell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(wage.getStaff().getComeDate()));

            Cell comeDateYearCell = dataRow.createCell(9);
            try {
                comeDateYearCell.setCellValue(StaffUtils.getFormatTime(wage.getStaff().getComeDate()));
            } catch (IllegalParametersException e) {
                e.printStackTrace();
            }

            Cell gangweiCell = dataRow.createCell(10);
            gangweiCell.setCellValue(wage.getStaff().getPerformancePay() + wage.getStaff().getWage());

            Cell performancePayCell = dataRow.createCell(11);
            performancePayCell.setCellValue(wage.getStaff().getPerformancePay());

            Cell wageCell = dataRow.createCell(12);
            wageCell.setCellValue(wage.getStaff().getWage());

            Cell dutyAllowanceCell = dataRow.createCell(13);
            dutyAllowanceCell.setCellValue(wage.getStaff().getDutyAllowance() == null ? 0 : wage.getStaff().getDutyAllowance());

            Cell grantsCell = dataRow.createCell(14);
            grantsCell.setCellValue(wage.getStaff().getGrants() == null ? 0 : wage.getStaff().getGrants());

            Cell total1Cell = dataRow.createCell(15);
            total1Cell.setCellValue(wage.getTotal1());

            Cell replenishmentCell = dataRow.createCell(16);
            replenishmentCell.setCellValue(wage.getReplenishment());

            Cell chargeBackCell = dataRow.createCell(17);
            chargeBackCell.setCellValue(wage.getChargeBack());

            Cell total2Cell = dataRow.createCell(18);
            total2Cell.setCellValue(wage.getTotal2());

            Cell teachingBonusCell = dataRow.createCell(19);
            teachingBonusCell.setCellValue(wage.getTeachingBonus());

            Cell mAllowanceCell = dataRow.createCell(20);
            mAllowanceCell.setCellValue(wage.getStaff().getMAllowance() == null ? 0 : wage.getStaff().getMAllowance());

            Cell pSubsidiesCell = dataRow.createCell(21);
            pSubsidiesCell.setCellValue(wage.getStaff().getPSubsidies() == null ? 0 : wage.getStaff().getPSubsidies());

            Cell studentAllowanceCell = dataRow.createCell(22);
            studentAllowanceCell.setCellValue(wage.getStudentAllowance());

            Cell bonusCell = dataRow.createCell(23);
            bonusCell.setCellValue(wage.getBonus());

            Cell schoolGrantCell = dataRow.createCell(24);
            schoolGrantCell.setCellValue(wage.getSchoolGrant());

            Cell teacherDayAllowanceCell = dataRow.createCell(25);
            teacherDayAllowanceCell.setCellValue(wage.getTeacherDayAllowance());

            Cell overtimeAssistanceCell = dataRow.createCell(26);
            overtimeAssistanceCell.setCellValue(wage.getOvertimeAssistance());

            Cell onDutySubsidiesCell = dataRow.createCell(27);
            onDutySubsidiesCell.setCellValue(wage.getOnDutySubsidies());

            Cell otherBenefitsCell = dataRow.createCell(28);
            otherBenefitsCell.setCellValue(wage.getOtherBenefits());

            Cell totalBenefitsCell = dataRow.createCell(29);
            totalBenefitsCell.setCellValue(wage.getTotalBenefits());

            Cell totalCell = dataRow.createCell(30);
            totalCell.setCellValue(wage.getTotal());

            Cell pensionInsuranceCell = dataRow.createCell(31);
            pensionInsuranceCell.setCellValue(wage.getPensionInsurance());

            Cell medicalInsuranceCell = dataRow.createCell(32);
            medicalInsuranceCell.setCellValue(wage.getMedicalInsurance());

            Cell unemploymentInsuranceCell = dataRow.createCell(33);
            unemploymentInsuranceCell.setCellValue(wage.getUnemploymentInsurance());

            Cell housingFundCell = dataRow.createCell(34);
            housingFundCell.setCellValue(wage.getHousingFund());

            Cell shouldMadeCell = dataRow.createCell(35);
            shouldMadeCell.setCellValue(wage.getShouldMade());

            Cell withholdingCell = dataRow.createCell(36);
            withholdingCell.setCellValue(wage.getWithholding());

            Cell personalIncomeTaxCell = dataRow.createCell(37);
            personalIncomeTaxCell.setCellValue(wage.getPersonalIncomeTax());

            Cell afterTaxRealHairCell = dataRow.createCell(38);
            afterTaxRealHairCell.setCellValue(wage.getAfterTaxRealHair());

            Cell unitPensionInsuranceCell = dataRow.createCell(39);
            unitPensionInsuranceCell.setCellValue(wage.getUnitPensionInsurance());

            Cell unitMedicalInsuranceCell = dataRow.createCell(40);
            unitMedicalInsuranceCell.setCellValue(wage.getUnitMedicalInsurance());

            Cell unitInjuryInsuranceCell = dataRow.createCell(41);
            unitInjuryInsuranceCell.setCellValue(wage.getUnitInjuryInsurance());

            Cell unitMaternityInsuranceCell = dataRow.createCell(42);
            unitMaternityInsuranceCell.setCellValue(wage.getUnitMaternityInsurance());

            Cell unitUnemploymentInsuranceCell = dataRow.createCell(43);
            unitUnemploymentInsuranceCell.setCellValue(wage.getUnitUnemploymentInsurance());

            Cell unitHousingFundCell = dataRow.createCell(44);
            unitHousingFundCell.setCellValue(wage.getUnitHousingFund());

            Cell hasHousingFundCell = dataRow.createCell(45);
            hasHousingFundCell.setCellValue(wage.getStaff().isHasHousingFund() ? "有" : "无");

            Cell bankIDCell = dataRow.createCell(46);
            sheet.setColumnWidth(46, wage.getStaff().getBankID().length() * 280);
            bankIDCell.setCellValue(wage.getStaff().getBankID());

            Cell sexCell = dataRow.createCell(47);
            sexCell.setCellValue(wage.getStaff().getSex() ? "男" : "女");

            Cell emailCell = dataRow.createCell(48);
            sheet.setColumnWidth(48, wage.getStaff().getEmail().length() * 280);
            emailCell.setCellValue(wage.getStaff().getEmail());
            logger.debug("downWageInfoByID::结束写入");
        }

        Staff staff = new Staff();
        staff.setWage(wageLists.stream().mapToInt(w -> w.getStaff().getWage()).sum());
        staff.setPerformancePay(wageLists.stream().mapToInt(w -> w.getStaff().getPerformancePay()).sum());
        staff.setDutyAllowance(wageLists.stream().mapToInt(w -> {
            if (w.getStaff().getDutyAllowance() == null) {
                return 0;
            } else {
                return w.getStaff().getDutyAllowance();
            }
        }).sum());
        staff.setGrants(wageLists.stream().mapToInt(w -> {
            if (w.getStaff().getGrants() == null) {
                return 0;
            } else {
                return w.getStaff().getGrants();
            }
        }).sum());

        totalWage.setStaff(staff);
        Row dataRow = sheet.createRow(nowCell[0]++);

        Cell cell = dataRow.createCell(10);
        cell.setCellValue(totalWage.getStaff().getWage() + totalWage.getStaff().getPerformancePay());

        Cell performancePayCell = dataRow.createCell(11);
        performancePayCell.setCellValue(totalWage.getStaff().getPerformancePay());

        Cell wageCell = dataRow.createCell(12);
        wageCell.setCellValue(totalWage.getStaff().getWage());

        Cell dutyAllowanceCell = dataRow.createCell(13);
        dutyAllowanceCell.setCellValue(totalWage.getStaff().getDutyAllowance() == null ? 0 : totalWage.getStaff().getDutyAllowance());

        Cell grantsCell = dataRow.createCell(14);
        grantsCell.setCellValue(totalWage.getStaff().getGrants() == null ? 0 : totalWage.getStaff().getGrants());

        Cell total1Cell = dataRow.createCell(15);
        total1Cell.setCellValue(totalWage.getTotal1());

        Cell replenishmentCell = dataRow.createCell(16);
        replenishmentCell.setCellValue(totalWage.getReplenishment());

        Cell chargeBackCell = dataRow.createCell(17);
        chargeBackCell.setCellValue(totalWage.getChargeBack());

        Cell total2Cell = dataRow.createCell(18);
        total2Cell.setCellValue(totalWage.getTotal2());

        Cell teachingBonusCell = dataRow.createCell(19);
        teachingBonusCell.setCellValue(totalWage.getTeachingBonus());

        Cell mAllowanceCell = dataRow.createCell(20);
        mAllowanceCell.setCellValue(totalWage.getStaff().getMAllowance() == null ? 0 : totalWage.getStaff().getMAllowance());

        Cell pSubsidiesCell = dataRow.createCell(21);
        pSubsidiesCell.setCellValue(totalWage.getStaff().getPSubsidies() == null ? 0 : totalWage.getStaff().getPSubsidies());

        Cell studentAllowanceCell = dataRow.createCell(22);
        studentAllowanceCell.setCellValue(totalWage.getStudentAllowance());

        Cell bonusCell = dataRow.createCell(23);
        bonusCell.setCellValue(totalWage.getBonus());

        Cell schoolGrantCell = dataRow.createCell(24);
        schoolGrantCell.setCellValue(totalWage.getSchoolGrant());

        Cell teacherDayAllowanceCell = dataRow.createCell(25);
        teacherDayAllowanceCell.setCellValue(totalWage.getTeacherDayAllowance());

        Cell overtimeAssistanceCell = dataRow.createCell(26);
        overtimeAssistanceCell.setCellValue(totalWage.getOvertimeAssistance());

        Cell onDutySubsidiesCell = dataRow.createCell(27);
        onDutySubsidiesCell.setCellValue(totalWage.getOnDutySubsidies());

        Cell otherBenefitsCell = dataRow.createCell(28);
        otherBenefitsCell.setCellValue(totalWage.getOtherBenefits());

        Cell totalBenefitsCell = dataRow.createCell(29);
        totalBenefitsCell.setCellValue(totalWage.getTotalBenefits());

        Cell totalCell = dataRow.createCell(30);
        totalCell.setCellValue(totalWage.getTotal());

        Cell pensionInsuranceCell = dataRow.createCell(31);
        pensionInsuranceCell.setCellValue(totalWage.getPensionInsurance());

        Cell medicalInsuranceCell = dataRow.createCell(32);
        medicalInsuranceCell.setCellValue(totalWage.getMedicalInsurance());

        Cell unemploymentInsuranceCell = dataRow.createCell(33);
        unemploymentInsuranceCell.setCellValue(totalWage.getUnemploymentInsurance());

        Cell housingFundCell = dataRow.createCell(34);
        housingFundCell.setCellValue(totalWage.getHousingFund());

        Cell shouldMadeCell = dataRow.createCell(35);
        shouldMadeCell.setCellValue(totalWage.getShouldMade());

        Cell withholdingCell = dataRow.createCell(36);
        withholdingCell.setCellValue(totalWage.getWithholding());

        Cell personalIncomeTaxCell = dataRow.createCell(37);
        personalIncomeTaxCell.setCellValue(totalWage.getPersonalIncomeTax());

        Cell afterTaxRealHairCell = dataRow.createCell(38);
        afterTaxRealHairCell.setCellValue(totalWage.getAfterTaxRealHair());

        Cell unitPensionInsuranceCell = dataRow.createCell(39);
        unitPensionInsuranceCell.setCellValue(totalWage.getUnitPensionInsurance());

        Cell unitMedicalInsuranceCell = dataRow.createCell(40);
        unitMedicalInsuranceCell.setCellValue(totalWage.getUnitMedicalInsurance());

        Cell unitInjuryInsuranceCell = dataRow.createCell(41);
        unitInjuryInsuranceCell.setCellValue(totalWage.getUnitInjuryInsurance());

        Cell unitMaternityInsuranceCell = dataRow.createCell(42);
        unitMaternityInsuranceCell.setCellValue(totalWage.getUnitMaternityInsurance());

        Cell unitUnemploymentInsuranceCell = dataRow.createCell(43);
        unitUnemploymentInsuranceCell.setCellValue(totalWage.getUnitUnemploymentInsurance());

        Cell unitHousingFundCell = dataRow.createCell(44);
        unitHousingFundCell.setCellValue(totalWage.getUnitHousingFund());

        workbook.write(servletOutputStream);
        logger.debug("downWageInfoByID::workbook写入到输出流完成");
        workbook.close();
        logger.debug("downWageInfoByID::workbook已关闭");
    }

    @Override
    public void delWageInfoByID(String id) throws NoSuchIdException {
        if (!wageDao.exists(id)) {
            logger.warn("delStaffInfoByID::ID->" + id + "不存在");
            throw new NoSuchIdException("ID:" + id + "不存在");
        }
        wageDao.delete(id);
    }
}
