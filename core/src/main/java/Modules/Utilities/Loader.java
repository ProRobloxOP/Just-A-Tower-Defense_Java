package Modules.Utilities;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.*;

import Modules.Infos.Towers.TowerInfos;
import Modules.Infos.Towers.TowerInfos.TowerInfo;

import io.github.justatowerdefense_java.Main;

import java.util.HashMap;

public class Loader implements Screen {
    private final Texture backdrop = new Texture("Images/Backdrops/Loading.png");
    private HashMap<String, Runnable> loadingRunnables;
    private TowerInfo showcaseTower;

    private final float x, y;
    private final Main mainGame;

    private StretchViewport backdropViewport;
    private SpriteBatch backdropBatch;
    private Sprite backdropSprite;

    private FitViewport towerViewport;
    private SpriteBatch towerBatch;

    public Loader(final Main mainGame) {
        Viewport mainViewport = mainGame.mainViewpoint;
        float screenWidth = mainViewport.getWorldWidth();
        float screenHeight = mainViewport.getWorldHeight();

        x = screenWidth/2;
        y = screenHeight/2;

        this.mainGame = mainGame;
    }

    public void addLoadingTask(String id, Runnable runnable) {
        //loadingRunnables.put(id, runnable);
        runnable.run();
    }

    @Override
    public void show() {
        loadingRunnables = new HashMap<>();

        addLoadingTask("Backdrop", this::loadBackdrop);
        addLoadingTask("ShowcaseTower", this::loadShowcaseTower);
    }

    @Override
    public void render(float delta) {
        if (showcaseTower == null){
            System.out.println("Loader is not loaded!");
            return;
        }

        renderBackdrop();
        renderTower();
    }

    @Override
    public void resize(int width, int height) {
        backdropViewport.update(width, height, true);
        towerViewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void loadBackdrop(){
        Viewport mainViewport = mainGame.mainViewpoint;

        backdropViewport = new StretchViewport(mainViewport.getWorldWidth(), mainViewport.getWorldHeight());
        backdropBatch = new SpriteBatch();

        backdropSprite = new Sprite(backdrop);
        backdropSprite.setSize(mainViewport.getWorldWidth(), mainViewport.getWorldHeight());
    }

    private void loadShowcaseTower(){
        TowerInfos towerInfos = new TowerInfos();
        Viewport mainViewport = mainGame.mainViewpoint;

        towerViewport = new FitViewport(mainViewport.getWorldWidth(), mainViewport.getWorldHeight());
        towerBatch = new SpriteBatch();
        showcaseTower = towerInfos.getTower("Test", (int) x, (int) (y*1.25), 0.3f);
        showcaseTower.loadSprite();
    }

    private void renderBackdrop() {
        backdropViewport.apply();
        backdropBatch.setProjectionMatrix(backdropViewport.getCamera().combined);
        backdropBatch.begin();

        backdropSprite.draw(backdropBatch);

        backdropBatch.end();
    }

    private void renderTower() {
        towerViewport.apply();
        towerBatch.setProjectionMatrix(towerViewport.getCamera().combined);
        towerBatch.begin();

        showcaseTower.getSprite().draw(towerBatch);
        towerBatch.end();
    }
}
