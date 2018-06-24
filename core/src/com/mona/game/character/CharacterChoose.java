package com.mona.game.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mona.game.MonaGame;

import java.awt.Font;

import javax.swing.text.Style;


/**
 * Created by TED on 2015/12/14.
 */
public class CharacterChoose implements  Screen {
    MonaGame monaGame;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Stage stage;
    private TextButton button;
    private TextureAtlas atlas;
    private TextureAtlas atlasChar;
    private Skin skin;
    private Skin skinChar;
    private Texture texture;
    private Texture char_texture;
    private Sprite sprite;
    private TextButton.TextButtonStyle style;
    private TextButton.TextButtonStyle styleChar;
    private BitmapFont font;
    private ImageButton imageButton;

    public  CharacterChoose(MonaGame mona){
        this.monaGame = mona;
        batch = new SpriteBatch();
    }
    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 550);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        atlas = new TextureAtlas("mainscene/btn.atlas");
        atlasChar = new TextureAtlas("character/opp.pack");

        ////////////////////////////////////
        // Create a skin
        ////////////////////////////////////
        skin = new Skin();
        skin.addRegions(atlas);
        font = new BitmapFont(Gdx.files.internal("mainscene/white_font.fnt"),false);
        ////////////////////////////////////
        // Button Style set
        ////////////////////////////////////
        style = new TextButton.TextButtonStyle();
        style.up = skin.getDrawable("btn_up");
        style.down = skin.getDrawable("btn_down");
        style.font = font;
        //================================================
        skinChar = new Skin();
        skinChar.addRegions(atlasChar);
        styleChar = new TextButton.TextButtonStyle();
        styleChar.down = skinChar.getDrawable("char_choose");
        imageButton = new ImageButton(null,styleChar.down);
        imageButton.setDisabled(true);

        imageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                    imageButton.getStyle().imageUp = styleChar.down;
                    imageButton.getStyle().imageUp = styleChar.down;
                    imageButton.setTouchable(Touchable.disabled);
                    imageButton.setDisabled(true);
                    button.setTouchable(Touchable.enabled);
                    button.setDisabled(false);
            }

        });

        button = new TextButton("開始",style);
        button.setTouchable(Touchable.disabled);
        button.setDisabled(true);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        texture = new Texture(Gdx.files.internal("character/char_background.jpg"));
        sprite = new Sprite(texture);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        stage.act(delta);


        batch.begin();
        sprite.draw(batch);
        batch.end();
        batch.begin();
        stage.draw();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        if (stage == null) {
            stage = new Stage();
        }
        stage.clear();
        button.setWidth(200);
        button.setHeight(55);

        button.setX(Gdx.graphics.getWidth() * 1 / 2 - 100);
        button.setY(Gdx.graphics.getHeight() * 2 / 16 + 10);

        imageButton.setWidth(Gdx.graphics.getWidth()/4);
        imageButton.setHeight(Gdx.graphics.getHeight());
        imageButton.setX(0);
        imageButton.setY(0);

        stage.addActor(imageButton);
        stage.addActor(button);

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
