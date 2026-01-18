package Modules.Utilities.Screens;

import Modules.Tools.Tuples.Pair;
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

import java.util.Vector;

public class Loader implements Screen {
    private final Texture backdrop = new Texture("Images/Backdrops/Loading.png");
    private Vector<Pair<String, Runnable>> loadingRunnables;
    private BattleScreen battleScreen;
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

    private boolean loaded;

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
        loadingRunnables.add(new Pair<>(id, runnable));
    }

    //Preloads assets (e.g. sprites, text, etc.) to minimize runtime lag.
    @Override
    public void show() {
        battleScreen = new BattleScreen(this);
        loadingRunnables = new Vector<>();
        loaded = false;

        this.loadBackdrop();
        this.loadLoadingFont();
        this.loadShowcaseTower();

        addLoadingTask("UserData", UserData::load);
        addLoadingTask("BattleScreen", battleScreen::load);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        onInput();

        renderBackdrop();
        renderTower();
        beginLoading();
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
        if (Gdx.input.isTouched()) { mainGame.setScreen(battleScreen); }
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

    private void setLoadingFont(String text){
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(loadingFont, text);

        labelViewport.apply();
        labelBatch.setProjectionMatrix(labelViewport.getCamera().combined);
        labelBatch.begin();

        loadingFont.draw(labelBatch, text, x - glyphLayout.width/2, y/2);

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

    //Loads all assets in Runnables
    private void beginLoading() {
        Thread loadingThread;

        if (loaded) {
            setLoadingFont("Touch To Start!");
            return;
        }

        for (int i = 0; i < loadingRunnables.size(); i++) {
            Pair<String, Runnable> loader = loadingRunnables.get(i);
            String loaderName = loader.first;
            Runnable runnable = loader.second;

            loadingThread = new Thread(runnable);
            loadingThread.run();
            setLoadingFont("Loading " + loaderName + "...");

            try {
                loadingThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        loaded = true;
    }
}
