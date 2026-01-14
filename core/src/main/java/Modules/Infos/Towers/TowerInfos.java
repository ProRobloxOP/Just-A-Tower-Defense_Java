package Modules.Infos.Towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.HashMap;
import java.util.Map;

public class TowerInfos {
    public interface TowerInfo {
        HashMap<String, Double> getStats();
        String getTexturePath();
        Sprite getSprite();
        String getName();

        Runnable loadSprite();
        Runnable drawSprite(Batch batch);
    }

    public TowerInfos(){}

    public TowerInfo getTower(String name, int x, int y, float scale){
        Map<String, TowerInfo> towerClasses = Map.ofEntries(
                Map.entry("Test", new Test(x, y, scale))
        );

        return towerClasses.get(name);
    }

    // Tower Info Classes (All Tower Infos)
    private static class Test implements TowerInfo {
        private final String name;
        private final HashMap<String, Double> stats;

        private final int x, y;
        private final float scale;

        private final String texturePath = "Images/Towers/Cowboy_Tower.png";
        private Sprite sprite;
        private boolean notLoaded;

        public Test(int x, int y, float scale){
            name = "Test";
            this.x = x;
            this.y = y;
            this.scale = scale;

            stats = new HashMap<>();
            setStats();
        }

        @Override
        public String getTexturePath() {
            return texturePath;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public HashMap<String, Double> getStats() {
            return stats;
        }

        @Override
        public Sprite getSprite() {
            return sprite;
        }

        @Override
        public Runnable loadSprite() {
            Texture texture = new Texture(texturePath);
            sprite = new Sprite(texture);
            sprite.setSize(texture.getWidth()*scale, texture.getHeight()*scale);
            sprite.setCenter(x, y);
            sprite.setOriginCenter();

            return null;
        }

        @Override
        public Runnable drawSprite(Batch batch) {
            if (sprite == null) {
                if (notLoaded) {
                    return null;
                }

                System.out.println("Tower " + name + " sprite has not been loaded!");
                notLoaded = true;
                return null;
            }
            sprite.draw(batch);

            notLoaded = false;
            return null;
        }

        private void setStats() {
            stats.put("Damage", 5.0);
            stats.put("Cooldown", 0.3);
            stats.put("Range", 10.0);
        }
    }
}
