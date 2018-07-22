package mysql1;

import java.util.List;

public interface ClientDAO {
    void init();
    void addClient(String name, int age);
    void deleteClient(int id);
    List<Client> getAll();
}
