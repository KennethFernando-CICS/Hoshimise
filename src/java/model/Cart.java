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
    
    public void addToCart(CartItem cartItem, int quantity){
        if(quantity <= 0)
            quantity = 1;
        this.cartItemMap.put(cartItem, quantity);
        System.out.println("[Cart]Added ID: " + cartItem.getProductId() + " - " + cartItem.getSize() + " to cart.");
    }
    
    public boolean takeOutFromCart(CartItem takeOutItem){
        for(Map.Entry<CartItem,Integer> entry: this.cartItemMap.entrySet()){
            CartItem cartItem = entry.getKey();
            if(cartItem.equals(takeOutItem))
            {
                System.out.println("[Cart]Took out ID: " + cartItem.getProductId() + " - " + cartItem.getSize() + " from cart.");
                cartItemMap.remove(cartItem);
                return true;
            }
        }        
        return false;
    }
}
