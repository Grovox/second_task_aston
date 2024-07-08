package mapper;

import dto.UserDelete;
import dto.UserPost;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    @Test
    void allUsersToJsonString() {
        User user1 = new User();
        user1.setUserId(1);
        user1.setName("Joe");
        user1.setAge(1);
        User user2 = new User();
        user2.setUserId(2);
        user2.setName("Alis");
        user2.setAge(2);
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        String jsonOut = UserMapper.allUsersToJsonString(users);

        String json = "[{\"userId\":1,\"age\":1,\"name\":\"Joe\"},{\"userId\":2,\"age\":2,\"name\":\"Alis\"}]";
        Assertions.assertEquals(jsonOut, json);
    }

    @Test
    void stringJsonToUser() {
        String json = "{\"userId\":1,\"age\":1,\"name\":\"Joe\"}";

        User user = UserMapper.stringJsonToUser(json);

        Assertions.assertEquals(user.getUserId(), 1);
        Assertions.assertEquals(user.getName(), "Joe");
        Assertions.assertEquals(user.getAge(), 1);
    }

    @Test
    void stringJsonToUserPost() {
        String json = "{\"age\":1,\"name\":\"Joe\"}";

        UserPost userPost = UserMapper.stringJsonToUserPost(json);

        Assertions.assertEquals(userPost.getName() , "Joe");
        Assertions.assertEquals(userPost.getAge(), 1);
    }

    @Test
    void stringJsonToUserDelete() {
        String json = "{\"userId\":1}";

        UserDelete userDelete = UserMapper.stringJsonToUserDelete(json);

        Assertions.assertEquals(userDelete.getUserId(), 1);
    }
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