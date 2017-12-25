package top.itning.hrms.entity.department;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "grassroot")
    private List<Grassroot> grassroots;
}
