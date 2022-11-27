package org.shiftworksboot.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.shiftworksboot.constant.Role;
import org.shiftworksboot.dto.EmployeeFormDto;
import org.shiftworksboot.entity.Department;
import org.shiftworksboot.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.yml")
class EmployeeServiceTest {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PasswordEncoder passwordEncoder;


    public Department createDepartment(){
        Department department = new Department();
        department.setDept_name("인사팀");
        department.setDept_id("dept1");
        department.setAuthority(Role.USER);
//        department.setDept_name("회계팀");
//        department.setDept_id("dept2");
//        department.setAuthority(Role.USER);
//        department.setDept_name("영업팀");
//        department.setDept_id("dept3");
//        department.setAuthority(Role.USER);
//        department.setDept_name("보안팀");
//        department.setDept_id("infosecu");
//        department.setAuthority(Role.ADMIN);
        return department;
    }

    public Employee createEmployee(){
        EmployeeFormDto employeeFormDto = new EmployeeFormDto();

        employeeFormDto.setEmp_id("test-user1");
        employeeFormDto.setName("김사원");
        employeeFormDto.setDept_name("인사팀");
        return Employee.createEmployee(employeeFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest(){
        Employee employee = createEmployee();
        Department department = createDepartment();
        Employee savedEmployee = employeeService.saveEmployee(employee);

        assertEquals(employee.getEmp_id(), savedEmployee.getEmp_id());
        assertEquals(employee.getName(), savedEmployee.getName());
        assertEquals(employee.getDepartment().getDept_name(), savedEmployee.getDepartment().getDept_name());
        assertEquals(department.getAuthority(), savedEmployee.getDepartment().getAuthority());

    }

}