public class Game {
    private int level;

    public static void main(String args[]) {
        GameGUI gui = new GameGUI();
        gui.display();
    }

    private void game() {
         level = 0;
    }

    public void nextLevel() {
        level += 1;
    }
}
