package example.springjwt.models.entities.commons;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class BaseEntity {
    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "DATE_CREATE")
    @Temporal(value = TemporalType.DATE)
    private Date createdDate;

    @Column(name = "DATE_UPDATE")
    @Temporal(value = TemporalType.DATE)
    private Date updatedDate;

    public Long getId() {
        return id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
