package repo;

import model.AuthorBook;

import java.util.List;

public interface AuthorBookRepository {
    List<AuthorBook> findByAuthorId(int id);

    List<AuthorBook> findByBookId(int id);
    List<AuthorBook> findByBooksId(List<Integer> booksId);

    void deleteAllFromBookId(int id);

    void saveAll(List<AuthorBook> authorBooks);

    void deleteAllFromBooksId(List<Integer> booksId);
}
