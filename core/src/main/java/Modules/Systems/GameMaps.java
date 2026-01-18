package Modules.Systems;

import Modules.Utilities.Screens.Loader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Map;

public class GameMaps {
    private final static String mapTexturePath = "Images/Maps";

    public interface GameMap {
        Bezier<Vector2> path = new Bezier<>();
        Vector2[] waypoints = new Vector2[]{};

        Bezier<Vector2> getPath();
        Vector2[] getWaypoints();

        void render(SpriteBatch spriteBatch, Viewport viewport);
        void load();
        void loadAssets();
    }

    public static GameMap getMap(String name, Loader mainLoader) {
        Map<String, GameMap> mapClasses = Map.ofEntries(
          Map.entry("Graveyard", new Graveyard(mainLoader))
        );

        return mapClasses.get(name);
    }

    private final static class Graveyard implements GameMap {
        private String texturePaths = mapTexturePath + "/Graveyard";
        private Bezier<Vector2> path;
        private Vector2[] waypoints;

        private Viewport mainViewport;
        private Loader mainLoader;

        Sprite backgroundPng, pathPng, riverPng, basePng;

        public Graveyard(Loader mainLoader) {
            this.mainLoader = mainLoader;
            this.mainViewport = mainLoader.getMainViewport();
        }

        @Override
        public Bezier<Vector2> getPath() {
            return path;
        }

        @Override
        public Vector2[] getWaypoints() {
            return waypoints;
        }

        @Override
        public void render(SpriteBatch spriteBatch, Viewport viewport) {

        }

        //General Map Load Method
        @Override
        public void load() {
            mainLoader.addLoadingTask("Map: Graveyard", this::loadAssets);
        }

        @Override
        public void loadAssets() {
            backgroundPng = new Sprite(new Texture(texturePaths + "/Background.png"));
            pathPng = new Sprite(new Texture(texturePaths + "Path.png"));
            riverPng = new Sprite(new Texture(texturePaths + "River.png"));
            basePng = new Sprite(new Texture(texturePaths + "Base.png"));
        }
    }
}
