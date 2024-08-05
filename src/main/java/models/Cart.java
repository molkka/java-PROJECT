package models;

import java.util.HashMap;
import java.util.Map;

public class Cart{


    private Map<Products,Integer> products ;

    public Cart() {
        products = new HashMap<>();
    }

    public void addQuantity(Products product , int quantity){
        // find the priduct in the map and update the quantity
        if(products.containsKey(product)) {
            products.put(product, products.get(product) + quantity);
        }
        else
            products.put(product,quantity);
    }
    public void DecrementQuantity(Products product , int quantity){
        // find the priduct in the map and update the quantity
        if(products.containsKey(product)) {
            if(products.get(product) > 1)
                products.put(product, products.get(product) - quantity);
            else
            {
                removeQuantity(product);
            }
        }
    }
    public void removeQuantity(Products product){
        if(products.containsKey(product)){
            products.remove(product);
        }

    }
    public void UpdateQuantity(Products product,int quantity){
        if(products.containsKey(product)){
            products.put(product,quantity);
        }
    }
    public void clear(){
        products.clear();
    }
    public Map<Products, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Products, Integer> products) {
        this.products = products;
    }

    public int getTotalCost(){
        return products.entrySet().stream().mapToInt(e->e.getKey().getPrice()*e.getValue()).sum();
    }

    @Override
    public String toString() {
        return "Cart{" +
                "products=" + products +
                '}';
    }
}
