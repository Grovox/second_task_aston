package mapper;

import dto.UserDelete;
import dto.UserPost;
import model.User;

public class UserMapper {
    public static User userPostToUser(UserPost userPost) {
        User user = new User();
        user.setName(userPost.getName());
        user.setAge(userPost.getAge());
        return user;
    }

    public static User userDeleteToUser(UserDelete userDelete) {
        User user = new User();
        user.setUserId(userDelete.getUserId());
        return user;
    }
}
