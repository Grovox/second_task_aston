package services;

import dto.BuyBookDelete;
import dto.BuyBookPost;
import mapper.BuyBookMapper;
import model.Book;
import model.BuyBook;
import repo.BookRepository;
import repo.BuyBookRepository;
import repo.impl.BookRepositoryImpl;
import repo.impl.BuyBookRepositoryImpl;

import java.util.List;

public class BuyBookService {
    private BuyBookRepository buyBookRepository = BuyBookRepositoryImpl.getInstance();
    private BookRepository bookRepository = BookRepositoryImpl.getInstance();
    private static BuyBookService instance;
    private BuyBookService() {
    }

    public static synchronized BuyBookService getInstance() {
        if (instance == null) {
            instance = new BuyBookService();
        }
        return instance;
    }

    public List<BuyBook> getAllBuyBooks() {
        return buyBookRepository.getAll();
    }

    public boolean haveBuyBookById(int id) {
        return buyBookRepository.findById(id) != null;
    }

    public void changeBuyBook(BuyBook buyBook) {
        BuyBook buyBookDB = buyBookRepository.findById(buyBook.getBuyBookId());
        if (buyBookDB.getBookId() == buyBook.getBookId()) {
            Book book = bookRepository.findById(buyBook.getBookId());

            int newAmountBook = book.getAmount() - (buyBook.getAmount() - buyBookDB.getAmount());
            book.setAmount(newAmountBook);

            bookRepository.update(book);
            buyBookRepository.update(buyBook);
        } else {
            Book bookPrev = bookRepository.findById(buyBookDB.getBookId());
            bookPrev.setAmount(bookPrev.getAmount() + buyBookDB.getAmount());
            bookRepository.update(bookPrev);

            Book bookNow = bookRepository.findById(buyBook.getBookId());
            bookNow.setAmount(bookNow.getAmount() - buyBook.getAmount());
            bookRepository.update(bookNow);
            buyBookRepository.update(buyBook);
        }
    }

    public void addBuyBook(BuyBookPost buyBookPost) {
        Book book = bookRepository.findById(buyBookPost.getBookId());
        BuyBook buyBook = BuyBookMapper.buyBookPostToBuyBook(buyBookPost);

        book.setAmount(book.getAmount() - buyBookPost.getAmount());
        buyBook.setPrice(book.getPrice());

        bookRepository.update(book);
        buyBookRepository.save(buyBook);
    }

    public void deleteBuyBook(BuyBookDelete buyBookDelete) {
        BuyBook buyBook = BuyBookMapper.buyBookDeleteToBuyBook(buyBookDelete);
        buyBookRepository.delete(buyBook);
    }
}
