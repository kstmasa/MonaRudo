package com.mona.game.MainScene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mona.game.MonaGame;


/**
 * Created by TED on 2015/12/5.
 */
public class mainscene implements Screen {
    MonaGame monaGame;
    private OrthographicCamera camera;
    private Texture texture;
    private Sprite sprite;
    public SpriteBatch batch;
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private TextButton startButton;
    private TextButton endButton;
    private TextButton.TextButtonStyle style;
    private BitmapFont font;

    public mainscene(MonaGame mona){
        this.monaGame = mona;
        batch = new SpriteBatch();
    }
    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
        ////////////////////////////////////
        // Create atlas
        ////////////////////////////////////
        atlas = new TextureAtlas("mainscene/btn.pack");
        ////////////////////////////////////
        // Create a skin
        ////////////////////////////////////
        skin = new Skin();
        skin.addRegions(atlas);
        font = new BitmapFont(Gdx.files.internal("mainscene/white_font.fnt"),
                false);
        ////////////////////////////////////
        // Button Style set
        ////////////////////////////////////
        style = new TextButton.TextButtonStyle();
        style.up = skin.getDrawable("btn_up");
        style.down = skin.getDrawable("btn_down");
        style.font = font;
        ////////////////////////////////////
        // Start Button
        ////////////////////////////////////

        startButton = new TextButton("開始遊戲" , style);
        startButton.setColor(1, 1, 1, 0);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MonaGame.music.getMusic("click").play();
                monaGame.setScreen(new CharacterChoose(monaGame));
            }
        });
        ////////////////////////////////////
        // End Button
        ////////////////////////////////////
        endButton = new TextButton("離開遊戲" , style);
        endButton.setColor(1, 1, 1, 0);
        endButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MonaGame.music.getMusic("click").play();
                Gdx.app.exit();
            }
        });

        ////////////////////////////////////
        //opening winodws
        ////////////////////////////////////
        texture = new Texture(Gdx.files.internal("mainscene/mainscene.png"));
        sprite = new Sprite(texture);



        MonaGame.music.getMusic("startBGM").play();
        MonaGame.music.getMusic("startBGM").setVolume(1/4f);

    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        stage.act(delta);
        ////////////////////////////////////
        //BEGIN DRAWING BATCHS
        ////////////////////////////////////
        batch.begin();
        sprite.draw(batch);
        batch.end();
        ////////////////////////////////////
        // Draw the main scene
        ////////////////////////////////////
        batch.begin();
        stage.draw();
        batch.end();
        ////////////////////////////////////
        //  Draw buttons
        ////////////////////////////////////
        ////////////////////////////////////
        //END DRAWING BATCHS
		////////////////////////////////////

        ////////////////////////////////////
        // If main scene background full appears. All buttons are displayed.
        ////////////////////////////////////
        if (!sprite.getColor().toString().equals("fffffffe")) {
            startButton.setColor(1, 1, 1, 1);
            endButton.setColor(1, 1, 1, 1);
        }
    }

    @Override
    public void resize(int width, int height) {
        if (stage == null) {
            stage = new Stage();
        }
        stage.clear();

        Gdx.input.setInputProcessor(stage);
        ////////////////////////////////////
        // Editing Start button
        ////////////////////////////////////
        startButton.setWidth(200);
        startButton.setHeight(55);
        startButton.setX(Gdx.graphics.getWidth() * 1 / 2 - 250);
        startButton.setY(Gdx.graphics.getHeight() * 2 / 16 + 10);
        ////////////////////////////////////
        // Editing End button
        ////////////////////////////////////
        endButton.setWidth(200);
        endButton.setHeight(55);
        endButton.setX(Gdx.graphics.getWidth() * 1/2 +50);
        endButton.setY(Gdx.graphics.getHeight() * 2 / 16 + 10);

        ////////////////////////////////////
        // Add to Stage
        ////////////////////////////////////
        stage.addActor(startButton);
        stage.addActor(endButton);
     }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        Gdx.app.log("Dispose", "Logged in dispose");
        stage.clear();
    }
}
