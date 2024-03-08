package ecommerce;

abstract class Customer{
       abstract void buyProduct(String login);
       abstract void addToCart(String login);
       abstract void buyFromCart(String login);
       abstract void returnProduct(String login);

} 
