package uno;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

    private FloatControl volume;

    //Carga el sonido
    public static Clip loadSound(String ruta) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(Sound.class.getResource(ruta)));
            return clip;
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Clip music;

    public Sound(Clip music) {
        this.music = music;
        volume = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
    }

    public void play() {
        music.setFramePosition(0);
        music.start();
    }

    public void loop() {
        music.setFramePosition(0);
        music.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        music.stop();
    }

    public int getFramePosition() {
        return music.getFramePosition();
    }

    public void changeVolume(float value) {
        volume.setValue(value);
    }
}
