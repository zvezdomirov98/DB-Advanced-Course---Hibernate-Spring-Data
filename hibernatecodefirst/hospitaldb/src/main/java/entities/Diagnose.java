package entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "diagnoses")
public class Diagnose implements DbEntity{
    private long id;
    private String name;
    private String comments;
    private Set<Patient> patients;

    public Diagnose() {

    }

    public Diagnose(String name,
                    String comments) {
        setName(name);
        setComments(comments);
        setPatients(new HashSet<>());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "patients",
    referencedColumnName = "diagnoses")
    public Set<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }
}
