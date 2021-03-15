package game;

import java.util.ArrayList;
import java.util.Scanner;

public class Message {
    static StringBuilder sbf = new StringBuilder();

    public static String questionS(String str){
        System.out.println(str);
        return new Scanner(System.in).nextLine();
    }
    public static Integer questionI(StringBuilder sbf){
        int i;
        do {
            Scanner myScanner = new Scanner(System.in);
            System.out.println(sbf.toString());
            while (!myScanner.hasNextInt()) {
                myScanner.next();
                System.out.println("Input mismatch! Try to input a number! Example: 1");
            }
            i = myScanner.nextInt();
            if (i < 1 || i > 6){
                questionS("Wrong input! \nYou have only 6 options to choose from! \nYou must write 1 or 2 or 3 or 4 or 5 or 6 to answer the question! \nPress 'Enter' to try again!");
            }
        } while (i < 1 || i > 6);
        sbf.setLength(0);
        return i;
    }

    public static StringBuilder message(String str,String str1, String str2, String str3, String str4, String str5, String str6){
        sbf.setLength(0);
        sbf.append("\\n------------------------------------------------------------------\\n").append('\n');
        sbf.append(str).append('\n');
        sbf.append(str1).append('\n');
        sbf.append(str2).append('\n');
        sbf.append(str3).append('\n');
        sbf.append(str4).append('\n');
        sbf.append(str5).append('\n');
        sbf.append(str6).append('\n');
        sbf.append("\\n------------------------------------------------------------------\\n");
        return sbf;
    }
    public static String limit(int answers){
        sbf.setLength(0);
        sbf.append("There is only ").append(answers).append(" options in this question!").append("\n");
        sbf.append("Answer it with 1");
        for (int i = 1; i < answers; i++){
            sbf.append(" or ").append(i+1);
        }
        sbf.append("!").append("\n");
        sbf.append("Press enter to try again.");
        return sbf.toString();
    }

    static int getCharactersListMessage(ArrayList<Character> characters, int curPage, int pages, int mode){
        String str, str1,str2,str3,str4,str5, str6 = "6: Exit ";
        if (curPage == pages){
            str5 = "5: First page";
            if (mode == 1){
                str6 += "Appraisal";
            }else if(mode == 2){
                str6 += "Deleting";
            } else if (mode == 3){
                str6 += "Upgrading";
            } else if (mode == 4){
                str6 += "Selecting for Adventure";
            }
        } else {
            str5 = "5: "+characters.get(4+curPage*5).shortString();
            str6 = "6: Next page";
        }
        if (curPage*5<characters.size()){
            str1 = "1: "+characters.get(curPage * 5).shortString();
        }else {
            str1 = "";
        }
        if (1+curPage*5<characters.size()){
            str2 = "2: "+characters.get(1+curPage*5).shortString();
        }else {
            str2 = "";
        }
        if (2+curPage*5<characters.size()){
            str3 = "3: "+characters.get(2+curPage*5).shortString();
        }else {
            str3 = "";
        }
        if (3+curPage*5<characters.size()){
            str4 = "4: "+characters.get(3+curPage*5).shortString();
        }else {
            str4 = "";
        }
        str = "Which character do you wish to ";
        if (mode == 1){
            str += "Appraise?";
        } else if (mode == 2){
            str += "Delete?";
        } else if (mode == 3){
            str += "Upgrade?";
        } else if (mode == 4){
            str += "Select for Adventure?";
        }
        return Message.questionI(Message.message(str+"\n",
                str1,
                str2,
                str3,
                str4,
                str5,
                str6));
    }
}
