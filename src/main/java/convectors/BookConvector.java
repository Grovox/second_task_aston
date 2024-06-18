package convectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.BookGet;
import dto.BookDelete;
import dto.BookPost;
import dto.BookPut;

import java.util.List;

public class BookConvector {
    private static ObjectMapper objectMapper = new ObjectMapper();

    private BookConvector() {
    }

    public static String allBookGetToJsonString(List<BookGet> books) {
        try {
            return objectMapper.writeValueAsString(books);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static BookPut stringJsonToBookPut(String json) {
        try {
            return objectMapper.readValue(json, BookPut.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static BookPost stringJsonToBookPost(String json) {
        try {
            return objectMapper.readValue(json, BookPost.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static BookDelete stringJsonToBookDelete(String json) {
        try {
            return objectMapper.readValue(json, BookDelete.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
