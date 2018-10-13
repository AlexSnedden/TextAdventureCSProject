import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*
Saved File format:
<lastStateID>
<agentCondition1 true/false>
....
<agentConditionN true/false>
 */

public class FileSaverController {
    GameAgent agent;
    Game game;
    String fileName;

    public FileSaverController(GameAgent gameAgent, Game gameToSave, String fName) {
        agent = gameAgent;
        game = gameToSave;
        fileName = fName;
    }
    public FileSaverController(String fName) {
        fileName = fName;
    }

    public void saveGameState() {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        pw.println(game.getCurrentState().getId());
        String[] keys = agent.getKeys();
        for (String key : keys) {
            pw.print(String.format("%s %b", key, agent.getAgentAttribute(key)));
        }
        pw.close();
    }

    public String getRevivedState() {
        try {
            BufferedReader bf = new BufferedReader(new FileReader(fileName));
            String stateID = bf.readLine();
            return stateID;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public GameAgent getRevivedAgent (){
        try {
            GameAgent gameAgent = new GameAgent();
            FileReader fr = null;
            fr = new FileReader(fileName);
            BufferedReader bf = new BufferedReader(fr);
            String nextLine = "";
            // get rid of first state line
            bf.readLine();
            nextLine = bf.readLine();
            while(nextLine != null) {
                String[] tokens = nextLine.split(" ");
                gameAgent.setAgentAttribute(tokens[0], Boolean.parseBoolean(tokens[1]));
                nextLine = bf.readLine();
            }
            return gameAgent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

