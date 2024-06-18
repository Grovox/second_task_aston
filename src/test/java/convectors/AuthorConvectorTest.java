package convectors;

import dto.AuthorDelete;
import dto.AuthorPost;
import model.Author;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class AuthorConvectorTest {

    @Test
    void allAuthorsToJsonString() {
        Author author1 = new Author();
        author1.setAuthorId(1);
        author1.setName("Joe");
        Author author2 = new Author();
        author2.setAuthorId(2);
        author2.setName("Alis");
        List<Author> authors = new ArrayList<>();
        authors.add(author1);
        authors.add(author2);

        String jsonOut = AuthorConvector.allAuthorsToJsonString(authors);

        String json = "[{\"authorId\":1,\"name\":\"Joe\"},{\"authorId\":2,\"name\":\"Alis\"}]";
        Assertions.assertEquals(jsonOut, json);
    }

    @Test
    void stringJsonToAuthor() {
        String json = "{\"authorId\":1,\"name\":\"Joe\"}";

        Author author = AuthorConvector.stringJsonToAuthor(json);

        Assertions.assertEquals(author.getAuthorId(), 1);
        Assertions.assertEquals(author.getName(), "Joe");
    }

    @Test
    void stringJsonToAuthorPost() {
        String json = "{\"name\":\"Joe\"}";

        AuthorPost authorPost = AuthorConvector.stringJsonToAuthorPost(json);

        Assertions.assertEquals(authorPost.getName(), "Joe");
    }

    @Test
    void stringJsonToAuthorDelete() {
        String json = "{\"authorId\":1}";

        AuthorDelete authorDelete = AuthorConvector.stringJsonToAuthorDelete(json);

        Assertions.assertEquals(authorDelete.getAuthorId(), 1);
    }
}