package convectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.AuthorDelete;
import dto.AuthorPost;
import model.Author;

import java.util.List;

public class AuthorConvector {
    private static ObjectMapper objectMapper = new ObjectMapper();

    private AuthorConvector() {
    }

    public static String allAuthorsToJsonString(List<Author> authors) {
        try {
            return objectMapper.writeValueAsString(authors);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Author stringJsonToAuthor(String json) {
        try {
            return objectMapper.readValue(json, Author.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static AuthorPost stringJsonToAuthorPost(String json) {
        try {
            return objectMapper.readValue(json, AuthorPost.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static AuthorDelete stringJsonToAuthorDelete(String json) {
        try {
            return objectMapper.readValue(json, AuthorDelete.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
