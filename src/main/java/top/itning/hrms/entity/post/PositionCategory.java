package top.itning.hrms.entity.post;

import lombok.Data;

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
@Entity
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
