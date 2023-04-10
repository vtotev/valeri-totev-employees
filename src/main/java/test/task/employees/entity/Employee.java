package test.task.employees.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {

    @Column(name = "EmpID", nullable = false)
    private Long empId;
    @Column(name = "dateFrom")
    private LocalDate dateFrom;
    @Column(name = "dateTo")
    private LocalDate dateTo;

    @ManyToMany(mappedBy = "employees")
    private List<Project> project = new ArrayList<>();

    public Employee() {
    }

    public Long getEmpId() {
        return empId;
    }

    public Employee setEmpId(Long empId) {
        this.empId = empId;
        return this;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public Employee setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
        return this;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public Employee setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
        return this;
    }

    public List<Project> getProject() {
        return project;
    }

    public Employee setProject(List<Project> project) {
        this.project = project;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(empId, employee.empId) && Objects.equals(dateFrom, employee.dateFrom) && Objects.equals(dateTo, employee.dateTo) && Objects.equals(project, employee.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empId, dateFrom, dateTo, project);
    }
}
