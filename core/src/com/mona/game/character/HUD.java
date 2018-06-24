package com.mona.game.character;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by _Silence on 2016/1/2.
 */
public class HUD {
    private character player;

    private TextureRegion heart;
    private TextureRegion[] font;

    public HUD(character player) {

        this.player = player;
        Texture tex = new Texture(Gdx.files.internal("character/heart.png"));
        Texture tex2 = new Texture(Gdx.files.internal("character/hud.png"));

        heart = new TextureRegion(tex, 0, 0, 16, 16);

        font = new TextureRegion[11];
        for(int i = 0; i < 6; i++) {
            font[i] = new TextureRegion(tex2, 32 + i * 9, 16, 9, 9);
        }
        for(int i = 0; i < 5; i++) {
            font[i + 6] = new TextureRegion(tex2, 32 + i * 9, 25, 9, 9);
        }
    }


    public void render(SpriteBatch sb) {

        sb.begin();


        // draw crystal
        sb.draw(heart,40,185);

        // draw crystal amount
        drawString(sb,  player.getHealth()+ " / " + player.getMaxHealth(),60, 190);

        sb.end();

    }

    private void drawString(SpriteBatch sb, String s, float x, float y) {
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == '/') c = 10;
            else if(c >= '0' && c <= '9') c -= '0';
            else continue;
            sb.draw(font[c], x + i * 9, y);
        }
    }



}
