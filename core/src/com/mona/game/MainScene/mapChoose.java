package com.mona.game.MainScene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.mona.game.MonaGame;
import com.mona.game.Play;

import java.awt.Image;

/**
 * Created by Ted on 2016/1/3.
 */
public class mapChoose implements Screen {
    MonaGame monaGame;
    private Sprite sprite;
    private OrthographicCamera camera;
    private Texture background;
    private SpriteBatch batch;
    private Stage stage;
    private ImageButton imageButton;
    private ImageButton imageButton1;
    private ImageButton imageButton2;
    private ImageButton imageButton3;

    public mapChoose(MonaGame mona){
        this.monaGame = mona;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,1000,550);

        imageButton = new ImageButton(null,new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("mainscene/rainbowBridge_up.png")))));
        imageButton1=new ImageButton(null,new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("mainscene/wushe.gif")))));
        imageButton2=new ImageButton(null,new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("mainscene/river.gif")))));
       imageButton3=new ImageButton(null,new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("mainscene/Mhebu.gif")))));

        imageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MonaGame.music.getMusic("click").play();
                MonaGame.music.removeMusic("startBGM");
                MonaGame.music.getMusic("gameBGM").play();
                monaGame.setScreen(new Play(monaGame));
            }
        });
        background = new Texture(Gdx.files.internal("mainscene/mapChooseBG.jpg"));
        sprite = new Sprite(background);
    }
    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        stage.act(delta);

        batch.begin();
        sprite.draw(batch);
        batch.end();

        batch.begin();
        stage.draw();
        batch.end();

        if (sprite.getColor().toString().equals("fffffffe")) {
            imageButton.setColor(1, 1, 1, 1);
            imageButton1.setColor(1,1,1,1);
            imageButton2.setColor(1,1,1,1);
            imageButton3.setColor(1,1,1,1);

        }


    }

    @Override
    public void resize(int width, int height) {
        if (stage == null) {
            stage = new Stage();
        }
        stage.clear();

        Gdx.input.setInputProcessor(stage);
        imageButton.setWidth(350);
        imageButton.setHeight(260);
        imageButton.setX(0);
        imageButton.setY(Gdx.graphics.getHeight() * 1 / 16 + 10);
        stage.addActor(imageButton);
        imageButton1.setWidth(217);
        imageButton1.setHeight(225);
        imageButton1.setX(Gdx.graphics.getWidth()*1/2-200);
        imageButton1.setY(Gdx.graphics.getHeight() *8 / 16+10 );
        stage.addActor(imageButton1);
        imageButton2.setWidth(275);
        imageButton2.setHeight(230);
        imageButton2.setX(Gdx.graphics.getWidth()*1/2+40);
        imageButton2.setY(Gdx.graphics.getHeight() *1/ 16+10 );
        stage.addActor(imageButton2);
        imageButton3.setWidth(216);
        imageButton3.setHeight(168);
        imageButton3.setX(Gdx.graphics.getWidth()*1/2+30);
        imageButton3.setY(Gdx.graphics.getHeight() *10/ 16+10 );
        stage.addActor(imageButton3);
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

    }
}
