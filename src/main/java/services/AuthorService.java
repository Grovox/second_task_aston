package services;

import dto.AuthorDelete;
import dto.AuthorId;
import dto.AuthorPost;
import mapper.AuthorMapper;
import model.Author;
import model.AuthorBook;
import repo.AuthorBookRepository;
import repo.AuthorRepository;
import repo.BookRepository;
import repo.BuyBookRepository;
import repo.impl.AuthorBookRepositoryImpl;
import repo.impl.AuthorRepositoryImpl;
import repo.impl.BookRepositoryImpl;
import repo.impl.BuyBookRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class AuthorService {

    private AuthorRepository authorRepository = AuthorRepositoryImpl.getInstance();
    private AuthorBookRepository authorBookRepository = AuthorBookRepositoryImpl.getInstance();
    private BookRepository bookRepository = BookRepositoryImpl.getInstance();
    private BuyBookRepository buyBookRepository = BuyBookRepositoryImpl.getInstance();
    private static AuthorService instance;

    private AuthorService() {
    }

    public static synchronized AuthorService getInstance() {
        if (instance == null) {
            instance = new AuthorService();
        }
        return instance;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.getAll();
    }

    public boolean haveAuthorById(int id) {
        return authorRepository.findById(id) != null;
    }

    public void changeAuthor(Author author) {
         authorRepository.update(author);
    }

    public void addAuthor(AuthorPost authorPost) {
        Author author = AuthorMapper.authorPostToAuthor(authorPost);
        authorRepository.save(author);
    }

    public void deleteAuthor(AuthorDelete authorDelete) {
        List<AuthorBook> authorBooks = authorBookRepository.findByAuthorId(authorDelete.getAuthorId());

        List<Integer> booksId = new ArrayList<>();
        if (authorBooks != null && !authorBooks.isEmpty()) {
            for (AuthorBook authorBook : authorBooks) {
                booksId.add(authorBook.getBookId());
            }
        }
        buyBookRepository.deleteByBooksId(booksId);

        authorBooks = authorBookRepository.findByBooksId(booksId);
        List<Integer> authorsId = new ArrayList<>();
        if (authorBooks != null && !authorBooks.isEmpty()) {
            for (AuthorBook authorBook : authorBooks) {
                authorsId.add(authorBook.getBookId());
            }
        }
        authorBookRepository.deleteAllFromBooksId(booksId);
        bookRepository.deleteByBooksId(booksId);

        authorRepository.deleteAuthorByIdIfNotHaveBooks(authorsId);
        Author author = AuthorMapper.authorDeleteToAuthor(authorDelete);
        authorRepository.delete(author);
    }

    public boolean haveAuthorsById(List<AuthorId> authorsId) {
        if (authorsId != null && !authorsId.isEmpty()) {
            for (AuthorId authorId : authorsId) {
                if (!haveAuthorById(authorId.getAuthorId()))
                    return false;
            }
        }
        return true;
    }
}
