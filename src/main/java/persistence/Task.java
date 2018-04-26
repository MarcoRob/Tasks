/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import business.Reminder;

@Entity
@Table(name = "tasks")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tasks.findAll", query = "SELECT t FROM Task t")
    , @NamedQuery(name = "Tasks.findById", query = "SELECT t FROM Task t WHERE t.id = :id")
    , @NamedQuery(name = "Tasks.findByTitle", query = "SELECT t FROM Task t WHERE t.title = :title")
    , @NamedQuery(name = "Tasks.findByTitleAndUserId", query = "SELECT t FROM Task t WHERE t.title = :title AND t.userId = :userId")
    , @NamedQuery(name = "Tasks.findByDescription", query = "SELECT t FROM Task t WHERE t.description = :description")
    , @NamedQuery(name = "Tasks.findByDueDate", query = "SELECT t FROM Task t WHERE t.dueDate = :dueDate")
    , @NamedQuery(name = "Tasks.findByCompletedDate", query = "SELECT t FROM Task t WHERE t.completedDate = :completedDate")
    , @NamedQuery(name = "Tasks.findByUserId", query = "SELECT t FROM Task t WHERE t.userId = :userId")})
public class Task extends business.TaskWithReminder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", updatable = false)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "title")
    private String title;
    @Size(max = 600)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "due_date")
    private long dueDate;
    @Column(name = "completed_date")
    private BigInteger completedDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_id")
    private String userId;
    @Column(name = "reminder")
    private long reminder;

    public Task() {

    }

    public Task(Long id) {
        this.id = id;
    }

    public Task(Long id, String title, String description, long dueDate,  BigInteger completedDate, String userId) {
    	super(title, description, dueDate, completedDate,  userId);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public BigInteger getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(BigInteger completedDate) {
        this.completedDate = completedDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public long getRemind() {
    	business.Reminder reminder = new business.Reminder(this.getDueDate());
		return reminder.getTime();
    }

    public void setRemind(long remind) {
        this.reminder = remind;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Task)) {
            return false;
        }
        Task other = (Task) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Task[ id=" + id + " title= " + title + " ]";
    }
    
}
