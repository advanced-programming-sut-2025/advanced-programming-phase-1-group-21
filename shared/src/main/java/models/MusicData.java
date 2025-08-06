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
     * Plays the music using LibGDX
     */
    public void play() {
        // Create a temporary file to play the music
        // Note: In a real application, you might want to cache these files
        Gdx.app.postRunnable(() -> {
            try {
                // Create temp file (LibGDX needs a file handle to play music)
                java.io.File tempFile = java.io.File.createTempFile("kryo_music_", getFileExtension());
                tempFile.deleteOnExit();

                // Write bytes to temp file
                java.nio.file.Files.write(tempFile.toPath(), musicBytes);

                // Load and play music
                Music music = Gdx.audio.newMusic(Gdx.files.absolute(tempFile.getAbsolutePath()));
                music.setLooping(looping);
                music.setVolume(volume);
                music.play();

                // Store reference if you need to control it later
                // You might want to add this to a music manager
            } catch (Exception e) {
                Gdx.app.error("MusicData", "Error playing music", e);
            }
        });
    }

    private String getFileExtension() {
        if (filePath == null) return ".mp3";
        int lastDot = filePath.lastIndexOf('.');
        if (lastDot == -1) return ".mp3";
        return filePath.substring(lastDot);
    }

    // Getters and setters
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public byte[] getMusicBytes() {
        return musicBytes;
    }

    public void setMusicBytes(byte[] musicBytes) {
        this.musicBytes = musicBytes;
    }

    public boolean isLooping() {
        return looping;
    }

    public void setLooping(boolean looping) {
        this.looping = looping;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }
}