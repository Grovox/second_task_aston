package mapper;

import dto.AuthorDelete;
import dto.AuthorPost;
import model.Author;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AuthorMapperTest {

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