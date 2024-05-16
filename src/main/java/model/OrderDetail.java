package model;

import java.math.BigDecimal;

public class OrderDetail {
    private int orderdetailId;
    private int ordertableId;
    private int productId;
    private int quantity;


    public OrderDetail(int orderdetailId, int ordertableId, int productId, int quantity) {
        this.orderdetailId = orderdetailId;
        this.ordertableId = ordertableId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getOrderDetailId() {
        return orderdetailId;
    }

    public void setOrderDetailId(int orderdetailId) {
        this.orderdetailId = orderdetailId;
    }

    public int getOrdertableId() {
        return ordertableId;
    }

    public void setOrdertableId(int ordertableId) {
        this.ordertableId = ordertableId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderdetailId=" + orderdetailId +
                ", ordertableId=" + ordertableId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}