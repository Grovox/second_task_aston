package mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.BookDelete;
import dto.BookGet;
import dto.BookPost;
import dto.BookPut;
import model.Book;

import java.util.List;

public class BookMapper {
    private static ObjectMapper objectMapper = new ObjectMapper();

    private BookMapper() {
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
    public static Book bookPutToBook(BookPut bookPut) {
        Book book = new Book();
        book.setBookId(bookPut.getBookId());
        book.setTitle(bookPut.getTitle());
        book.setPrice(bookPut.getPrice());
        book.setAmount(bookPut.getAmount());
        return book;
    }

    public static Book bookPostToBook(BookPost bookPost) {
        Book book = new Book();
        book.setTitle(bookPost.getTitle());
        book.setPrice(bookPost.getPrice());
        book.setAmount(bookPost.getAmount());
        return book;
    }

    public static Book bookDeleteToBook(BookDelete bookDelete) {
        Book book = new Book();
        book.setBookId(bookDelete.getBookId());
        return book;
    }
}
