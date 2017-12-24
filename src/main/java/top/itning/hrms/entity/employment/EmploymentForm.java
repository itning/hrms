package top.itning.hrms.entity.employment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用工形式实体
 *
 * @author Ning
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
