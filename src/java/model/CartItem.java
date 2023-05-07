package model;

public class CartItem {
    
    private Product product;
    private String size;

    public CartItem() {
    }

    public CartItem(Product product, String size) {
        this.product = product;
        this.size = size;
    } 
    
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
           
}
