import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import Modules.Utilities.*;

public class Main {
    private static final int rowTileSize = 16;
    private static final int columnTileSize = 9;

    private static final int baseRowRes = 960;
    private static final int baseColumnRes = 540;

    private static final int rowTileCount = baseRowRes/rowTileSize;
    private static final int columnTileCount = baseColumnRes/columnTileSize;

    public static void print(String msg){
        System.out.print(msg);
    }
    public static void println(String msg){
        System.out.println(msg);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createGameWindow);
    }

    private static void createGameWindow(){
        JFrame window = new JFrame("JATD (Java)");
        GamePanel gamePanel = new GamePanel(baseRowRes, baseColumnRes);
        Loader loader = new Loader(gamePanel);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);

        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);

        gamePanel.setPaintThread(loader::getLoadScreen);

        window.setVisible(true);
    }
}
