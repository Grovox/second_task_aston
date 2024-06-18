package mapper;

import dto.UserDelete;
import dto.UserPost;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void userPostToUser() {
        UserPost userPost = new UserPost();
        userPost.setName("joe");
        userPost.setAge(11);

        User user = UserMapper.userPostToUser(userPost);

        Assertions.assertEquals(user.getName(), userPost.getName());
        Assertions.assertEquals(user.getAge(), userPost.getAge());
    }

    @Test
    void userDeleteToUser() {
        UserDelete userDelete = new UserDelete();
        userDelete.setUserId(1);

        User user = UserMapper.userDeleteToUser(userDelete);

        Assertions.assertEquals(user.getUserId(), userDelete.getUserId());
    }
}