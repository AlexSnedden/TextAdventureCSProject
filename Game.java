import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Game {
    private HashMap<String, State> states;
    private HashMap<String, Input> inputs;
    private HashMap<String, Boolean> agent;
    private String initialState;
    private String currentState;
    private String[] stateIds, inputIds;
    private String opening;

    public Game(HashMap<String, State> states, HashMap<String, Input> inputs, String initialState, String initialOpening) {
        this.states = states;
        this.inputs = inputs;
        this.agent = new HashMap<>();
        this.initialState = initialState;
        this.currentState = initialState;
        Object[] stateKeySet = states.keySet().toArray();
        Object[] inputKeySet = inputs.keySet().toArray();
        stateIds = Arrays.copyOf(stateKeySet, stateKeySet.length, String[].class);
        inputIds = Arrays.copyOf(inputKeySet, inputKeySet.length, String[].class);
        opening = initialOpening;
    }

    public void setStates(HashMap<String, State> states) {
        this.states = states;
    }
    public void setInputs(HashMap<String, Input> inputs) {
        this.inputs = inputs;
    }

    public Input[] getCurrentInputs() {
        ArrayList<Input> inputs = new ArrayList<>();
        for(String inputId: inputIds) {
            Input input = this.inputs.get(inputId);
            if(input.getConditions().containsKey(currentState)) {
                inputs.add(input);
            }
        }
        Object[] inputsObjs = inputs.toArray();
        return Arrays.copyOf(inputsObjs, inputsObjs.length, Input[].class);
    }
    public void setCurrentState(String stateID) { currentState = stateID; }
    public State getCurrentState() {
        return states.get(currentState);
    }
    public String getOpening() {
        return opening;
    }
}
