package convectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UserPost;
import model.User;

import java.util.List;

public class UserConvector {
    private static ObjectMapper objectMapper = new ObjectMapper();

    private UserConvector() {
    }

    public static String allUsersToJsonString(List<User> users) throws JsonProcessingException {
        return objectMapper.writeValueAsString(users);
    }

    public static User stringJsonToUser(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, User.class);
    }

    public static UserPost stringJsonToUserPost(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, UserPost.class);
    }
}
