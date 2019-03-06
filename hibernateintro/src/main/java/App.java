import entities.Address;
import entities.Employee;
import entities.Town;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

public class App {

    private static EntityManager em;

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("soft_uni");
        em = emf.createEntityManager();
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
}