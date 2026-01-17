package Modules.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;

import java.awt.*;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SpriteMethods {
    public static class InteractableSprites {
        private static final Vector<Sprite> sprites = new Vector<>();

        public static Vector<Sprite> getSprites() {
            return sprites;
        }

        public static void add(Sprite sprite){
            sprites.add(sprite);
        }

        public static boolean hoverOnInteractable(Sprite sprite){
            boolean hovering = false;

            for (Sprite interactable : sprites) {
                if (interactable.getBoundingRectangle().overlaps(sprite.getBoundingRectangle())) {
                    hovering = true;
                }
            }

            return hovering;
        }
    }

    private static boolean isHoveredOn(Sprite sprite, Camera camera){
        Vector3 mouseVector = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        return sprite.getBoundingRectangle().contains(mouseVector.x, mouseVector.y);
    }

    public static void onClicked(Sprite sprite, Camera camera, Runnable runnable) {
        if (!isHoveredOn(sprite, camera) || !Gdx.input.isTouched()){ return; }
        runnable.run();
    }

    public static void onHover(Sprite sprite, Camera camera, Runnable runnable) {
        if (!isHoveredOn(sprite, camera) || Gdx.input.isTouched()) { return; }
        runnable.run();
    }

    public static void scaleInterpolate(Sprite sprite, float scale, float seconds) {
        sprite.setSize(sprite.getWidth()*scale, sprite.getHeight()*scale);
        SpriteMethods.setPosition(sprite, SpriteMethods.getX(sprite) - (sprite.getWidth()*(1f - 1f/scale))/2, SpriteMethods.getY(sprite) - (sprite.getHeight()*(1f - 1f/scale))/2);

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleWithFixedDelay(() -> {
            sprite.setSize(sprite.getWidth()/scale, sprite.getHeight()/scale);
            SpriteMethods.setPosition(sprite, SpriteMethods.getX(sprite) + (sprite.getWidth()*(scale - 1f))/2, SpriteMethods.getY(sprite) + (sprite.getHeight()*(scale - 1f))/2);

            executorService.shutdown();
        }, 0, (long) (seconds*1000), TimeUnit.MILLISECONDS);
    }

    public static float getX(Sprite sprite) {
        return sprite.getX() + sprite.getWidth()/2;
    }

    public static float getY(Sprite sprite) {
        return sprite.getY() + sprite.getHeight()/2;
    }

    public static void setPosition(Sprite sprite, float x, float y) {
        sprite.setPosition(x - sprite.getWidth()/2, y - sprite.getHeight()/2);
    }
}
