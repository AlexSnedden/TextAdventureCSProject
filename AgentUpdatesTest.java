import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgentUpdatesTest {
    @Test
    void shouldReturnNullForNonexistentUpdate() {
        AgentUpdates ag = new AgentUpdates();
        assertEquals(null, ag.getUpdateVal("nonexistent"));

    }
}