package Modules.Utilities;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics2D;

import Modules.Infos.Towers.TowerInfos;
import Modules.Infos.Towers.TowerInfos.TowerInfo;

public class Loader extends JPanel {
    private final JPanel gamePanel;

    public Loader(JPanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public Void getLoadScreen(Graphics2D g2d) {
        TowerInfos towerInfos = new TowerInfos();

        int width = gamePanel.getWidth()/4, height = gamePanel.getWidth()/4;
        int x = (gamePanel.getWidth() - width) / 2, y = (int) ((gamePanel.getHeight() - height) / 3.5);

        TowerInfo Test = towerInfos.getTower("Test",  x, y, width, height);
        towerInfos.draw(Test, g2d);

        gamePanel.setBackground(new Color(135, 206, 235));

        return null;
    }
}
