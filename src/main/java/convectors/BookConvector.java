package convectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.BookPost;
import model.Book;

import java.util.List;

public class BookConvector {
    private static ObjectMapper objectMapper = new ObjectMapper();

    private BookConvector() {
    }

    public static String allBooksToJsonString(List<Book> books) throws JsonProcessingException {
        return objectMapper.writeValueAsString(books);
    }

    public static Book stringJsonToBook(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Book.class);
    }

    public static BookPost stringJsonToBookPost(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, BookPost.class);
    }

}
