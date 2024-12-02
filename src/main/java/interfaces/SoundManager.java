package main.java.interfaces;

import java.util.HashMap;

public class SoundManager {
    private SoundPlayer soundPlayer;
    private HashMap<Integer, String> levelMusicMap; // Mapa nivel -> canción

    public SoundManager() {
        soundPlayer = new SoundPlayer();
        levelMusicMap = new HashMap<>();


        levelMusicMap.put(0, "src/main/resources/MusicaGlamour.wav");
        levelMusicMap.put(1, "src/main/resources/MusicaCitaPelea.wav");
        levelMusicMap.put(2, "src/main/resources/MusicaChiste.wav");
        levelMusicMap.put(3, "src/main/resources/MusicaEspiritu.wav");
        levelMusicMap.put(4, "src/main/resources/MusicaDelCovenant.wav");
        levelMusicMap.put(5, "src/main/resources/MusicaNoches.wav");
        levelMusicMap.put(6, "src/main/resources/MusicaDeAtraco.wav");

    }

    public void playLevelMusic(int level) {

        String filePath = levelMusicMap.get(level);
        if (filePath != null) {
            soundPlayer.play(filePath);
        } else {
            System.out.println("Este lvl no tiene musica :v");
        }
    }

    public void loopLevelMusic(int level)
    {
        soundPlayer.loop();
    }
    public void stopMusic() {
        soundPlayer.stop();
    }




}
