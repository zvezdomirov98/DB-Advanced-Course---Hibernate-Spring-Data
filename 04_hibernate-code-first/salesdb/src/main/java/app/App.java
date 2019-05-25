package app;

import entities.Customer;
import entities.Product;
import entities.Sale;
import entities.StoreLocation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.Date;

public class App {
    private static EntityManager em;

    public static void main(String[] args) {
        EntityManagerFactory managerFactory = Persistence
                .createEntityManagerFactory("sales");
        em = managerFactory.createEntityManager();

        Product product = new Product("MacBook",
                100, new BigDecimal("3499"));
        StoreLocation storeLocation = new StoreLocation("Paradise Center");
        Customer customer = new Customer("Pesho",
                "pesho@abv.bg", "999-888-777");
        Sale sale = new Sale(product, customer, storeLocation, new Date());

        inTransaction(() -> {
            em.persist(product);
            em.persist(customer);
            em.persist(storeLocation);
            em.persist(sale);
        });
    }

    private static void inTransaction(Runnable runnable) {
        em.getTransaction().begin();
        runnable.run();
        em.getTransaction().commit();
    }
}
