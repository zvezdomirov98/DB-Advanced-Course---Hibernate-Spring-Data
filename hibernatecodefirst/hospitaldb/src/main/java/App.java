import entities.Diagnose;
import entities.Medication;
import entities.Patient;
import entities.Visitation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;

public class App {
    private static EntityManager em;
    public static void main(String[] args) {
        EntityManagerFactory managerFactory = Persistence
                .createEntityManagerFactory("hospital");
        em = managerFactory.createEntityManager();
        Patient patient = new Patient(
                "pesho", "peshov",
                "pesho1@abv.bg", new Date(),
                true);
        final Visitation visitation = new Visitation(new Date(), patient);
        patient.getVisitations().add(visitation);
        final Diagnose diagnose = new Diagnose("Ill", "High Flu");
        patient.getDiagnoses().add(diagnose);
        final Medication medication = new Medication("Aspirin");
        patient.getPrescribedMedications().add(medication);

        visitation.setPatient(patient);
        diagnose.getPatients().add(patient);
        medication.getPatients().add(patient);
        inTransaction(() -> {
            em.persist(visitation);
            em.persist(diagnose);
            em.persist(medication);
            em.persist(patient);
        });
    }

    public static void inTransaction(Runnable runnable) {
        em.getTransaction().begin();
        runnable.run();
        em.getTransaction().commit();
    }
}
