package io.github.justatowerdefense_java;

import Modules.Utilities.Screens.Loader;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.viewport.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public StretchViewport mainViewpoint;

    @Override
    public void create() {
        mainViewpoint = new StretchViewport(960, 540);
        this.setScreen(new Loader(this));
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        mainViewpoint.update(width, height, true);
    }

    @Override
    public void render() {
        super.render();
    }
}
