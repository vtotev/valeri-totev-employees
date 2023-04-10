package test.task.employees.entity;

public class Team {
    private Employee firstEmployee;
    private Employee secondEmployee;
    private Long projectId;

    public Team(Employee firstEmployee, Employee secondEmployee, Long projectId) {
        this.firstEmployee = firstEmployee;
        this.secondEmployee = secondEmployee;
        this.projectId = projectId;
    }

    public Employee getFirstEmployee() {
        return firstEmployee;
    }

    public void setFirstEmployee(Employee firstEmployee) {
        this.firstEmployee = firstEmployee;
    }

    public Employee getSecondEmployee() {
        return secondEmployee;
    }

    public void setSecondEmployee(Employee secondEmployee) {
        this.secondEmployee = secondEmployee;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
