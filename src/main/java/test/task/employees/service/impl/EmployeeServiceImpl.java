package test.task.employees.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import test.task.employees.entity.Employee;
import test.task.employees.entity.Project;
import test.task.employees.repository.EmployeeRepository;
import test.task.employees.service.EmployeeService;
import test.task.employees.service.ProjectService;
import test.task.employees.utils.DateUtilities;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ProjectService projectService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ProjectService projectService) {
        this.employeeRepository = employeeRepository;
        this.projectService = projectService;
    }

    @Override
    public boolean readEmployees(MultipartFile fileData) {
        List<String> lines = convertFileToString(fileData);
        if (lines.isEmpty() || fileData.isEmpty()) {
            return false;
        }
        lines
                .forEach(l -> {
                    String[] data = l.split(",\\s+");
                    if (data.length < 4) {
                        return;
                    }
                    Long empId = Long.parseLong(data[0]);
                    Long projId = Long.parseLong(data[1]);
                    LocalDate dateFrom = DateUtilities.parseDate(data[2]);
                    LocalDate dateTo;
                    dateTo = data[3].equalsIgnoreCase("null") ? null : DateUtilities.parseDate(data[3]);
                    Project project = projectService.findByProjectId(projId).orElse(null);
                    if (project == null) {
                       project = projectService.saveProject(new Project().setProjectId(projId));
                    }
                    Employee employee = new Employee()
                            .setEmpId(empId)
                            .setDateFrom(dateFrom)
                            .setDateTo(dateTo);
                    employeeRepository.save(employee);
                    project.getEmployees().add(employee);

                    projectService.saveProject(project);
                });
        return true;
    }

    private List<String> convertFileToString(MultipartFile fileData) {
        StringBuilder sb = new StringBuilder();
        try {
            InputStream inputStream = fileData.getInputStream();
            try (Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                int c = 0;
                while ((c = reader.read()) != -1) {
                    sb.append((char) c);
                }
            }
        } catch (IOException e) {
            return new ArrayList<>();
        }
        return Arrays.stream(sb.toString().split(System.lineSeparator())).collect(Collectors.toList());
    }

}
