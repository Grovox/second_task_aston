package mapper;

import dto.AuthorDelete;
import dto.AuthorPost;
import model.Author;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class AuthorMapperTest {
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

        String jsonOut = AuthorMapper.allAuthorsToJsonString(authors);

        String json = "[{\"authorId\":1,\"name\":\"Joe\"},{\"authorId\":2,\"name\":\"Alis\"}]";
        Assertions.assertEquals(jsonOut, json);
    }

    @Test
    void stringJsonToAuthor() {
        String json = "{\"authorId\":1,\"name\":\"Joe\"}";

        Author author = AuthorMapper.stringJsonToAuthor(json);

        Assertions.assertEquals(author.getAuthorId(), 1);
        Assertions.assertEquals(author.getName(), "Joe");
    }

    @Test
    void stringJsonToAuthorPost() {
        String json = "{\"name\":\"Joe\"}";

        AuthorPost authorPost = AuthorMapper.stringJsonToAuthorPost(json);

        Assertions.assertEquals(authorPost.getName(), "Joe");
    }

    @Test
    void stringJsonToAuthorDelete() {
        String json = "{\"authorId\":1}";

        AuthorDelete authorDelete = AuthorMapper.stringJsonToAuthorDelete(json);

        Assertions.assertEquals(authorDelete.getAuthorId(), 1);
    }

    @Test
    void authorPostToAuthor() {
        AuthorPost authorPost = new AuthorPost();
        authorPost.setName("Joe");

        Author author = AuthorMapper.authorPostToAuthor(authorPost);

        Assertions.assertEquals(author.getName(), authorPost.getName());
    }

    @Test
    void authorDeleteToAuthor() {
        AuthorDelete authorDelete = new AuthorDelete();
        authorDelete.setAuthorId(1);

        Author author = AuthorMapper.authorDeleteToAuthor(authorDelete);

        Assertions.assertEquals(author.getAuthorId(), authorDelete.getAuthorId());
    }
}