package game;


import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class SaveLoadEngine {
    static Connection con;
    static ResultSet rs;
    static String q;
    static String[] saveInfo = new String[3];
    static PreparedStatement stmt;

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
    public static void save(Game game, boolean local) {
        if (local){
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
        }else {
            try {
                connect();
                String saveId;
                switch (Message.questionI(Message.message("","1: New Save","2: Overwrite/Update existing save","3: Delete existing save","4: abort","",""))){
                    case 1:
                        saveId = saveGameToDatabase(game);
                        saveGame(game, saveId);
                        Message.questionS("Your Save Code is: \n"+saveId+"\nPress Enter to continue");
                        break;
                    case 2:
                        saveId = Message.questionS("Input the Save Code of the save you want to Overwrite/Update:\n");
                        deleteSaveInDatabase(saveId);
                        saveGameToDatabase(game, saveId);
                        saveGame(game, saveId);
                        break;
                    case 3:
                        deleteSaveInDatabase();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private static void saveGame(Game game, String saveId) throws Exception {
        saveCharactersToDatabase(game, saveId);
        saveAdventureToDatabase(game.checkpoint, saveId);
    }
    private static String saveGameToDatabase(Game game) throws Exception {
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        return saveGameToDatabase(game, uuid);
    }
    private static String saveGameToDatabase(Game game, String uuid)throws Exception{
        q = "insert into game_data (id, points) " +
                "values (?, ?)";
        stmt = con.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, uuid);
        stmt.setInt(2, game.points);
        stmt.executeUpdate();
        stmt.close();
        return uuid;
    }

    private static void saveCharactersToDatabase(Game game, String game_id)throws Exception{
        for (Character c : game.characters){
            int id = 0;
            stmt = getCharacterSaveStatement(c, game_id);
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            if (rs.next()){
                id = rs.getInt(1);
            }

            stmt = getAbilitiesSaveStatement(c, id);
            stmt.executeUpdate();

            stmt = getItemsSaveStatement(c, id);
            stmt.executeUpdate();
            stmt.close();
        }
    }
    private static PreparedStatement getCharacterSaveStatement(Character c, String game_id) throws Exception{
        q = "insert into characters (name, gender, race, class, god, game_id, strength, dexterity, constitution, intelligence, wisdom, will, charisma, luck, magic)" +
                " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        stmt = con.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, c.name);
        stmt.setString(2, c.gender);
        stmt.setString(3, c.race);
        stmt.setString(4, c.role);
        stmt.setString(5, c.god);
        stmt.setString(6, game_id);
        for (int i = 0; i < 9; i++){
            stmt.setInt((i+7), c.stats[i]);
        }

        return stmt;
    }
    private static PreparedStatement getAbilitiesSaveStatement(Character c, int id) throws Exception{
        q = "insert into abilities (name, owner_id) " +
                "values (?, ?)";
        stmt = con.prepareStatement(q);
        for (Abilities a : c.abilitiesUnlocked){
            stmt.setString(1, a.name());
            stmt.setInt(2, id);
        }
        return stmt;
    }
    private static PreparedStatement getItemsSaveStatement(Character c, int id) throws Exception{
        q = "insert into items (name, owner_id) " +
                "values (?, ?)";
        stmt = con.prepareStatement(q);
        for (Items i : c.inventory){
            stmt.setString(1, i.name());
            stmt.setInt(2, id);
        }
        return stmt;
    }

    private static void saveAdventureToDatabase(Adventure a, String game_id)throws Exception{
        if (a!=null){
            int id = getCharacterForAdventure(a);
            q = "insert into adventure_data (questType, quest, questPlace, questTarget, progressCur, questProgressStep, progressMax, earnedPoints, curCharacter, game_id) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = con.prepareStatement(q);
            stmt.setString(1, a.QuestType);
            stmt.setString(2, a.Quest);
            stmt.setInt(3, a.QuestPlace);
            stmt.setInt(4, a.QuestTarget);
            stmt.setInt(5, a.progressCur);
            stmt.setInt(6, a.QuestProgressStep);
            stmt.setInt(7, a.progressMax);
            stmt.setInt(8, a.earnedPoints);
            stmt.setInt(9, id);
            stmt.setString(10, game_id);
            stmt.executeUpdate();
        }else {
            q = "delete from adventure_data where game_id = ?";
            stmt = con.prepareStatement(q);
            stmt.setString(1, game_id);
            stmt.execute();
        }
        stmt.close();
    }
    private static int getCharacterForAdventure(Adventure a)throws Exception{
        int id = 0;
        q = "select id from characters where name = ?";
        stmt = con.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, a.character.name);
        rs = stmt.executeQuery();
        if (rs.next()){
            id = rs.getInt(1);
        }
        stmt.close();
        return id;
    }
    /** Load Method **/
    public static void load(Game game, boolean local) {
        if (local){
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
        }else {
            try {
                connect();
                String saveId;
                switch (Message.questionI(Message.message("","1: Load with SaveCode","2: Delete the save","3: Abort","","",""))){
                    case 1:
                        saveId = Message.questionS("Input the Save Code of the save you want to load from:\n");
                        setInfoFromDatabase(game, saveId);
                        break;
                    case 2:
                        deleteSaveInDatabase();
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private static void setInfoFromDatabase(Game game, String saveId) throws Exception {
        q = "select * from game_data game\n" +
                "inner join characters on game.id = characters.game_id\n" +
                "left outer join adventure_data adventure on game.id = adventure.game_id " +
                "where game.id = ?";
        stmt = con.prepareStatement(q);
        stmt.setString(1, saveId);
        rs = stmt.executeQuery();
        ResultSet rs1;

        if (rs.next()){
            game.points = rs.getInt("points");

            ArrayList<Character> characters = new ArrayList<>();
            int charId;
            do{
                charId = rs.getInt("characters.id");
                int[] stats = new int[]{rs.getInt("strength"),
                        rs.getInt("dexterity"), rs.getInt("constitution"),
                        rs.getInt("intelligence"), rs.getInt("wisdom"), rs.getInt("will"),
                        rs.getInt("charisma"), rs.getInt("luck"), rs.getInt("magic")};

                ArrayList<Abilities> abilities = new ArrayList<>();
                q = "select * from abilities where owner_id = ?";
                stmt = con.prepareStatement(q);
                stmt.setInt(1, charId);
                rs1 = stmt.executeQuery();
                while (rs1.next()){
                    abilities.add(Abilities.valueOf(rs1.getString("name")));
                }

                ArrayList<Items> items = new ArrayList<>();
                q = "select * from items where owner_id = ?";
                stmt = con.prepareStatement(q);
                stmt.setInt(1, charId);
                rs1 = stmt.executeQuery();
                while (rs1.next()){
                    items.add(Items.valueOf(rs1.getString("name")));
                }

                characters.add(new Character(rs.getString("characters.name"),
                        rs.getString("gender"),
                        rs.getString("race"),
                        rs.getString("class"),
                        rs.getString("god"),
                        stats,
                        abilities,
                        items));

                if (rs.getInt("curCharacter")==charId){
                    game.checkpoint = new Adventure(rs.getString("questType"),
                            rs.getString("quest"), rs.getInt("questPlace"),
                            rs.getInt("questTarget"), rs.getInt("progressCur"),
                            rs.getInt("questProgressStep"), rs.getInt("progressMax"),
                            rs.getInt("earnedPoints"), characters.get(characters.size()-1));
                }
            }while (rs.next());
            game.characters = characters;
        }
    }
    private static void deleteSaveInDatabase() throws Exception{
        deleteSaveInDatabase(Message.questionS("Input the Save Code of the save you want to delete:\n"));
    }
    private static void deleteSaveInDatabase(String uuid) throws Exception{
        stmt = con.prepareStatement("delete from game_data where id = ?");
        stmt.setString(1, uuid);
        stmt.execute();
        stmt.close();
    }
    
    private static void connect() throws SQLException {
        String myUrl = "jdbc:mysql://localhost:3306/chaostales?useUnicode=true&serverTimezone=UTC";
        con = DriverManager.getConnection(
                myUrl,
                "root",
                "53644635");
    }
}