package top.itning.hrms.entity.job;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 社会职称名实体
 *
 * @author Ning
 */
@Data
@Entity
@Table(name = "JOB_TITLE")
public class JobTitle implements Serializable {
    /**
     * UUID
     */
    @Id
    private String id;
    /**
     * 社会职称名
     */
    @NotNull
    private String name;
    /**
     * 职称级别
     */
    @NotNull
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "jobLevels")
    private List<JobLevel> jobLevels;
}
