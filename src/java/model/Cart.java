package model;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    
    private String owner;
    private Map<CartItem,Integer> cartItemMap;
    
    public Cart(){}
    
    public Cart(String owner){
        this.owner = owner;
        this.cartItemMap = new HashMap<>();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Map<CartItem, Integer> getCartItemMap() {
        return cartItemMap;
    }

    public void setCartItemMap(Map<CartItem, Integer> cartItemMap) {
        this.cartItemMap = cartItemMap;
    }
    
}
