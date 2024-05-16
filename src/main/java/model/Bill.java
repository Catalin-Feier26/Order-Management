package model;
/**
 * The Bill record represents a bill in the application.
 */
public record Bill(int ordertableId,int clientId, int noProducts, double totalPrice){}

