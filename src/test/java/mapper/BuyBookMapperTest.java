package mapper;

import dto.BuyBookDelete;
import dto.BuyBookPost;
import model.BuyBook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class BuyBookMapperTest {

    @Test
    void allBuyBooksToJsonString() {
        BuyBook buyBook1 = new BuyBook();
        buyBook1.setBuyBookId(1);
        buyBook1.setBookId(1);
        buyBook1.setUserId(1);
        buyBook1.setPrice(1);
        buyBook1.setAmount(1);
        BuyBook buyBook2 = new BuyBook();
        buyBook2.setBuyBookId(2);
        buyBook2.setBookId(2);
        buyBook2.setUserId(2);
        buyBook2.setPrice(2);
        buyBook2.setPrice(2);
        List<BuyBook> buyBooks = new ArrayList<>();
        buyBooks.add(buyBook1);
        buyBooks.add(buyBook2);

        String jsonOut = BuyBookMapper.allBuyBooksToJsonString(buyBooks);

        String json = "[{\"buyBookId\":1,\"price\":1.0,\"amount\":1,\"userId\":1,\"bookId\":1},{\"buyBookId\":2,\"price\":2.0,\"amount\":0,\"userId\":2,\"bookId\":2}]";
        Assertions.assertEquals(jsonOut, json);
    }

    @Test
    void stringJsonToBuyBook() {
        String json = "{\"buyBookId\":1,\"price\":1.0,\"amount\":1,\"userId\":1,\"bookId\":1}";

        BuyBook buyBook = BuyBookMapper.stringJsonToBuyBook(json);

        Assertions.assertEquals(buyBook.getBuyBookId(), 1);
        Assertions.assertEquals(buyBook.getPrice(), 1);
        Assertions.assertEquals(buyBook.getAmount(), 1);
        Assertions.assertEquals(buyBook.getUserId(), 1);
        Assertions.assertEquals(buyBook.getBookId(), 1);
    }

    @Test
    void stringJsonToBuyBookPost() {
        String json = "{\"amount\":1,\"userId\":1,\"bookId\":1}";

        BuyBookPost buyBookPost = BuyBookMapper.stringJsonToBuyBookPost(json);

        Assertions.assertEquals(buyBookPost.getAmount(), 1);
        Assertions.assertEquals(buyBookPost.getUserId(), 1);
        Assertions.assertEquals(buyBookPost.getBookId(), 1);
    }

    @Test
    void stringJsonToBuyBookDelete() {
        String json = "{\"buyBookId\":1}";

        BuyBookDelete buyBookDelete = BuyBookMapper.stringJsonToBuyBookDelete(json);

        Assertions.assertEquals(buyBookDelete.getBuyBookId(), 1);
    }
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