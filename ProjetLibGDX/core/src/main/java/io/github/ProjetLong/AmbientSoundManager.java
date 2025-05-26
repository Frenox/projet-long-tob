package io.github.ProjetLong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AmbientSoundManager {

    private static final Sound wavesSfx = Gdx.audio.newSound(Gdx.files.internal("audio/AmbientWater.mp3"));
    private static final Sound seagullSfx = Gdx.audio.newSound(Gdx.files.internal("audio/Seagull.mp3"));
    private ScheduledExecutorService seagullScheduler;

    private boolean isPaused;

    public AmbientSoundManager() {
        wavesSfx.loop(); // Lance l'audio de vagues
        startSeagullLoop(); // Lance la boucle de sfx de mouette
    }

    /*Initialise la boucle d'audio pour le sfx de mouette */
    private void startSeagullLoop() {
        seagullScheduler = Executors.newSingleThreadScheduledExecutor();
        scheduleSeagullSound();
    }

    private void scheduleSeagullSound() {
        int delay = 20 + (int) (Math.random() * 35);

        // Joue l'audio apres [delay] secondes
        seagullScheduler.schedule(() -> {
            if (isPaused) return;
            seagullSfx.play();
            scheduleSeagullSound();
        }, delay, TimeUnit.SECONDS);

    }
    
    public void pauseAudio() {
        isPaused = true;

        wavesSfx.pause();
        seagullSfx.pause();   
    }
    
    public void resumeAudio() {
        isPaused = false;

        wavesSfx.resume();
        seagullSfx.resume();
    }

}
