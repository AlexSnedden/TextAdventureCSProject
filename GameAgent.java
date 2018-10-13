import java.util.Arrays;
import java.util.HashMap;

public class GameAgent {
    public HashMap<String, Boolean> agentAttributes;
    public String[] getKeys() {
        Object[] obj = agentAttributes.keySet().toArray();
        return Arrays.copyOf(obj, obj.length, String[].class);
    }
    public GameAgent() {
        agentAttributes = new HashMap<>();
    }
    public int getAgentAttribute(String attribute) {
        if(agentAttributes.containsKey(attribute)) {
            return agentAttributes.get(attribute).equals(true) ? 1:0;
        } else {
            return -1;
        }
    }
    public void setAgentAttribute(String attribute, boolean val) {
        agentAttributes.put(attribute, val);
    }
}
