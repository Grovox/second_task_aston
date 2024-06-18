package convectors;

import dto.UserDelete;
import dto.UserPost;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserConvectorTest {

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

        String jsonOut = UserConvector.allUsersToJsonString(users);

        String json = "[{\"userId\":1,\"age\":1,\"name\":\"Joe\"},{\"userId\":2,\"age\":2,\"name\":\"Alis\"}]";
        Assertions.assertEquals(jsonOut, json);
    }

    @Test
    void stringJsonToUser() {
        String json = "{\"userId\":1,\"age\":1,\"name\":\"Joe\"}";

        User user = UserConvector.stringJsonToUser(json);

        Assertions.assertEquals(user.getUserId(), 1);
        Assertions.assertEquals(user.getName(), "Joe");
        Assertions.assertEquals(user.getAge(), 1);
    }

    @Test
    void stringJsonToUserPost() {
        String json = "{\"age\":1,\"name\":\"Joe\"}";

        UserPost userPost = UserConvector.stringJsonToUserPost(json);

        Assertions.assertEquals(userPost.getName() , "Joe");
        Assertions.assertEquals(userPost.getAge(), 1);
    }

    @Test
    void stringJsonToUserDelete() {
        String json = "{\"userId\":1}";

        UserDelete userDelete = UserConvector.stringJsonToUserDelete(json);

        Assertions.assertEquals(userDelete.getUserId(), 1);
    }
}