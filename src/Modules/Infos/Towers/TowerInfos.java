package Modules.Infos.Towers;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

import java.awt.Color;
import java.awt.Graphics2D;

public class TowerInfos {
    public interface TowerInfo {
        String getName();
        UUID getUuid();
        HashMap<String, Double> getStats();

        Runnable getGuiCreator(Graphics2D g2d);
    }

    public TowerInfos(){}

    public TowerInfo getTower(String name, int x, int y, int width, int height){
        Map<String, TowerInfo> towerClasses = Map.ofEntries(
                Map.entry("Test", new Test(x, y, width, height))
        );

        return towerClasses.get(name);
    }

    public void draw(TowerInfo towerInfo, Graphics2D g2d) {
        Runnable guiCreator = towerInfo.getGuiCreator(g2d);
        guiCreator.run();
    }

    private static class Test implements TowerInfo {
        private final String name;
        private final UUID uuid;
        private final HashMap<String, Double> stats;

        private final int x, y;
        private final int width, height;

        public Test(int x, int y, int width, int height){
            name = "Test";
            uuid = UUID.randomUUID();
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;

            stats = new HashMap<>();
            setStats();
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public UUID getUuid() {
            return uuid;
        }

        @Override
        public HashMap<String, Double> getStats() {
            return stats;
        }

        @Override
        public Runnable getGuiCreator(Graphics2D g2d) {
            return () -> {
                g2d.setColor(new Color(48, 92, 222));
                g2d.fillOval(x, y, width, height);
            };
        }

        private void setStats() {
            stats.put("Damage", 5.0);
            stats.put("Cooldown", 0.3);
            stats.put("Range", 10.0);
        }
    }
}
