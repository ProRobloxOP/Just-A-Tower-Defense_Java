package io.github.justatowerdefense_java;

import Modules.Utilities.Screens.Loader;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.viewport.*;

/**
 * The class that is called by libGDX to run the game. Controls the mainViewport and creates the main Loader Handle.
 *
 * @author Tyson Yung
 */
public class Main extends Game {
    /**
     * Stores the mainViewport which can be used as a frame to hold the main window settings.
     */
    public StretchViewport mainViewpoint;

    /**
     * LibGDX calls this method, which creates the mainViewport instance.
     * <p>Also creates the main Loader and sets the screen to it.</p>
     */
    @Override
    public void create() {
        mainViewpoint = new StretchViewport(960, 540);
        this.setScreen(new Loader(this));
    }

    /**
     *LibGDX calls this method, which is called when the window is resized.
     *In return, all other sprites and graphics related to the mainViewport are also resized.
     *
     * @param width given width to resize by LibGDX
     * @param height given height to resize by LibGDX
     */
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        mainViewpoint.update(width, height, true);
    }

    /**
     * LibGDX calls this method, currently only allows the window to render.
     */
    @Override
    public void render() {
        super.render();
    }
}
