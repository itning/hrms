package top.itning.hrms.entity.employment;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用工形式实体
 * @author Ning
 */
@Data
@Entity
@Table(name = "EMP_FORM")
public class EmploymentForm implements Serializable {
    /**
     * UUID
     */
    @Id
    private String id;
    /**
     * 用工形式名
     */
    @NotNull
    private String name;
}
