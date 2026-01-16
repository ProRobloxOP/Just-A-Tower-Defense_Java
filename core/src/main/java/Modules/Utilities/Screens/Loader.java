package Modules.Utilities.Screens;

import Modules.Utilities.UserData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;

import Modules.Tools.LoadFont;
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

    private FitViewport labelViewport;
    private SpriteBatch labelBatch;
    private BitmapFont loadingFont;

    public Loader(final Main mainGame) {
        Viewport mainViewport = mainGame.mainViewpoint;
        float screenWidth = mainViewport.getWorldWidth();
        float screenHeight = mainViewport.getWorldHeight();

        x = screenWidth/2;
        y = screenHeight/2;

        this.mainGame = mainGame;
    }

    public Main getMainGame() {
        return mainGame;
    }

    public Viewport getMainViewport(){
        return mainGame.mainViewpoint;
    }

    public void addLoadingTask(String id, Runnable runnable) {
        //loadingRunnables.put(id, runnable);
        runnable.run();
    }

    @Override
    public void show() {
        loadingRunnables = new HashMap<>();

        addLoadingTask("UserData", UserData::load);

        addLoadingTask("Backdrop", this::loadBackdrop);
        addLoadingTask("LoadingLabel", this::loadLoadingFont);
        addLoadingTask("ShowcaseTower", this::loadShowcaseTower);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        onInput();

        renderBackdrop();
        renderLoadingFont();
        renderTower();
    }

    @Override
    public void resize(int width, int height) {
        backdropViewport.update(width, height, true);
        towerViewport.update(width, height, true);
        labelViewport.update(width, height, true);
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
        backdropBatch.dispose();
        towerBatch.dispose();
        labelBatch.dispose();

        loadingFont.dispose();
    }

    private void onInput() {
        if (Gdx.input.isTouched()) {
            mainGame.setScreen(new BattleScreen(this));
        }
    }

    private void loadLoadingFont() {
        String FontPath = "ThaleahFat.ttf";
        Viewport mainViewport = mainGame.mainViewpoint;

        labelViewport = new FitViewport(mainViewport.getWorldWidth(), mainViewport.getWorldHeight());
        labelBatch = new SpriteBatch();
        loadingFont = LoadFont.generateFont(FontPath, 45);
        loadingFont.setColor(Color.BLACK);
    }

    private void loadBackdrop(){
        Viewport mainViewport = mainGame.mainViewpoint;

        backdropViewport = new StretchViewport(mainViewport.getWorldWidth(), mainViewport.getWorldHeight());
        backdropBatch = new SpriteBatch();
        backdropSprite = new Sprite(backdrop);
        backdropSprite.setSize(mainViewport.getWorldWidth(), mainViewport.getWorldHeight());
    }

    private void loadShowcaseTower(){
        Viewport mainViewport = mainGame.mainViewpoint;

        towerViewport = new FitViewport(mainViewport.getWorldWidth(), mainViewport.getWorldHeight());
        towerBatch = new SpriteBatch();
        showcaseTower = TowerInfos.get("Test", x, y*1.25f, 0.3f);
        showcaseTower.loadSprite();
    }

    private void renderLoadingFont(){
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(loadingFont, "Touch To Play!");

        labelViewport.apply();
        labelBatch.setProjectionMatrix(labelViewport.getCamera().combined);
        labelBatch.begin();

        loadingFont.draw(labelBatch, "Touch To Play!", x - glyphLayout.width/2, y/2);

        labelBatch.end();
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
