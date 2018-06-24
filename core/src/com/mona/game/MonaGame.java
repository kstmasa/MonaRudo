package com.mona.game;


import com.badlogic.gdx.Game;
import com.mona.game.MainScene.mainscene;
import com.mona.game.Music.myMusic;

public class MonaGame extends Game {

	public static myMusic music;
	public static final int V_WIDTH = 320;
	public static final int V_HEIGHT = 208;
	public static final float STEP = 1 / 60f;

	@Override
	public void create () {

		music = new myMusic();
		music.loadMusic("Music/startBGM.mp3", "startBGM");
		music.loadMusic("Music/click.mp3","click");
		music.loadMusic("Music/gameBGM.mp3","gameBGM");
		music.loadMusic("Music/gameoverBGM.mp3", "gameoverBGM");
		setScreen(new mainscene(this));

	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();

	}
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.pause();
	}
}
