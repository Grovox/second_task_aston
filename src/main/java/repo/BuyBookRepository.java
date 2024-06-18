package repo;

import model.BuyBook;

import java.util.List;

public interface BuyBookRepository extends Repository<BuyBook> {
    List<BuyBook> getAll();

    BuyBook findById(int id);

    void deleteByUserId(int id);

    void deleteByBookId(int id);

    void deleteByBooksId(List<Integer> bookId);
}
