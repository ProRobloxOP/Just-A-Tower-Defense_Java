package Modules.Systems;

import Modules.Tools.SpriteMethods;
import Modules.Tools.SpriteMethods.InteractableSprites;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Map;

public class GameMaps {
    private final static String mapTexturePath = "Images/Maps";
    private static StretchViewport mapViewport;
    private static SpriteBatch mapBatch;

    public interface GameMap {
        String name = "";
        Bezier<Vector2> path = new Bezier<>();
        Vector2[] waypoints = new Vector2[]{};

        Bezier<Vector2> getPath();
        Vector2[] getWaypoints();

        void render(SpriteBatch spriteBatch);
        void load(Viewport mainViewport);
        void loadAssets(Viewport mainViewport);
    }

    public static GameMap getMap(String name) {
        Map<String, GameMap> mapClasses = Map.ofEntries(
          Map.entry("Graveyard", new Graveyard())
        );

        return mapClasses.get(name);
    }

    private final static class Graveyard implements GameMap {
        private String name = "Graveyard";
        private Bezier<Vector2> path;
        private Vector2[] waypoints;

        Sprite backgroundPng, pathPng, riverPng, basePng;

        public Graveyard() {}

        @Override
        public Bezier<Vector2> getPath() {
            return path;
        }

        @Override
        public Vector2[] getWaypoints() {
            return waypoints;
        }

        @Override
        public void render(SpriteBatch spriteBatch) {
            backgroundPng.draw(spriteBatch);
            riverPng.draw(spriteBatch);
            pathPng.draw(spriteBatch);
            basePng.draw(spriteBatch);
        }

        public String getName() {
            return name;
        }

        //General Map Load Method
        @Override
        public void load(Viewport mainViewport) {
            loadAssets(mainViewport);
        }

        @Override
        public void loadAssets(Viewport mainViewport) {
            String texturePaths = mapTexturePath + "/Graveyard/";
            backgroundPng = new Sprite(new Texture(texturePaths + "Background.png"));
            pathPng = new Sprite(new Texture(texturePaths + "Path.png"));
            riverPng = new Sprite(new Texture(texturePaths + "River.png"));
            basePng = new Sprite(new Texture(texturePaths + "Base.png"));

            backgroundPng.setSize(backgroundPng.getWidth()*1.007f, backgroundPng.getHeight());
            riverPng.setSize(riverPng.getWidth()*2, riverPng.getHeight());

            SpriteMethods.setPosition(backgroundPng, mainViewport.getWorldWidth()/2, mainViewport.getWorldHeight()/2);
            SpriteMethods.setPosition(riverPng, mainViewport.getWorldWidth()/2, mainViewport.getWorldHeight()/2);
            SpriteMethods.setPosition(pathPng, mainViewport.getWorldWidth()/2, mainViewport.getWorldHeight()/2);
            SpriteMethods.setPosition(basePng, mainViewport.getWorldWidth()/5, mainViewport.getWorldHeight()/4);

            InteractableSprites.add(basePng);
        }
    }
}
