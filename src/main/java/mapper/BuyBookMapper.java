package mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.BuyBookDelete;
import dto.BuyBookPost;
import model.BuyBook;

import java.util.List;

public class BuyBookMapper {
    private static ObjectMapper objectMapper = new ObjectMapper();

    private BuyBookMapper() {
    }

    public static String allBuyBooksToJsonString(List<BuyBook> buyBooks) {
        try {
            return objectMapper.writeValueAsString(buyBooks);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static BuyBook stringJsonToBuyBook(String json) {
        try {
            return objectMapper.readValue(json, BuyBook.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static BuyBookPost stringJsonToBuyBookPost(String json) {
        try {
            return objectMapper.readValue(json, BuyBookPost.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static BuyBookDelete stringJsonToBuyBookDelete(String json) {
        try {
            return objectMapper.readValue(json, BuyBookDelete.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
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
