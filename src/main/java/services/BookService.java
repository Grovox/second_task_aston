package services;

import dto.BookPost;
import repo.BookRepo;
import model.Book;

import java.util.List;

public class BookService {
    public static List<Book> getAllBook() {
        return BookRepo.getAllBook();
    }

    public static boolean haveBookById(int bookId) {
        return BookRepo.haveBookById(bookId);
    }

    public static void changeBook(Book book) {
        BookRepo.changeBook(book);
    }

    public static void addBook(BookPost bookPost) {
        BookRepo.addBook(bookPost);
    }

    public static boolean haveBook(Book book) {
        return BookRepo.haveBook(book);
    }

    public static void deleteBook(Book book) {
        BookRepo.deleteBook(book);
    }
}
