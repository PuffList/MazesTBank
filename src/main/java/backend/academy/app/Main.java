package backend.academy.app;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {

    public static void main(String[] args) {
        MazeApp game = new MazeApp();
        game.startGame();
    }
}
