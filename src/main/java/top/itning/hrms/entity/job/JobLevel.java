package top.itning.hrms.entity.job;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 职称级别实体
 *
 * @author Ning
 */
@Data
@Entity
@Table(name = "JOB_LEVEL")
public class JobLevel implements Serializable {
    /**
     * UUID
     */
    @Id
    private String id;
    /**
     * 职称级别名
     */
    @NotNull
    private String name;
}
