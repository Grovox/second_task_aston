package validators;

import junit.framework.Assert;
import model.User;
import org.junit.jupiter.api.Test;

class JsonValidatorTest {

    @Test
    void validJsonToClass(){
        String json = "{\n" +
                "        \"userId\":1,\n" +
                "        \"name\": \"jack\",\n" +
                "        \"age\": 22\n" +
                "    }";

        boolean ans = JsonValidator.validJsonToClass(json, User.class);

        Assert.assertEquals(ans, true);
    }

    @Test
    void validJsonToClassBed(){
        String json = "{\n" +
                "        \"authorId\":1,\n" +
                "        \"name\": \"jack\",\n" +
                "    }";

        boolean ans = JsonValidator.validJsonToClass(json, User.class);

        Assert.assertEquals(ans, false);
    }
}