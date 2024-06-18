package mapper;

import dto.BuyBookDelete;
import dto.BuyBookPost;
import model.BuyBook;

public class BuyBookMapper {
    public static BuyBook buyBookPostToBuyBook(BuyBookPost buyBookPost) {
        BuyBook buyBook = new BuyBook();
        buyBook.setAmount(buyBookPost.getAmount());
        buyBook.setBookId(buyBookPost.getBookId());
        buyBook.setUserId(buyBookPost.getUserId());
        return buyBook;
    }

    public static BuyBook buyBookDeleteToBuyBook(BuyBookDelete buyBookDelete) {
        BuyBook buyBook = new BuyBook();
        buyBook.setBuyBookId(buyBookDelete.getBuyBookId());
        return buyBook;
    }
}
