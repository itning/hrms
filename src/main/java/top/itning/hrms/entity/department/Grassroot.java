package top.itning.hrms.entity.department;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 基层单位实体
 *
 * @author Ning
 */
@Data
@Entity
@Table(name = "DEP_GRASSROOT")
public class Grassroot implements Serializable {
    /**
     * UUID
     */
    @Id
    private String id;
    /**
     * 专业/基层单位
     */
    @NotNull
    private String name;
}
