package com.mona.game.character;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mona.game.windowHandler.Animation;
import com.mona.game.windowHandler.B2DVars;


/**
 * Created by _Silence on 2016/1/2.
 */
public class Sprite {

    protected Body body;
    protected Animation animation;
    protected float width;
    protected float height;

    public Sprite(Body body) {
        this.body = body;
        animation = new Animation();
    }

    public void setAnimation(TextureRegion reg, float delay) {
        setAnimation(new TextureRegion[] { reg }, delay);
    }

    public void setAnimation(TextureRegion[] reg, float delay) {
        animation.setFrames(reg, delay);
        width = reg[0].getRegionWidth();
        height = reg[0].getRegionHeight();
    }

    public void update(float dt) {
        animation.update(dt);
    }

    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(animation.getFrame(), (body.getPosition().x * B2DVars.PPM - width / 2), (int) (body.getPosition().y * B2DVars.PPM - height / 2));
        sb.end();
    }

    public Body getBody() { return body; }
    public Vector2 getPosition() { return body.getPosition(); }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
}
