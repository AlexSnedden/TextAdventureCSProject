import java.util.Arrays;
import java.util.HashMap;

public class Input {
    private String ID;
    private String display;
    private String newStateID;
    // does this link just loop back to the same state?
    private boolean loopAround;
    private String loopAroundDisplay;
    private HashMap<String, HashMap<String, Boolean>> conditions;

    public String getLoopAroundDisplay() {
        return loopAroundDisplay;
    }

    public void setLoopAroundDisplay(String loopAroundDisplay) {
        this.loopAroundDisplay = loopAroundDisplay;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDisplay() {
        return display;
    }

    public String getNewStateID() {
        return newStateID;
    }

    public void setNewStateID(String newStateID) {
        this.newStateID = newStateID;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public void setLoopAround(boolean val) { this.loopAround = val; }
    public boolean getLoopAround() { return loopAround; }

    public HashMap<String, HashMap<String, Boolean>> getConditions() { return conditions; }
    public Boolean getConditions(String state, String condition) {
        if(conditions.containsKey(state)) {
            if(conditions.get(state).containsKey(condition)) {
                return conditions.get(state).get(condition);
            }
        }
        return null;
    }
    public String[] getConditionNames(String state) {
        if(conditions.containsKey(state)) {
            Object[] objA = conditions.get(state).keySet().toArray();
            return Arrays.copyOf(objA, objA.length, String[].class);
        } else {
            return new String[0];
        }
    }

    public void setConditions(HashMap<String, HashMap<String, Boolean>> conditions) {
        this.conditions = conditions;
    }



}
