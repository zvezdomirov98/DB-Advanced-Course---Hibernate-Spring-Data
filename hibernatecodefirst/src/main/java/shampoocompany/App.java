package shampoocompany;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {
    private static EntityManager em;
    private static EntityManagerFactory emf;

    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("shampoo_company");
        em = emf.createEntityManager();
        em.getTransaction().begin();

        em.getTransaction().commit();
    }
    
}
