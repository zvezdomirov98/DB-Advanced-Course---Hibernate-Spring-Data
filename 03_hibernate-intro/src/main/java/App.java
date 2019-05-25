import entities.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class App {

    private static EntityManager em;

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("soft_uni");
        em = emf.createEntityManager();
        getDepartmentsMaxSalary();
    }

    /*Problem 1: Remove Objects*/
    private static void setLongNamesToLower() {
        String hqlQuery = "FROM Town";
        em.getTransaction().begin();
        List<Town> result = em.createQuery(hqlQuery, Town.class).getResultList();
        result
                .forEach(t -> {
                    if (t.getName().length() > 5) {
                        em.detach(t);
                    } else {
                        t.setName(t.getName().toLowerCase());
                        em.persist(t);
                    }
                });
        em.getTransaction().commit();
    }

    /*Problem 2: Contains Employee*/
    private static boolean isEmployeeInDb(String empName) {
        String query =
                "FROM Employee " +
                        "WHERE concat(first_name, ' ', last_name) = :name";

        em.getTransaction().begin();
        try {
            Employee employee = em.createQuery(query, Employee.class)
                    .setParameter("name", empName)
                    .getSingleResult();
            return employee != null;
        } catch (NoResultException e) {
            return false;
        }
    }

    /*Problem 3: Employees with salary over 50000*/
    private static List<String> getHighSalaryNames(BigDecimal salaryLowerBound) {
        String query =
                "SELECT firstName FROM Employee WHERE salary > :salary";
        return em.createQuery(query, String.class)
                .setParameter("salary", salaryLowerBound)
                .getResultList();
    }

    /*Problem 4: Employees from Department*/
    private static List<Employee> getEmpsFromDept(String department) {
        String query =
                "FROM Employee WHERE department.name = :name";
        return em.createQuery(query, Employee.class)
                .setParameter("name", department)
                .getResultList();
    }

    /*Problem 5: Adding a New Address and
        Updating Employee*/
    private static void addNewAddress(Address address) {
        em.getTransaction().begin();
        em.persist(address);
        em.getTransaction().commit();
    }

    private static void updateEmployeeAddress(
            Address newAddress, String lastName) {
        em.getTransaction().begin();
        String selectEmpQuery =
                "FROM Employee WHERE lastName = :lastName";
        Employee targetEmp = em
                .createQuery(selectEmpQuery, Employee.class)
                .setParameter("lastName", lastName)
                .getSingleResult();
        targetEmp.setAddress(newAddress);
        newAddress.getEmployees().add(targetEmp);
        em.persist(targetEmp);
        em.persist(newAddress);
        em.getTransaction().commit();
    }

    /*Problem 6: Addresses with employee count*/
    private static HashMap<String, Integer> getAddressesEmpCount() {

        List<Address> addresses = em
                .createQuery(
                        "FROM Address",
                        Address.class)
                .getResultList();
        HashMap<String, Integer> result = new HashMap<>();
        addresses
                .forEach(a -> result.put(
                        a.getText(),
                        a.getEmployees().size()));
        return result;
    }

    /*Problem 7: Get Employee With Project*/
    private static Employee getEmpById(int id) {
        return em.createQuery(
                "FROM Employee WHERE id = :param", Employee.class)
                .setParameter("param", id)
                .getSingleResult();
    }

    private static void printEmployeeInfo(Employee employee) {
        String employeeProjects =
                employee.getProjects().stream()
                        .map(Project::getName)
                        .sorted(Comparator.reverseOrder())
                        .collect(Collectors.joining("\n\t"));
        System.out.printf("%s %s - %s\n\t%s",
                employee.getFirstName(),
                employee.getLastName(),
                employee.getJobTitle(),
                employeeProjects);
    }

    /*Problem 8: Find Latest 10 Projects*/
    private static List<Project> findLastStartedProjects() {
        TypedQuery<Project> query =
                em.createQuery(
                        "FROM Project " +
                                "ORDER BY startDate DESC",
                        Project.class);
        query.setMaxResults(10);
        return query.getResultList();
    }

    private static void printProjectInfo(Project project) {
        System.out.printf(
                "Project name: %s\n" +
                        "\tProject description: %s\n" +
                        "\tProject start date: %s\n" +
                        "\tProject end date: %s\n",
                project.getName(),
                project.getDescription(),
                project.getStartDate(),
                project.getEndDate());
    }

    /*Problem 9: Increase Salaries*/
    private static List<Employee> getEmpFromDept(String depName) {
        return em.createQuery(
                "FROM Employee " +
                        "WHERE department.name = :name",
                Employee.class)
                .setParameter("name", depName)
                .getResultList();
    }

    private static void increaseEmpSalary(Employee emp) {
        emp.setSalary(
                emp.getSalary().multiply(
                        new BigDecimal(1.12)));
        inTransaction(() -> em.persist(emp));
    }

    private static void printEmpSalaryInfo(Employee emp) {
        System.out.printf(
                "%s %s ($%.2f)\n",
                emp.getFirstName(),
                emp.getLastName(),
                emp.getSalary());
    }

    private static void inTransaction(Runnable runnable) {
        em.getTransaction().begin();
        runnable.run();
        em.getTransaction().commit();
    }

    private static void testProblem9() {
        String[] promoteDepartments = {
                "Engineering",
                "Tool Design",
                "Marketing",
                "Information Services"
        };
        for (String department : promoteDepartments) {
            getEmpFromDept(department)
                    .forEach(e -> {
                        increaseEmpSalary(e);
                        printEmpSalaryInfo(e);
                    });
        }
    }

    /*Problem 10: Remove Towns*/
    private static int removeTown(String townName) {
        Town townToRemove = em.createQuery(
                "FROM Town WHERE name = :name", Town.class)
                .setParameter("name", townName)
                .getSingleResult();
        List<Address> addressesFromTown = getTownAddresses(townToRemove);

        inTransaction(() -> {
            addressesFromTown
                    .forEach(em::remove);
            em.remove(townToRemove);
        });
        return addressesFromTown.size();
    }

    private static List<Address> getTownAddresses(Town town) {
        return em.createQuery(
                "FROM Address WHERE town = :town",
                Address.class)
                .setParameter("town", town)
                .getResultList();
    }

    private static void testProblem10(String townToRemove) {
        int removedAddresses = removeTown(townToRemove);
        String addressPostfix =
                removedAddresses == 1 ?
                        "" :
                        "es";
        System.out.printf(
                "%d address%s in %s deleted",
                removedAddresses,
                addressPostfix,
                townToRemove
        );
    }

    /*Problem 11: Find Employees by First Name*/
    private static List<Employee> getEmpsByPattern(String pattern) {
        return em.createQuery(
                "FROM Employee WHERE UPPER(firstName) " +
                        "LIKE UPPER(:pattern) ",
                Employee.class)
                .setParameter("pattern", pattern + "%")
                .getResultList();
    }

    private static void printEmpJobSalary(Employee employee) {
        System.out.printf(
                "%s %s - %s - ($%s)\n",
                employee.getFirstName(),
                employee.getLastName(),
                employee.getJobTitle(),
                employee.getSalary());
    }

    private static void testProblem11(String pattern) {
        getEmpsByPattern(pattern)
                .forEach(App::printEmpJobSalary);
    }

    /*Problem 12: Employees Maximum Salaries*/
    private static void getDepartmentsMaxSalary() {

    }
}