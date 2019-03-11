package entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student extends BasePerson
        implements DbEntity {
    private int id;
    private double averageGrade;
    private int attendance;
    private Set<Course> courses;

    public Student() {
        super();
    }

    public Student(String firstName,
                   String lastName,
                   String phoneNumber,
                   double averageGrade,
                   int attendance) {
        super(firstName, lastName, phoneNumber);
        setAverageGrade(averageGrade);
        setAttendance(attendance);
        setCourses(new HashSet<>());
    }

    @Id
    @Column
    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "average_grade")
    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    @Column
    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    @ManyToMany(mappedBy = "students")
    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
