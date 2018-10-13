import javax.swing.*;
import java.io.File;
import java.util.Scanner;

public class Launcher {
    public static void main(String args[]) {
        String gameFile = promptForGameFile();
        TamlReader tamlReader = new TamlReader();
        Game g = tamlReader.readFile(gameFile);
        GameAgent ga;
        String loadDataFilePath = null;
        String dataFilePath = null;
        if(promptForLoadingSavedGame()) {
            loadDataFilePath = promptForFile();
            FileSaverController fsc = new FileSaverController(loadDataFilePath);
            ga = fsc.getRevivedAgent();
            g.setCurrentState(fsc.getRevivedState());
        } else {
            ga = new GameAgent();
        }
        if(promptForNewSaveFile()) {
            dataFilePath = promptForSaveFile();
        }
        GameController gc = new GameController(g, ga, dataFilePath);
        gc.launch();
    }

    private static String promptForFile() {
        JFileChooser jfc = new JFileChooser();
        int ret = jfc.showOpenDialog(null);
        if(ret == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        return null;
    }

    private static boolean promptForLoadingSavedGame() {
        System.out.println("Would you like to load a saved game? (Y/N)");
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String input = scanner.nextLine();
            if (input.equals("Y") || input.equals("y")) {
                return true;
            } else if (input.equals("N") || input.equals("n")) {
                return false;
            } else {
                System.out.println("Please enter Y or N.");
            }
        }
    }
    private static boolean promptForNewSaveFile() {
        System.out.println("Would you like to choose a file to save your game in? (Y/N)");
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String input = scanner.nextLine();
            if (input.equals("Y") || input.equals("y")) {
                return true;
            } else if (input.equals("N") || input.equals("n")) {
                return false;
            } else {
                System.out.println("Please enter Y or N.");
            }
        }
    }

    private static String promptForGameFile() {
        //return "C:\\Users\\Alex\\Desktop\\SimpleDemo.taml";
        System.out.println("Please choose a game file");
        String gameFile = promptForFile();
        Scanner scanner = new Scanner(System.in);
        while(gameFile == null) {
            System.out.println("You must choose a game file. Ok?");
            scanner.nextLine();
            gameFile = promptForFile();
        }
        return gameFile;
    }

    private static String promptForSaveFile() {
        System.out.println("Please choose a save file");
        String gameFile = promptForFile();
        Scanner scanner = new Scanner(System.in);
        while(gameFile == null) {
            System.out.println("You must choose a save file. Ok?");
            scanner.nextLine();
            gameFile = promptForFile();
        }
        return gameFile;
    }
}
