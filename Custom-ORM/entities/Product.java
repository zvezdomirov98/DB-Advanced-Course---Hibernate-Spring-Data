package entities;

import db.annotations.Column;
import db.annotations.Entity;
import db.annotations.Primary;

@Entity(name = "products")
public class Product {
    @Primary(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "batch")
    private String batch;

    @Column(name = "price")
    private double price;

    public Product() {

    }

    public Product(String name, double price) {
        setName(name);
        setPrice(price);
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format(
                "%s - $%.2f",
                getName(),
                getPrice());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
