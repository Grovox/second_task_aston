package convectors;

import dto.BookDelete;
import dto.BookGet;
import dto.BookPost;
import dto.BookPut;
import mapper.BookMapper;
import model.Author;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookConvectorTest {

    @Test
    void allBookGetToJsonString() {
        Author author1 = new Author();
        author1.setAuthorId(1);
        author1.setName("Joe");
        Author author2 = new Author();
        author2.setAuthorId(2);
        author2.setName("Alis");
        List<Author> authors1 = new ArrayList<>();
        authors1.add(author1);
        authors1.add(author2);
        List<Author> authors2 = new ArrayList<>();
        authors2.add(author1);
        BookGet bookGet1 = new BookGet();
        bookGet1.setBookId(1);
        bookGet1.setAuthors(authors1);
        bookGet1.setTitle("book1");
        bookGet1.setPrice(1.1f);
        bookGet1.setAmount(1);
        BookGet bookGet2 = new BookGet();
        bookGet2.setBookId(2);
        bookGet2.setAuthors(authors2);
        bookGet2.setTitle("book2");
        bookGet2.setPrice(2.2f);
        bookGet2.setAmount(2);
        List<BookGet> booksGet = new ArrayList<>();
        booksGet.add(bookGet1);
        booksGet.add(bookGet2);

        String jsonOut = BookConvector.allBookGetToJsonString(booksGet);

        String json = "[{\"bookId\":1,\"title\":\"book1\",\"price\":1.1,\"amount\":1,\"authors\":[{\"authorId\":1,\"name\":\"Joe\"},{\"authorId\":2,\"name\":\"Alis\"}]},{\"bookId\":2,\"title\":\"book2\",\"price\":2.2,\"amount\":2,\"authors\":[{\"authorId\":1,\"name\":\"Joe\"}]}]";
        Assertions.assertEquals(jsonOut, json);
    }

    @Test
    void stringJsonToBookPut() {
        String json = "{\"bookId\":1," +
                "\"title\":\"book\"," +
                "\"price\":1.1," +
                "\"amount\":1," +
                "\"authorsId\":[" +
                "{\"authorId\":1}," +
                "{\"authorId\":2}]}";


        BookPut bookPut = BookConvector.stringJsonToBookPut(json);

        Assertions.assertEquals(bookPut.getBookId(), 1);
        Assertions.assertEquals(bookPut.getTitle(), "book");
        Assertions.assertEquals(bookPut.getPrice(), 1.1f);
        Assertions.assertEquals(bookPut.getAmount(), 1);
        Assertions.assertEquals(bookPut.getAuthorsId().size(), 2);
    }

    @Test
    void stringJsonToBookPost() {
        String json = "{\"title\":\"book\"," +
                "\"price\":1.1," +
                "\"amount\":1," +
                "\"authorsId\":[" +
                "{\"authorId\":1}," +
                "{\"authorId\":2}]}";


        BookPost bookPost = BookConvector.stringJsonToBookPost(json);

        Assertions.assertEquals(bookPost.getTitle(), "book");
        Assertions.assertEquals(bookPost.getPrice(), 1.1f);
        Assertions.assertEquals(bookPost.getAmount(), 1);
        Assertions.assertEquals(bookPost.getAuthorsId().size(), 2);
    }

    @Test
    void stringJsonToBookDelete() {
        String json = "{\"bookId\":1}";

        BookDelete bookDelete = BookConvector.stringJsonToBookDelete(json);

        Assertions.assertEquals(bookDelete.getBookId(), 1);
    }
}