package ua.kiev.prog;

import java.util.List;

public interface UserDAO {
    void add(String name, int age);
    List<User> getAll();
}
