package services;

import dto.AuthorPost;
import model.Author;
import repo.AuthorRepo;

import java.util.List;

public class AuthorService {
    public static List<Author> getAllAuthors() {
        return AuthorRepo.getAllAuthors();
    }

    public static boolean haveAuthorById(int authorId) {
        return AuthorRepo.haveAuthorById(authorId);
    }

    public static void changeAuthor(Author author) {
        AuthorRepo.changeAuthor(author);
    }

    public static void addAuthor(AuthorPost authorPost) {
        AuthorRepo.addAuthor(authorPost);
    }

    public static boolean haveAuthor(Author author) {
        return AuthorRepo.haveAuthor(author);
    }

    public static void deleteAuthor(Author author) {
        AuthorRepo.deleteAuthor(author);
    }

    public static boolean haveAuthors(List<Author> authors) {
        for (Author author : authors) {
            if (!haveAuthor(author))
                return false;
        }
        return true;
    }
}
