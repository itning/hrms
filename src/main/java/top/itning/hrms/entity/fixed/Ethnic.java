package top.itning.hrms.entity.fixed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 民族实体
 * <p>【民族代码及名称如下】：
 * <p> 01	汉族
 * <p> 02	蒙古族
 * <p> 03	回族
 * <p> 04	藏族
 * <p> 05	维吾尔族
 * <p> 06	苗族
 * <p> 07	彝族
 * <p> 08	壮族
 * <p> 09	布依族
 * <p> 10	朝鲜族
 * <p> 11	满族
 * <p> 12	侗族
 * <p> 13	瑶族
 * <p> 14	白族
 * <p> 15	土家族
 * <p> 16	哈尼族
 * <p> 17	哈萨克族
 * <p> 18	傣族
 * <p> 19	黎族
 * <p> 20	傈僳族
 * <p> 21	佤族
 * <p> 22	畲族
 * <p> 23	高山族
 * <p> 24	拉祜族
 * <p> 25	水族
 * <p> 26	东乡族
 * <p> 27	纳西族
 * <p> 28	景颇族
 * <p> 29	柯尔克孜族
 * <p> 30	土族
 * <p> 31	达斡尔族
 * <p> 32	仫佬族
 * <p> 33	羌族
 * <p> 34	布朗族
 * <p> 35	撒拉族
 * <p> 36	毛难族
 * <p> 37	仡佬族
 * <p> 38	锡伯族
 * <p> 39	阿昌族
 * <p> 40	普米族
 * <p> 41	塔吉克族
 * <p> 42	怒族
 * <p> 43	乌孜别克族
 * <p> 44	俄罗斯族
 * <p> 45	鄂温克族
 * <p> 46	崩龙族
 * <p> 47	保安族
 * <p> 48	裕固族
 * <p> 49	京族
 * <p> 50	塔塔尔族
 * <p> 51	独龙族
 * <p> 52	鄂伦春族
 * <p> 53	赫哲族
 * <p> 54	门巴族
 * <p> 55	珞巴族
 * <p> 56	基诺族
 * <p> 97	其他
 * <p> 98	外国血统
 *
 * @author Ning
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FIXED_ETHNIC")
public class Ethnic implements Serializable {
    /**
     * ID
     */
    @Id
    private String id;
    /**
     * 名称
     */
    @NotNull
    private String name;
}
