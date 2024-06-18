package convectors;

import dto.BuyBookDelete;
import dto.BuyBookPost;
import model.BuyBook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class BuyBookConvectorTest {

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

        String jsonOut = BuyBookConvector.allBuyBooksToJsonString(buyBooks);

        String json = "[{\"buyBookId\":1,\"price\":1.0,\"amount\":1,\"userId\":1,\"bookId\":1},{\"buyBookId\":2,\"price\":2.0,\"amount\":0,\"userId\":2,\"bookId\":2}]";
        Assertions.assertEquals(jsonOut, json);
    }

    @Test
    void stringJsonToBuyBook() {
        String json = "{\"buyBookId\":1,\"price\":1.0,\"amount\":1,\"userId\":1,\"bookId\":1}";

        BuyBook buyBook = BuyBookConvector.stringJsonToBuyBook(json);

        Assertions.assertEquals(buyBook.getBuyBookId(), 1);
        Assertions.assertEquals(buyBook.getPrice(), 1);
        Assertions.assertEquals(buyBook.getAmount(), 1);
        Assertions.assertEquals(buyBook.getUserId(), 1);
        Assertions.assertEquals(buyBook.getBookId(), 1);
    }

    @Test
    void stringJsonToBuyBookPost() {
        String json = "{\"amount\":1,\"userId\":1,\"bookId\":1}";

        BuyBookPost buyBookPost = BuyBookConvector.stringJsonToBuyBookPost(json);

        Assertions.assertEquals(buyBookPost.getAmount(), 1);
        Assertions.assertEquals(buyBookPost.getUserId(), 1);
        Assertions.assertEquals(buyBookPost.getBookId(), 1);
    }

    @Test
    void stringJsonToBuyBookDelete() {
        String json = "{\"buyBookId\":1}";

        BuyBookDelete buyBookDelete = BuyBookConvector.stringJsonToBuyBookDelete(json);

        Assertions.assertEquals(buyBookDelete.getBuyBookId(), 1);
    }
}