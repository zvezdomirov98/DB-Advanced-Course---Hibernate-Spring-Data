package entities;

import interfaces.DbEntity;

import javax.persistence.*;

@Entity
@Table(name = "billing_details")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public class BillingDetail implements DbEntity {
    private long id;
    private String number;
    private User owner;

    public BillingDetail() {

    }

    public BillingDetail(String number,
                         User owner) {
        setNumber(number);
        setOwner(owner);
    }

    @Id
    @Column
    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Column(nullable = false,
            unique = true)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
            referencedColumnName = "id")
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
