import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GameController {
    private String saveFile;
    private Game game;
    private GameAgent agent;
    private GameView gameView;
    private Scanner scanner;
    public GameController(Game g, GameAgent gameAgent, String sFile) {
        game = g;
        agent = gameAgent;
        saveFile = sFile;
    }
    public void launch() {
        scanner = new Scanner(System.in);
        gameView = new GameView();
        if(saveFile != null) {
            gameView.saveGameInstructionsMessage();
        }
        gameView.displayString(game.getOpening());
        while(true) {
            State currentState = game.getCurrentState();
            Input[] currentInputs = game.getCurrentInputs();
            AgentUpdates updates = currentState.getAgentUpdates();
            for(String update: updates.getUpdatedStateNames()) {
                agent.setAgentAttribute(update, updates.getUpdateVal(update));
            }
            ArrayList<Input> inputs = new ArrayList<>();
            gameView.displayState(currentState);
            for(int i = 0; i < currentInputs.length; i++) {
                Input input = currentInputs[i];
                String[] conditionNames = input.getConditionNames(currentState.getId());
                boolean passedConditions = true;
                if (conditionNames.length > 0) {
                    int idx = 0;
                    while (passedConditions && idx < conditionNames.length) {
                        int ret = agent.getAgentAttribute(conditionNames[idx]);
                        boolean bool;
                        if (ret > -1) {
                            bool = (ret == 1);
                        } else {
                            // if agent doesn't have this key, default it to false.
                            bool = false;
                        }

                        if (bool != input.getConditions(currentState.getId(), conditionNames[idx])) {
                            passedConditions = false;
                        }
                        idx++;
                    }
                }

                if (passedConditions) {
                        inputs.add(input);
                }
            }
            if(inputs.size() > 0) {
                gameView.displayInputs(inputs);
                int choice = getNextIntAndHandleSaveRequests(inputs.size());
                Input chosenInput = inputs.get(choice-1);
                while(chosenInput.getLoopAround()) {
                    gameView.displayString(chosenInput.getLoopAroundDisplay());
                    gameView.displayInputs(inputs);
                    choice = getNextIntAndHandleSaveRequests(inputs.size());
                    chosenInput = inputs.get(choice-1);
                }
                game.setCurrentState(chosenInput.getNewStateID());

            } else {
                gameView.die();
                System.exit(0);
            }
        }
    }

    private int getNextIntAndHandleSaveRequests(int numInputs) {
        while(true) {
            gameView.enterInputMessage();
            try {
                int choice = scanner.nextInt();
                if(choice == 0) {
                    gameView.saveGameMessage();
                    saveGame();
                } else if(choice < 0 || choice > numInputs) {
                    gameView.giveOutOfBoundsIntegerError();
                } else {
                    return choice;
                }
            } catch (InputMismatchException e) {
                gameView.giveNonIntegerInputError();
                scanner.nextLine();
            }
        }
    }

    private void saveGame() {
        if(saveFile == null) {
            gameView.noSaveFileMessage();
            return;
        }
        FileSaverController fsc = new FileSaverController(agent, game, saveFile);
        fsc.saveGameState();
    }
}
