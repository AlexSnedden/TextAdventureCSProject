import java.util.ArrayList;

public class GameView {
    private static String SAVE_GAME_MESSAGE = "saving game...";
    private static String NON_INTEGER_INPUT_ERROR_MESSAGE = "enter numeric input";
    private static String OUT_OF_BOUNDS_INTEGER_ERROR_MESSAGE = "please choose one of the inputs";
    private static String GAME_OVER_MESSAGE = "*********** GAME OVER ***********";
    private static String ENTER_INPUT_MESSAGE = "Enter your input:> ";
    private static String NO_SAVE_FILE_MESSAGE = "Cannot save game. No save file was selected";
    private static String SAVE_GAME_INSTRUCTION = "You can save your game by entering 0 when prompted for input";
    public void displayState(State state) {
        System.out.println(String.format("%s\n", state.getDisplay()));
    }
    private String paddedString(String string, int size) {
        if(string.length() == size) {
            return string;
        } else {
            return paddedString(" " + string, size);
        }
    }
    private String[] getRightJustifiedInputDisplays(ArrayList<Input> inputs) {
        // find the largest of the input displays
        String[] newDisplays = new String[inputs.size()];
        int largestIdx = 0;
        for(int i = 0; i < inputs.size(); i++) {
            if(inputs.get(i).getDisplay().length() > inputs.get(largestIdx).getDisplay().length()) {
                largestIdx = i;
            }
        }
        int size = inputs.get(largestIdx).getDisplay().length();
        for(int i = 0; i < inputs.size(); i++) {
            newDisplays[i] = paddedString(inputs.get(i).getDisplay(), size);
        }
        return newDisplays;
    }
    public void displayInputs(ArrayList<Input> inputs) {
        String[] newDisplays = getRightJustifiedInputDisplays(inputs);
        for(int i=0; i < newDisplays.length; i++) {
            System.out.println(String.format("%d*  %s", i+1, newDisplays[i]));
        }
        System.out.println();
    }
    public void displayString(String string) {
        System.out.println(string);
    }
    public void saveGameMessage() {
        System.out.println(SAVE_GAME_MESSAGE);
    }
    public void die() {
        System.out.println(GAME_OVER_MESSAGE);
    }
    public void giveNonIntegerInputError() {
        System.out.println(NON_INTEGER_INPUT_ERROR_MESSAGE);
    }
    public void giveOutOfBoundsIntegerError() {
        System.out.println(OUT_OF_BOUNDS_INTEGER_ERROR_MESSAGE);
    }
    public void enterInputMessage() {
        System.out.print(ENTER_INPUT_MESSAGE);
    }
    public void noSaveFileMessage() {
        System.out.println(NO_SAVE_FILE_MESSAGE);
    }
    public void saveGameInstructionsMessage() {
        System.out.println(SAVE_GAME_INSTRUCTION);
    }
}
