import entities.BillingDetail;
import entities.CreditCard;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.Month;
import java.time.Year;
import java.util.Date;

public class App {
    private static EntityManager em;

    public static void main(String[] args) {
        EntityManagerFactory managerFactory = Persistence
                .createEntityManagerFactory("billspaymentsystem");
        em = managerFactory.createEntityManager();

        User pesho = new User("Pesho", "Peshov",
                "pesho123@abv.bg", "1234");
        Date expirationDate = new Date();
        BillingDetail billingDetail = new CreditCard("99441", pesho, "MasterCard",
                Month.of(expirationDate.getMonth()),
                Year.of(expirationDate.getYear()));
        pesho.getBillingDetails().add(billingDetail);

        inTransaction(() -> {
            em.persist(pesho);
            em.persist(billingDetail);
        });
    }

    public static void inTransaction(Runnable runnable) {
        em.getTransaction().begin();
        runnable.run();
        em.getTransaction().commit();
    }
}
