package test.task.employees.service.impl;

import org.springframework.stereotype.Service;
import test.task.employees.entity.Employee;
import test.task.employees.entity.Project;
import test.task.employees.entity.Team;
import test.task.employees.entity.view.ProjectViewModel;
import test.task.employees.repository.ProjectRepository;
import test.task.employees.service.ProjectService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private List<Team> teams = new ArrayList<>();

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Optional<Project> findByProjectId(Long projId) {
        return projectRepository.findByProjectId(projId);
    }

    @Override
    public List<Project> getProjectWithMoreThanOneEmployees() {
        return projectRepository.getAllProjectsWithMoreThanOneEmployee();
    }

    // check if team is already added to list
    public boolean isTeamPresent(Employee empl1, Employee empl2, Long projectId) {
        return teams.stream().anyMatch(team ->
                (team.getFirstEmployee().equals(empl1) || team.getFirstEmployee().equals(empl2))
                        && (team.getSecondEmployee().equals(empl2) || team.getSecondEmployee().equals(empl1))
                        && team.getProjectId().equals(projectId));
    }

    @Override
    public List<ProjectViewModel> getProjectsByEmployeeCouples() {
        // Find all teams with projects
        teams.clear();
        List<Project> projects = getProjectWithMoreThanOneEmployees();
        projects.forEach(p -> {
            List<Employee> employees = p.getEmployees().stream().sorted(Comparator.comparing(Employee::getEmpId)).toList();
            employees.forEach(e -> {
                employees.forEach(e2 -> {
                    if (!e.equals(e2) && isDateOverlapping(e, e2) && !isTeamPresent(e, e2, p.getProjectId())) {
                        teams.add(new Team(e, e2, p.getProjectId()));
                    }
                });
            });
        });

        // Populate teams in views with calculated days worked
        var ref = new Object() {
            List<ProjectViewModel> views = new ArrayList<>();
        };
        teams.forEach(team -> {
            Employee firstEmployee = team.getFirstEmployee();
            Employee secondEmployee = team.getSecondEmployee();
            ref.views.add(new ProjectViewModel().setProjectId(team.getProjectId())
                    .setFirstEmployee(firstEmployee.getEmpId())
                    .setSecondEmployee(secondEmployee.getEmpId())
                    .setDaysWorked(calculateDays(firstEmployee, secondEmployee)));
        });

        // Create grouping by teams to sum up all their days worked together
        Optional<String> entry = ref.views.stream()
                .collect(Collectors.groupingBy(team -> team.getFirstEmployee() + "-" + team.getSecondEmployee(),
                        Collectors.summingLong(ProjectViewModel::getDaysWorked)))
                .entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).map(Map.Entry::getKey);

        // get the longest worked team if any
        if (entry.isPresent()) {
            String[] ids = entry.get().split("-");
            return ref.views.stream().filter(projectViewModel -> projectViewModel.getFirstEmployee().equals(Long.parseLong(ids[0])) &&
                    projectViewModel.getSecondEmployee().equals(Long.parseLong(ids[1]))).toList();
        }
        return new ArrayList<>();
    }

    // Calculate days worked together
    private Long calculateDays(Employee firstEmp, Employee secondEmp) {
        LocalDate empl1DateTo = firstEmp.getDateTo() == null ? LocalDate.now() : firstEmp.getDateTo();
        LocalDate empl2DateTo = secondEmp.getDateTo() == null ? LocalDate.now() : secondEmp.getDateTo();
        LocalDate dateStart = firstEmp.getDateFrom().isBefore(secondEmp.getDateFrom()) ? secondEmp.getDateFrom() : firstEmp.getDateFrom();
        LocalDate dateEnd = empl1DateTo.isAfter(empl2DateTo) ? empl2DateTo : empl1DateTo;
        return ChronoUnit.DAYS.between(dateStart, dateEnd);
    }

    // check if dates are overlapping
    private boolean isDateOverlapping(Employee empl1, Employee empl2) {
        LocalDate empl1DateFrom = empl1.getDateFrom();
        LocalDate empl2DateFrom = empl2.getDateFrom();
        LocalDate empl1DateTo = empl1.getDateTo() == null ? LocalDate.now() : empl1.getDateTo();
        LocalDate empl2DateTo = empl2.getDateTo() == null ? LocalDate.now() : empl2.getDateTo();
        return empl1DateFrom.isBefore(empl2DateFrom) && empl1DateTo.isAfter(empl2DateFrom) ||
                empl1DateFrom.isBefore(empl2DateTo) && empl1DateTo.isAfter(empl2DateTo) ||
                empl1DateFrom.isBefore(empl2DateFrom) && empl1DateTo.isAfter(empl2DateTo) ||
                empl1DateFrom.isAfter(empl2DateFrom) && empl1DateTo.isBefore(empl2DateTo);
    }

}
