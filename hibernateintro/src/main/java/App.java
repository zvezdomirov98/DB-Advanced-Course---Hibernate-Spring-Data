import entities.Employee;
import entities.Town;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

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
}