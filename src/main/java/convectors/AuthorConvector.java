package convectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.AuthorPost;
import model.Author;

import java.util.List;

public class AuthorConvector {
    private static ObjectMapper objectMapper = new ObjectMapper();

    private AuthorConvector() {
    }

    public static String allAuthorsToJsonString(List<Author> authors) throws JsonProcessingException {
        return objectMapper.writeValueAsString(authors);
    }

    public static Author stringJsonToAuthor(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Author.class);
    }

    public static AuthorPost stringJsonToAuthorPost(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, AuthorPost.class);
    }
}
