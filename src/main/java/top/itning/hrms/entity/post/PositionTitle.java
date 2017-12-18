package top.itning.hrms.entity.post;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 岗位名称实体
 *
 * @author Ning
 */
@Data
@Entity
@Table(name = "POST_TITLE")
public class PositionTitle implements Serializable {
    /**
     * UUID
     */
    @Id
    private String id;
    /**
     * 岗位名称
     */
    @NotNull
    private String name;
}
