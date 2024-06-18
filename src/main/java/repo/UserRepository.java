package repo;

import model.User;

import java.util.List;

public interface UserRepository extends Repository<User> {
    List<User> getAll();

    User findById(int id);
}
