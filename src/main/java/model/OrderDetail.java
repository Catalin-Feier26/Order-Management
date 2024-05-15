package model;

import java.math.BigDecimal;

public class OrderDetail {
    private int orderdetailId;
    private int ordertableId;
    private int productId;
    private int quantity;
    private BigDecimal unitPrice;

    public OrderDetail(int orderdetailId, int ordertableId, int productId, int quantity, BigDecimal unitPrice) {
        this.orderdetailId = orderdetailId;
        this.ordertableId = ordertableId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
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

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderdetailId=" + orderdetailId +
                ", ordertableId=" + ordertableId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}