package com.mona.game.Enemy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.mona.game.character.Sprite;

/**
 * Created by _Silence on 2016/1/3.
 */

public class Enemy extends Sprite {
   public Enemy(Body body)
    {
        super(body);
        Texture tex= new Texture(Gdx.files.internal("enemy/enemy.png"));
        TextureRegion[] enemy = new TextureRegion[3];
      //  TextureRegion[] enemy= TextureRegion.split(tex,56,64)[1];
        for(int i = 0; i < 3; i++) {
            enemy[i] = new TextureRegion(tex,298-( (i+1) * 56),64,56,64);
        }
        animation.setFrames(enemy, 1 /10f);

        width = enemy[0].getRegionWidth();
        height = enemy[0].getRegionHeight();

    }
}
