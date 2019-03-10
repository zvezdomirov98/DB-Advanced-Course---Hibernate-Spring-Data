package shampoocompany.labels;

import shampoocompany.shampoos.BasicShampoo;

import javax.persistence.*;

@Entity
@Table(name = "labels")
public class BasicLabel implements Label {
    private long id;
    private String title;
    private String subtitle;

    @OneToOne(mappedBy = "label",
            targetEntity = BasicShampoo.class,
            cascade = CascadeType.ALL)
    private BasicShampoo basicShampoo;

    public BasicLabel() {

    }

    public BasicLabel(String title, String subtitle) {
        setTitle(title);
        setSubtitle(subtitle);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Column
    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Column
    @Override
    public String getSubtitle() {
        return this.subtitle;
    }

    @Override
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
