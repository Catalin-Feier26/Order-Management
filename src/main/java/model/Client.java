package model;

public class Client {

    public int clientId;
    private String name;
    private String address;
    private String contactNumber;

    public Client(int clientId, String name, String address, String contactNumber) {
        this.clientId = clientId;
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
    }
    public int getClientId() {
        return clientId;
    }
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getContactNumber() {
        return contactNumber;
    }
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", name='" + name + '\'' +
                '}';
    }
}
