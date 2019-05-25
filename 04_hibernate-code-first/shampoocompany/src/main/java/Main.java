import ingredients.AmmoniumChloride;
import ingredients.BasicIngredient;
import ingredients.Mint;
import ingredients.Nettle;
import labels.BasicLabel;
import shampoos.BasicShampoo;
import shampoos.FreshNuke;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory managerFactory =
                Persistence
                        .createEntityManagerFactory("shampoo_company");
        EntityManager em = managerFactory.createEntityManager();
        em.getTransaction().begin();

        BasicIngredient am = new AmmoniumChloride();
        BasicIngredient mint = new Mint();
        BasicIngredient nettle = new Nettle();

        BasicLabel label = new BasicLabel(
                "Fresh Nuke Shampoo",
                "Contains mint and nettle"
        );

        BasicShampoo shampoo = new FreshNuke(label);
        shampoo.getIngredients().add(am);
        shampoo.getIngredients().add(nettle);
        shampoo.getIngredients().add(mint);
        em.persist(shampoo);

        em.getTransaction().commit();
        em.close();
    }
}
