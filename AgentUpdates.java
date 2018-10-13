import java.util.Arrays;
import java.util.HashMap;

public class AgentUpdates {
    private HashMap<String, Boolean> updates;
    public AgentUpdates() {
        updates = new HashMap<>();
    }
    public void addUpdate(String updateName, boolean val) {
        updates.put(updateName, val);
    }
    public Boolean getUpdateVal(String updateName) {
        if(updates.containsKey(updateName)) {
            return updates.get(updateName);
        }
        return null;
    }
    public String[] getUpdatedStateNames() {
        Object[] o = updates.keySet().toArray();
        return Arrays.copyOf(o, o.length, String[].class);
    }
}
