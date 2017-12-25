package top.itning.hrms.entity.job;

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
 * 社会职称名实体
 *
 * @author Ning
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
}
