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
 * 政治面貌实体
 * <p>【政治面貌分为以下13类，代码及名称如下】：
 * <p> 01 中共党员
 * <p> 02 中共预备党员
 * <p> 03 共青团员
 * <p> 04 民革党员
 * <p> 05 民盟盟员
 * <p> 06 民建会员
 * <p> 07 民进会员
 * <p> 08 农工党党员
 * <p> 09 致公党党员
 * <p> 10 九三学社社员
 * <p> 11 台盟盟员
 * <p> 12 无党派人士
 * <p> 13 群众（现称普通居民，与居民身份证相对应）
 *
 * @author Ning
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FIXED_PS")
public class PoliticalStatus implements Serializable {
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
