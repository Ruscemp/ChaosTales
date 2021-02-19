package game;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SaveLoadEngine {

    static String[] saveInfo = new String[3];

    private static void getInfo(Game game){
        if (game.checkpoint == null) {
            saveInfo[0] = "null";
        }else {
            saveInfo[0] = game.checkpoint.toString();
        }
        saveInfo[1] = String.valueOf(game.points);
        saveInfo[2] = convertCharactersToSaveData(game.characters);
    }
    private static void setInfo(Game game){
        game.checkpoint = convertSaveDataToGameCheckpoint(saveInfo[0]);
        game.points = Integer.parseInt(saveInfo[1]);
        game.characters = convertSaveDataToCharacters(saveInfo[2]);
    }
    /** Convert Methods **/
    private static Adventure convertSaveDataToGameCheckpoint(String saveData){
        String[] str = saveData.split("/");
        return new Adventure(str[0], str[1], Integer.parseInt(str[2]),
                Integer.parseInt(str[3]), Integer.parseInt(str[4]), Integer.parseInt(str[5]),
                Integer.parseInt(str[6]), Integer.parseInt(str[7]), convertSaveDataToCharacter(str[8]));
    }
    private static String convertCharactersToSaveData(ArrayList<Character> characters){
        StringBuilder sbf = new StringBuilder();
        for (Character c : characters){
            sbf.append(c).append("/");
        }
        return sbf.toString();
    }
    private static ArrayList<Character> convertSaveDataToCharacters(String saveData){
        String[] str = saveData.split("/");
        ArrayList<Character> array = new ArrayList<>();
        System.out.println("-----------");
        for (String s : str) {
            array.add(convertSaveDataToCharacter(s)); //Convert all info into a new character
        }
        return array;
    }
    private static Character convertSaveDataToCharacter(String saveData){
        String[] str1 = saveData.split("\\|"); //Get character into an array
        int[] in = Arrays.stream(str1[5].split("#")).mapToInt(Integer::parseInt).toArray(); //Get characters stats into an array
        String[] ab = str1[6].split("\\$");               // Get
        ArrayList<Abilities> abilities = new ArrayList<>();    // characters
        for (String a : ab) {                                 // abilities
            abilities.add(Abilities.valueOf(a));             // into an
        }                                                   // array
        String[] si = str1[7].split("%");       // Get
        ArrayList<Items> items = new ArrayList<>();  // characters
        for (String a : si) {                       // abilities
            items.add(Items.valueOf(a));           // into an
        }                                         // array
        return new Character(str1[0], str1[1], str1[2], str1[3], str1[4], in, abilities, items); // Return Character
    }

    private static int getSaveFile(boolean save){
        String[] str = new String[6];
        for (int i = 0; i<5;i++){
            if (new File("saveFile_"+(i+1)+".txt").exists()){
                if (save){
                    str[i] = "Replace saveFile_"+(i+1)+"?";
                }else {
                    str[i] = "Load from saveFile_"+(i+1)+"?";
                }
            }else {
                if (save){
                    str[i] = "Save in Empty slot "+(i+1)+"!";
                }else {
                    str[i] = "Slot "+(i+1)+" is empty!";
                }
            }
        }
        if (save){
            str[5] = "To which slot do you want to Save?";
        }else {
            str[5] = "From which slot do you want to Load?";
        }
        int i = Message.questionI(Message.message(str[5]+"\n",
                "1: "+str[0],
                "2: "+str[1],
                "3: "+str[2],
                "4: "+str[3],
                "5: "+str[4],
                "6: Back"));
        if (!save && str[(i-1)].contains("empty")){
            return -1;
        }else {
            return i;
        }
    }
    /** Save Method**/
    public static void save(Game game) {
        int i = getSaveFile(true);
        if (i != 6){
            File outputFile;
            BufferedWriter outputWriter;

            try {
                outputFile = new File("saveFile_"+i+".txt");
                outputWriter = new BufferedWriter(new FileWriter(outputFile));

                getInfo(game);
                for (String s : saveInfo) {
                    outputWriter.write(s + "\n");
                }
                outputWriter.close();

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    /** Load Method **/
    public static void load(Game game) {
        int i;
        do {
            i = getSaveFile(false);
            if (i == -1) {
                Message.questionS("You can't load from empty slot! \nPress 'Enter' to try again!");
            }else if (i != 6) {
                File inputFile;
                BufferedReader inputReader;
                try {
                    inputFile = new File("saveFile_"+i+".txt");
                    if (inputFile.exists()){
                        inputReader = new BufferedReader(new FileReader(inputFile));

                        for (int x = 0; x< saveInfo.length; x++){
                            saveInfo[x] = inputReader.readLine();
                        }
                        inputReader.close();
                        setInfo(game);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }while (i == -1);
    }
}