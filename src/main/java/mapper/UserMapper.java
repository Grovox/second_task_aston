package mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UserDelete;
import dto.UserPost;
import model.User;

import java.util.List;

public class UserMapper {
    private static ObjectMapper objectMapper = new ObjectMapper();

    private UserMapper() {
    }

    public static String allUsersToJsonString(List<User> users) {
        try {
            return objectMapper.writeValueAsString(users);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static User stringJsonToUser(String json) {
        try {
            return objectMapper.readValue(json, User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static UserPost stringJsonToUserPost(String json) {
        try {
            return objectMapper.readValue(json, UserPost.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static UserDelete stringJsonToUserDelete(String json) {
        try {

            return objectMapper.readValue(json, UserDelete.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
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
