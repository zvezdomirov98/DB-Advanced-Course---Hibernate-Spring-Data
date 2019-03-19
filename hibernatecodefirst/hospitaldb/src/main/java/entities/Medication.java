package entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "medications")
public class Medication implements DbEntity {
    private long id;
    private String name;
    private Set<Patient> patients;

    public Medication() {

    }

    public Medication(String name) {
        setName(name);
        setPatients(new HashSet<>());
    }

    @Id
    @Column
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "patients",
            referencedColumnName = "medications")
    public Set<Patient> getPatients() {
        return this.patients;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }
}
