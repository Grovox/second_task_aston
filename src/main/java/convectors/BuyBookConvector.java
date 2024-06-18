package convectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.BuyBookDelete;
import dto.BuyBookPost;
import model.BuyBook;

import java.util.List;

public class BuyBookConvector {
    private static ObjectMapper objectMapper = new ObjectMapper();

    private BuyBookConvector() {
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
}
