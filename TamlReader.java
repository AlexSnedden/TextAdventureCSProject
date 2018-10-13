import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;

public class TamlReader {
    private static String INITIAL_OPENING_FIELD = "initial opening";
    private static String STATES_FIELD = "states";
    private static String STATES_ID_FIELD = "ID";
    private static String STATE_DISPLAY_FIELD = "display";
    private static String STATE_AGENT_UPDATES_FIELD = "agent updates";
    private static String INPUTS_FIELD = "inputs";
    private static String INPUTS_ID_FIELD = "ID";
    private static String INPUTS_DISPLAY_FIELD = "display";
    private static String INPUTS_AFFECTED_STATES_ID = "affected states";
    private static String INPUTS_AFFECTED_STATES_STATE_ID = "state";
    private static String INPUTS_AFFECTED_STATES_CONDITIONS_FIELD = "conditions";
    private static String INPUTS_NEXT_STATE_ID = "new state ID";
    private static String INPUTS_LOOP_AROUND_FIELD = "loop around";
    private static String INPUTS_LOOP_AROUND_DISPLAY_FIELD = "loop around display";
    private static String INITIAL_STATE_FIELD = "initial state";

    public static Game readFile(String filePath) {
        String fileContents = null;
        try {
            fileContents = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        JSONObject configuration;
        try {
            configuration = new JSONObject(fileContents);
        } catch (JSONException e) {
            System.out.println(String.format("Json syntax error: %s", e.getMessage()));
            return null;
        }
        HashMap<String, State> states = new HashMap<>();
        JSONArray stateArray = null;
        try {
            stateArray = (JSONArray)configuration.get(STATES_FIELD);
        } catch (JSONException e) {
            System.out.println(String.format("Failed to read states from file: %s", e.getMessage()));
        }
        for(Object o: stateArray) {
            State newState = new State();
            JSONObject jsonState = (JSONObject) o;
            newState.setId(jsonState.getString(STATES_ID_FIELD));
            newState.setDisplay(jsonState.getString(STATE_DISPLAY_FIELD));
            if (jsonState.has(STATE_AGENT_UPDATES_FIELD)) {
                JSONArray agentUpdatesJson = jsonState.getJSONArray(STATE_AGENT_UPDATES_FIELD);
                AgentUpdates agentUpdates = new AgentUpdates();
                for(Object j: agentUpdatesJson) {
                    JSONObject agentUpdatesJsonObject = (JSONObject)j;
                    Iterator keys = agentUpdatesJsonObject.keys();
                    while (keys.hasNext()) {
                        String key = (String)keys.next();
                        agentUpdates.addUpdate(key, agentUpdatesJsonObject.getBoolean(key));
                    }
                }
                newState.setAgentUpdates(agentUpdates);
            }
            states.put(newState.getId(), newState);
        }
        HashMap<String, Input> inputs = new HashMap<>();
        JSONArray inputArray = null;
        try {
            inputArray = (JSONArray)configuration.get(INPUTS_FIELD);
        } catch (JSONException e) {
            System.out.println(String.format("Failed to read inputs from file: %s", e.getMessage()));
        }
        for(Object j: inputArray) {
            JSONObject jsonInput = (JSONObject)j;
            Input input = new Input();
            input.setID(jsonInput.getString(INPUTS_ID_FIELD));
            try {
                boolean loopAround = jsonInput.getBoolean(INPUTS_LOOP_AROUND_FIELD);
                if (loopAround) {
                    input.setLoopAround(jsonInput.getBoolean(INPUTS_LOOP_AROUND_FIELD));
                    input.setLoopAroundDisplay(jsonInput.getString(INPUTS_LOOP_AROUND_DISPLAY_FIELD));
                }
            } catch (Exception e) {
                /*if(input.getID().equals("TRY_LOCKED_DOOR")) {
                    e.printStackTrace();
                }*/
                input.setNewStateID(jsonInput.getString(INPUTS_NEXT_STATE_ID));
                input.setLoopAround(false);
            }
            input.setDisplay(jsonInput.getString(INPUTS_DISPLAY_FIELD));
            HashMap<String, HashMap<String, Boolean>> conditions = new HashMap<>();
            HashMap<String, Boolean> conditionMap;
            for(Object k: jsonInput.getJSONArray(INPUTS_AFFECTED_STATES_ID)) {
                conditionMap = new HashMap<>();
                JSONObject affectedState = (JSONObject)k;
                try {
                    JSONObject conditionVals = affectedState.getJSONObject(INPUTS_AFFECTED_STATES_CONDITIONS_FIELD);
                    Iterator conditionValsFields = conditionVals.keys();
                    while (conditionValsFields.hasNext()) {
                        String key = (String) conditionValsFields.next();
                        conditionMap.put(key, conditionVals.getBoolean(key));
                    }
                    conditions.put(affectedState.getString(INPUTS_AFFECTED_STATES_STATE_ID),
                            conditionMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            input.setConditions(conditions);
            inputs.put(input.getID(), input);
        }

        return new Game(states, inputs, configuration.getString(INITIAL_STATE_FIELD), configuration.getString(INITIAL_OPENING_FIELD));
    }
}
