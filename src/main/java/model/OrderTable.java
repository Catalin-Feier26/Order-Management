package model;

import java.sql.Timestamp;

public class OrderTable {
    public int orderId;
    private int clientId;
    private Timestamp orderDate;

    public OrderTable(int orderId, int clientId, Timestamp orderDate) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.orderDate = orderDate;
    }
    public int getOrderTableId() {
        return orderId;
    }
    public void setOrderTableId(int orderId) {
        this.orderId = orderId;
    }
    public int getClientId() {
        return clientId;
    }
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    public Timestamp getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }
    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", clientId=" + clientId +
                ", orderDate=" + orderDate +
                '}';
    }
}