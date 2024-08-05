package models;

import java.util.Objects;

public class Products {
    private int id;
    private int price;
    private String title;
    private String image;

    public Products() {
    }

    public Products(int id, int price, String title, String image) {
        this.id = id;
        this.price = price;
        this.title = title;
        this.image = image;
    }

    public Products(int price, String title, String image) {
        this.price = price;
        this.title = title;
        this.image = image;
    }

    @Override
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Products products = (Products) o;
        return price == products.price && Objects.equals(title, products.title) && Objects.equals(image, products.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, title, image);
    }
}
