package top.itning.hrms.entity.post;

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
 * 岗位类别实体
 *
 * @author Ning
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "POST_CATEGORY")
public class PositionCategory implements Serializable {
    /**
     * UUID
     */
    @Id
    private String id;
    /**
     * 岗位类别名
     */
    @NotNull
    private String name;
}
