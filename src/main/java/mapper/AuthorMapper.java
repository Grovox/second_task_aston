package mapper;

import dto.AuthorDelete;
import dto.AuthorPost;
import model.Author;

public class AuthorMapper {
    public static Author authorPostToAuthor(AuthorPost authorPost) {
        Author author = new Author();
        author.setName(authorPost.getName());
        return author;
    }

    public static Author authorDeleteToAuthor(AuthorDelete authorDelete) {
        Author author = new Author();
        author.setAuthorId(authorDelete.getAuthorId());
        return author;
    }
}
