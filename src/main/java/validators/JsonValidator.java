package validators;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonValidator {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static boolean validJsonToClass(String jsonStr, Class<?> valueType){
        try {
            objectMapper.readValue(jsonStr, valueType);
            return true;
        } catch (JsonParseException | JsonMappingException e) {
            return false;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
