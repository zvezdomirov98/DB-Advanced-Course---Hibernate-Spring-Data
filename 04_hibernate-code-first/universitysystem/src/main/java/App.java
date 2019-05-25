import entities.Course;
import entities.Student;
import entities.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;

public class App {
    private static EntityManager em;

    public static void main(String[] args) {
        EntityManagerFactory managerFactory =
                Persistence.createEntityManagerFactory("university");
        em = managerFactory.createEntityManager();

        Teacher teacher = new Teacher(
                "Pesho",
                "Peshov",
                "088811122",
                "pesho@gmail.com",
                new BigDecimal("10")
        );

        Student student = new Student(
                "Gosho",
                "Goshov",
                "098882221",
                5.5,
                6
        );

        Course course = new Course(
                "Java DB",
                "Hibernate",
                new Date(),
                new Date(),
                12.5);
        course.setTeacher(teacher);
        course.getStudents().add(student);
        teacher.getCourses().add(course);
        student.getCourses().add(course);

        inTransaction(() -> {
            em.persist(course);
            em.persist(student);
            em.persist(teacher);
        });
    }

    public static void inTransaction(Runnable runnable) {
        em.getTransaction().begin();
        runnable.run();
        em.getTransaction().commit();
    }
}
