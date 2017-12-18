package top.itning.hrms.entity.department;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 部门实体
 *
 * @author Ning
 */
@Data
@Entity
@Table(name = "DEP_DEPARTMENT")
public class Department implements Serializable {
    /**
     * UUID
     */
    @Id
    private String id;
    /**
     * 部门名
     */
    @NotNull
    private String name;
    /**
     * 专业/基层单位
     */
    @NotNull
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "grassroot")
    private List<Grassroot> grassroots;
}
