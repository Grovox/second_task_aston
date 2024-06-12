package convectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.BuyBookPost;
import model.BuyBook;

import java.util.List;

public class BuyBookConvector {
    private static ObjectMapper objectMapper = new ObjectMapper();

    private BuyBookConvector() {
    }

    public static String allBuyBooksToJsonString(List<BuyBook> buyBooks) throws JsonProcessingException {
        return objectMapper.writeValueAsString(buyBooks);
    }

    public static BuyBook stringJsonToBuyBook(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, BuyBook.class);
    }

    public static BuyBookPost stringJsonToBuyBookPost(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, BuyBookPost.class);
    }
}
