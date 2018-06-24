package com.mona.game.windowHandler;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mona.game.MonaGame;

/**
 * Created by TED on 2016/1/1.
 */
public class BackGround {
    private TextureRegion textureRegion;
    private OrthographicCamera camera;
    private float scale;

    private float x;
    private float y;
    private int numDrawX;
    private int numDrawY;

    private float dx;
    private float dy;

    public BackGround(TextureRegion textureRegion, OrthographicCamera camera, float scale) {
        this.textureRegion = textureRegion;
        this.camera = camera;
        this.scale = scale;
        numDrawX = MonaGame.V_WIDTH / textureRegion.getRegionWidth() + 1;
        numDrawY = MonaGame.V_HEIGHT / textureRegion.getRegionHeight() + 1;
    }

    public void setVector(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void update(float dt) {
        x += (dx * scale) * dt;
        y += (dy * scale) * dt;
    }

    public void render(SpriteBatch sb) {

        float x = ((this.x + camera.viewportWidth / 2 - camera.position.x) * scale) % textureRegion.getRegionWidth();
        float y = ((this.y + camera.viewportHeight / 2 - camera.position.y) * scale) % textureRegion.getRegionHeight();

        sb.begin();

        int colOffset = x > 0 ? -1 : 0;
        int rowOffset = y > 0 ? -1 : 0;
        for(int row = 0; row < numDrawY; row++) {
            for(int col = 0; col < numDrawX; col++) {
                sb.draw(textureRegion, x + (col + colOffset) * textureRegion.getRegionWidth(), y + (rowOffset + row) * textureRegion.getRegionHeight());
            }
        }

        sb.end();

    }
}
