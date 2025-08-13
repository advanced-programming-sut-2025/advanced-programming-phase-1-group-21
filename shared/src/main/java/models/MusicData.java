package models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class MusicData implements KryoSerializable {
    private String filePath;
    private byte[] musicBytes;
    private boolean looping;
    private float volume = 1.0f;

    /**
     * NEW: Holds the playable music instance on the client.
     * 'transient' tells Kryo to ignore this field during serialization.
     * This is crucial because a Music object cannot be sent over the network.
     */
    private transient Music musicInstance;

    public MusicData() {
    }

    public MusicData(String filePath) {
        this.filePath = filePath;
        loadMusicBytes();
    }

    private void loadMusicBytes() {
        System.out.println("Loading Music File: " + filePath);
        try {
            FileHandle fileHandle = Gdx.files.internal(filePath);
            this.musicBytes = fileHandle.readBytes();
        } catch (Exception e) {
            e.printStackTrace();
            this.musicBytes = new byte[0];
        }
    }

    @Override
    public void write(Kryo kryo, Output output) {
        output.writeString(filePath);
        output.writeBoolean(looping);
        output.writeFloat(volume);
        output.writeInt(musicBytes.length);
        output.writeBytes(musicBytes);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        filePath = input.readString();
        looping = input.readBoolean();
        volume = input.readFloat();
        int length = input.readInt();
        musicBytes = input.readBytes(length);
    }

    /**
     * MODIFIED: Plays the music, creating the instance if it doesn't exist.
     */
    public void play() {
        Gdx.app.postRunnable(() -> {
            // If the music instance doesn't exist, create it.
            if (musicInstance == null && musicBytes != null && musicBytes.length > 0) {
                try {
                    java.io.File tempFile = java.io.File.createTempFile("kryo_music_", getFileExtension());
                    tempFile.deleteOnExit();
                    java.nio.file.Files.write(tempFile.toPath(), musicBytes);
                    musicInstance = Gdx.audio.newMusic(Gdx.files.absolute(tempFile.getAbsolutePath()));
                } catch (Exception e) {
                    Gdx.app.error("MusicData", "Error creating music from bytes", e);
                    return; // Exit if creation fails
                }
            }

            // If the instance exists, set its properties and play.
            if (musicInstance != null) {
                musicInstance.setLooping(looping);
                musicInstance.setVolume(volume);
                musicInstance.play();
            }
        });
    }

    public void updateVolume(float newVolume) {
        // Clamp the volume between 0.0 and 1.0
        this.volume = Math.max(0.0f, Math.min(1.0f, newVolume));

        // If the music is currently playing, update its volume on the main thread.
        if (musicInstance != null) {
            Gdx.app.postRunnable(() -> {
                if (musicInstance != null) { // Double-check in case it was disposed
                    musicInstance.setVolume(this.volume);
                }
            });
        }
    }

    /**
     * NEW: Stops the music playback.
     */
    public void stop() {
        if (musicInstance != null) {
            Gdx.app.postRunnable(() -> {
                if (musicInstance != null) musicInstance.stop();
            });
        }
    }

    /**
     * NEW: Disposes of the music instance to free up memory. Call this when you're done.
     */
    public void dispose() {
        if (musicInstance != null) {
            Gdx.app.postRunnable(() -> {
                if (musicInstance != null) {
                    musicInstance.dispose();
                    musicInstance = null;
                }
            });
        }
    }

    private String getFileExtension() {
        if (filePath == null) return ".mp3";
        int lastDot = filePath.lastIndexOf('.');
        if (lastDot == -1) return ".mp3";
        return filePath.substring(lastDot);
    }

    // Getters and setters...
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public byte[] getMusicBytes() { return musicBytes; }
    public void setMusicBytes(byte[] musicBytes) { this.musicBytes = musicBytes; }
    public boolean isLooping() { return looping; }
    public void setLooping(boolean looping) { this.looping = looping; }
    public float getVolume() { return volume; }
    public void setVolume(float volume) { this.volume = volume; }
}