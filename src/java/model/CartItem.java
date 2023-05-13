package model;

import java.util.Objects;

public class CartItem {
    
    private int productId;
    private String size;

    public CartItem(int productId, String size) {
        this.productId = productId;
        this.size = size;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CartItem other = (CartItem) obj;
        if (this.productId != other.productId) {
            return false;
        }
        return Objects.equals(this.size, other.size);
    }    
}
