import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class InputTest {
    @Test
    void getConditionShouldReturnNullForNonexistentState() {
        Input input = new Input();
        input.setConditions(new HashMap<>());
        assertEquals(null, input.getConditions("nonexistent", "nonexistent"));
    }

    @Test
    void getConditionShouldReturnNullForNonexistentCondition() {
        Input input = new Input();
        HashMap<String, Boolean> hm1 = new HashMap<>();
        HashMap<String, HashMap<String, Boolean>> hm2 = new HashMap<>();
        hm2.put("state", hm1);
        input.setConditions(hm2);
        assertEquals(null, input.getConditions("state", "nonexistent"));
    }

}