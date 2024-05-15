package businessLayer;

import dataAccess.ClientDao;
import model.Client;

import java.sql.SQLException;
import java.util.List;

public class ClientManager {
    private ClientDao clientDao;


    public ClientManager() {
        this.clientDao = new ClientDao();
    }

    public Client createClient(String name, String address, String contactNumber) throws SQLException {
        Client client = new Client(0, name, address, contactNumber);
        return clientDao.create(client);
    }

    public Client updateClient(int clientId, String name, String address, String contactNumber) throws SQLException {
        Client client = new Client(clientId, name, address, contactNumber);
        return clientDao.update(client);
    }

    public void deleteClient(int clientId) throws SQLException {
        clientDao.delete(clientId);
    }

    public Client getClient(int clientId) throws SQLException {
        return clientDao.read(clientId);
    }
    public List<Client> getAllClients() throws SQLException {
        return clientDao.readAll();
    }
}