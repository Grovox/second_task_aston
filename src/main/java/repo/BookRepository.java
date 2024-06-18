package repo;

import dto.BookGet;
import model.Book;

import java.util.List;

public interface BookRepository extends Repository<Book> {
    List<BookGet> getAll();

    Book findById(int id);

    void deleteByBooksId(List<Integer> booksId);

    int getLastId();
}
