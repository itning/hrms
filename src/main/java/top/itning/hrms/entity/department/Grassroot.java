package top.itning.hrms.entity.department;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
