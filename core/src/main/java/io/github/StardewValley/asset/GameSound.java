package io.github.StardewValley.asset;

import com.badlogic.gdx.audio.Sound;

public enum GameSound {
    CLICK("click.wav"),
    ;

    private final String path;
    private Sound sound;

    GameSound(String path) {
        this.path = "sound-effects/" + path;
    }

    public String getPath() {
        return path;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public void play(float volume) {
        if (sound != null)
            sound.play(volume);
    }

    public void dispose() {
        if (sound != null) sound.dispose();
    }
}
