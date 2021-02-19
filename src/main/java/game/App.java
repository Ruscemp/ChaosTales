package game;

import java.io.Console;

public class App {
    public static void main( String[] args ) {
        
        Game game = null;String str;
        while (true) {
            str = "1: Start ";
            if (game!=null){
                str += "old";
            }else {
                str += "new";
            }
            str +=" Game";
            switch (Game.Menu(str)){
                case 1:
                    if (game!=null){
                        game.startGame();
                    } else {
                        game = new Game();
                        game.newGame();
                    }
                    break;
                case 2:
                    game = Game.loadGame();
                    break;
                case 3:
                    game = new Game();
                    game.newGame();
                    break;
                case 4:
                    Game.options();
                    break;
                case 5:
                    Game.info();
                    break;
                case 6:
                    System.exit(0);
            }
        }
    }
}
