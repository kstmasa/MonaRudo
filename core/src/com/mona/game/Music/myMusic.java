package com.mona.game.Music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import java.util.HashMap;

/**
 * Created by TED on 2015/12/31.
 */
public class myMusic {
    private HashMap<String,Music> music;
    public myMusic(){
        music = new HashMap<String, Music>();
    }

    /*********/
	/* Music */
    /*********/

    public void loadMusic(String path) {
        int slashIndex = path.lastIndexOf('/');
        String key;
        if(slashIndex == -1) {
            key = path.substring(0, path.lastIndexOf('.'));
        }
        else {
            key = path.substring(slashIndex + 1, path.lastIndexOf('.'));
        }
        loadMusic(path, key);
    }
    public void loadMusic(String path, String key) {
        Music m = Gdx.audio.newMusic(Gdx.files.internal(path));
        music.put(key, m);
    }
    public Music getMusic(String key) {
        return music.get(key);
    }
    public void removeMusic(String key) {
        Music m = music.get(key);
        if(m != null) {
            music.remove(key);
            m.dispose();
        }
    }

}
