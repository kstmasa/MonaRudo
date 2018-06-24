package com.mona.game.character;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

import java.util.List;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.mona.game.MonaGame;
import com.mona.game.windowHandler.B2DVars;

/**
 * Created by TED on 2015/12/28.
 */
public  class character extends Sprite {

    private int health;
    private int Maxhealth;
    public Array<TextureRegion[]> showAnimation;
    public int currentAction;
    public character(Body body){

        super(body);
        Maxhealth=20;
        health = 20;
        Texture tex = new Texture(Gdx.files.internal("character/player_sheet.png"));
        showAnimation = new Array<TextureRegion[]>();
        for (int i  = 0 ; i< B2DVars.numFrames.length;i++){
            TextureRegion[] sprites = new TextureRegion[B2DVars.numFrames[i]];

            if(i != B2DVars.ATTACK){
                for (int j = 0; j < sprites.length; j++) {
                    sprites[j] = new TextureRegion(tex, j * 80,i*64, 80, 64);
                }
            }else {
                for (int j = 0; j < sprites.length; j++) {
                    sprites[j] = new TextureRegion(tex, j * 113, i*64, 113, 64);
                }
            }
            showAnimation.add(sprites);
        }

        currentAction = B2DVars.NORML;
        animation.setFrames(showAnimation.get(currentAction));

        width =showAnimation.get(currentAction)[0].getRegionWidth();
        height = showAnimation.get(currentAction)[0].getRegionHeight();
    }
    public int getHealth() { return health; }
    public int getMaxHealth() { return Maxhealth; }
    public void setShowAnimationFunction(int i, float delay){
        currentAction = i ;
        animation.setFrames(showAnimation.get(currentAction),delay);
    }
    public void byAttack(){
        health -= 5;
    }
}
