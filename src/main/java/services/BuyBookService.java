package services;

import dto.BuyBookPost;
import model.BuyBook;
import repo.BookRepo;
import repo.BuyBookRepo;

import java.util.List;

public class BuyBookService {
    public static List<BuyBook> getAllBuyBooks() {
        return BuyBookRepo.getAllBuyBooks();
    }

    public static boolean haveBuyBookById(int buyBookId) {
        return BuyBookRepo.haveBuyBookById(buyBookId);
    }

    public static void changeBuyBook(BuyBook buyBook) {
        int pervAmountBuyBook = BuyBookRepo.getAmountBuyBookById(buyBook.getBuyBookId());
        BookRepo.changeBookAmountById(buyBook.getAmount() - pervAmountBuyBook, buyBook.getBookId());
        BuyBookRepo.changeBuyBook(buyBook);
    }

    public static void addBuyBook(BuyBookPost buyBookPost) {
        BookRepo.changeBookAmountById(buyBookPost.getAmount(), buyBookPost.getBookId());
        BuyBookRepo.addBuyBook(buyBookPost);
    }

    public static boolean haveBuyBook(BuyBook buyBook) {
        return BuyBookRepo.haveBuyBook(buyBook);
    }

    public static void deleteBuyBook(BuyBook buyBook) {
        BuyBookRepo.deleteBuyBook(buyBook);
    }

    public static boolean isEnoughBooks(int bookId, int amount) {
        int amountBook = BookRepo.getAmountBookById(bookId);
        return amountBook >= amount;
    }
}
