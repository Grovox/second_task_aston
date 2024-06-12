package services;

import dto.UserPost;
import model.User;
import repo.BuyBookRepo;
import repo.UserRepo;

import java.util.List;

public class UserService {
    public static List<User> getAllUsers(){
        return UserRepo.getAllUser();
    }
    public static boolean haveUser(User user){
        return UserRepo.haveUser(user);
    }

    public static boolean haveUserById(int userId){
        return UserRepo.haveUserById(userId);
    }

    public static void deleteUser(User user) {
        UserRepo.deleteUser(user);
        BuyBookRepo.deleteBuyBookByUserId(user);
    }

    public static void changeUser(User user) {
        UserRepo.changeUser(user);
    }

    public static void addUser(UserPost user) {
        UserRepo.addUser(user);
    }
}
