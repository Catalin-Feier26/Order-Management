package businessLayer;

import dataAccess.ClientDao;
import model.Client;

import java.sql.SQLException;
import java.util.List;
/**
 * This class is responsible for managing the clients.
 */

public class ClientManager {
    private ClientDao clientDao;
    /**
     * Constructs a new ClientManager with a new ClientDao.
     */

    public ClientManager() {
        this.clientDao = new ClientDao();
    }
    /**
     * Creates a new client with the given name, address, and contact number.
     * @param name the name of the client
     * @param address the address of the client
     * @param contactNumber the contact number of the client
     * @return the created client
     * @throws SQLException if an error occurs while creating the client
     */
    public Client createClient(String name, String address, String contactNumber) throws SQLException {
        Client client = new Client(0, name, address, contactNumber);
        return clientDao.create(client);
    }
    /**
     * Updates the client with the given client ID with the given name, address, and contact number.
     * @param clientId the ID of the client
     * @param name the name of the client
     * @param address the address of the client
     * @param contactNumber the contact number of the client
     * @return the updated client
     * @throws SQLException if an error occurs while updating the client
     */
    public Client updateClient(int clientId, String name, String address, String contactNumber) throws SQLException {
        Client client = new Client(clientId, name, address, contactNumber);
        return clientDao.update(client);
    }
    /**
     * Deletes the client with the given client ID.
     * @param clientId the ID of the client
     * @throws SQLException if an error occurs while deleting the client
     */
    public void deleteClient(int clientId) throws SQLException {
        clientDao.delete(clientId);
    }
    /**
     * Gets the client with the given client ID.
     * @param clientId the ID of the client
     * @return the client with the given client ID
     * @throws SQLException if an error occurs while getting the client
     */
    public Client getClient(int clientId) throws SQLException {
        return clientDao.read(clientId);
    }
    /**
     * Gets all clients in the application.
     * @return a list of all clients
     * @throws SQLException if an error occurs while getting all clients
     */
    public List<Client> getAllClients() throws SQLException {
        return clientDao.readAll();
    }
}