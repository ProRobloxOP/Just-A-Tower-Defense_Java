package Modules.Utilities;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.util.function.Function;

public class GamePanel extends JPanel {
    private Function<Graphics2D, Void> paintThread;

    public GamePanel(int baseRowRes, int baseColumnRes) {
        setPreferredSize(new Dimension(baseRowRes, baseColumnRes));
        setDoubleBuffered(true);
    }

    public void setPaintThread(Function<Graphics2D, Void> paintThread) {
        this.paintThread = paintThread;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (paintThread == null){ return; }

        Graphics2D g2d = (Graphics2D) g;
        paintThread.apply(g2d);

        g2d.dispose();
    }
}
