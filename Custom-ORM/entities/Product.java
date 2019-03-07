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

    @Column(name = "price")
    private double price;

    public Product() {

    }

    public Product(String name, double price) {
        setName(name);
        setPrice(price);
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
}
