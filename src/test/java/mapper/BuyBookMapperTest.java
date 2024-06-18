package mapper;

import dto.BuyBookDelete;
import dto.BuyBookPost;
import model.BuyBook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BuyBookMapperTest {

    @Test
    void buyBookPostToBuyBook() {
        BuyBookPost buyBookPost = new BuyBookPost();
        buyBookPost.setUserId(1);
        buyBookPost.setBookId(1);
        buyBookPost.setAmount(1);

        BuyBook buyBook = BuyBookMapper.buyBookPostToBuyBook(buyBookPost);

        Assertions.assertEquals(buyBook.getUserId(), buyBookPost.getUserId());
        Assertions.assertEquals(buyBook.getBookId(), buyBookPost.getBookId());
        Assertions.assertEquals(buyBook.getAmount(), buyBookPost.getAmount());
    }

    @Test
    void buyBookDeleteToBuyBook() {
        BuyBookDelete buyBookDelete = new BuyBookDelete();
        buyBookDelete.setBuyBookId(1);

        BuyBook buyBook = BuyBookMapper.buyBookDeleteToBuyBook(buyBookDelete);

        Assertions.assertEquals(buyBook.getBuyBookId(), buyBookDelete.getBuyBookId());
    }
}