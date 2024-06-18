package services;

import dto.UserDelete;
import dto.UserPost;
import mapper.UserMapper;
import model.User;
import repo.BuyBookRepository;
import repo.UserRepository;
import repo.impl.BuyBookRepositoryImpl;
import repo.impl.UserRepositoryImpl;

import java.util.List;

public class UserService {
    private UserRepository userRepository = UserRepositoryImpl.getInstance();
    private BuyBookRepository buyBookRepository = BuyBookRepositoryImpl.getInstance();
    private static UserService instance;
    private UserService() {
    }

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }
    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    public boolean haveUserById(int id) {
        return userRepository.findById(id) != null;
    }

    public void deleteUser(UserDelete userDelete) {
        User user = UserMapper.userDeleteToUser(userDelete);
        userRepository.delete(user);
        buyBookRepository.deleteByUserId(user.getUserId());
    }

    public void changeUser(User user) {
        userRepository.update(user);
    }

    public void addUser(UserPost userPost) {
        User user = UserMapper.userPostToUser(userPost);
        userRepository.save(user);
    }
}
