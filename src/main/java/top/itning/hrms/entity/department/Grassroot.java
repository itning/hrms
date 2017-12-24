package top.itning.hrms.entity.department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@AllArgsConstructor
@NoArgsConstructor
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
