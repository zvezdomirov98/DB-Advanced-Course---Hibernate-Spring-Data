package entities;

import javax.persistence.*;
import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "patients")
public class Patient implements DbEntity{
    private long id;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private Date birthDate;
    private File picture;
    private boolean medicallyInsured;
    private Set<Visitation> visitations;
    private Set<Diagnose> diagnoses;
    private Set<Medication> prescribedMedications;

    public Patient() {

    }

    public Patient(String firstName,
                   String lastName,
                   String email,
                   Date birthDate,
                   boolean medicallyInsured) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setBirthDate(birthDate);
        setMedicallyInsured(medicallyInsured);
        setVisitations(new HashSet<Visitation>());
        setDiagnoses(new HashSet<Diagnose>());
        setPrescribedMedications(new HashSet<Medication>());
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

    @Column(name = "first_name",
            nullable = false)
    public String getFirstName() {
        return firstName;
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name",
    nullable = false)
    public String getLastName() {
        return lastName;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "birth_date",
    nullable = false)
    public Date getBirthDate() {
        return birthDate;
    }

    private void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Column
    public File getPicture() {
        return picture;
    }

    private void setPicture(File picture) {
        this.picture = picture;
    }

    @Column(nullable = false)
    public boolean isMedicallyInsured() {
        return medicallyInsured;
    }

    public void setMedicallyInsured(boolean hasMedicalInsurance) {
        this.medicallyInsured = hasMedicalInsurance;
    }

    @OneToMany(mappedBy = "patient")
    public Set<Visitation> getVisitations() {
        return visitations;
    }

    public void setVisitations(Set<Visitation> visitations) {
        this.visitations = visitations;
    }

    @ManyToMany(mappedBy = "patients")
    public Set<Diagnose> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(Set<Diagnose> diagnoses) {
        this.diagnoses = diagnoses;
    }

    @ManyToMany(mappedBy = "patients")
    public Set<Medication> getPrescribedMedications() {
        return prescribedMedications;
    }

    public void setPrescribedMedications(Set<Medication> prescribedMedications) {
        this.prescribedMedications = prescribedMedications;
    }
}
