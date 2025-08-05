package Main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Sound {
    Clip clip;
    URL[] soundurUrl = new URL[30];

    public Sound() {
        soundurUrl[0] = getClass().getResource("/sound/villageHum.wav");
        soundurUrl[1] = getClass().getResource("/sound/grass.wav");
        soundurUrl[2] = getClass().getResource("/sound/DungeonMusic.wav");
        soundurUrl[3] = getClass().getResource("/sound/itemGET.wav");
        soundurUrl[4] = getClass().getResource("/sound/doorunlock.wav");
        soundurUrl[5] = getClass().getResource("/sound/attack_spear.wav");
        soundurUrl[6] = getClass().getResource("/sound/Acadiavillage.wav");
        soundurUrl[7] = getClass().getResource("/sound/orb.wav");
        soundurUrl[8] = getClass().getResource("/sound/denied.wav");
        soundurUrl[10] = getClass().getResource("/sound/gun.wav");
        soundurUrl[9] = getClass().getResource("/sound/walks.wav");
        soundurUrl[11] = getClass().getResource("/sound/hurtOrc.wav");
        soundurUrl[12] = getClass().getResource("/sound/orcDeath.wav");
        soundurUrl[13] = getClass().getResource("/sound/ak_reload.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundurUrl[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playMs() {
        clip.start();
    }

    public void stopMs() {
        clip.stop();
    }

    public void loopMs() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
