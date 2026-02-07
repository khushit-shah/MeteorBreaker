package com.meteor.breaker;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class AudioPlayer {
    private static final Logger LOGGER = Log.getLogger(AudioPlayer.class);

    static Map<String, Sound> sound = new HashMap<>();
    static Map<String, Music> music = new HashMap<>();

    public static void load() {
        try {
            music.put("background", new Music("background.ogg"));
            sound.put("bullet", new Sound("Bullet.ogg"));
            sound.put("missile", new Sound("Missile.ogg"));
            sound.put("bullet_Over", new Sound("BulletOver.ogg"));
            sound.put("Game_Over", new Sound("GameOver.ogg"));
            sound.put("missile_Blast", new Sound("MissileBlast.ogg"));
            LOGGER.info("Audio resources loaded successfully");
        } catch (SlickException exception) {
            LOGGER.severe("Failed to load audio resources: " + exception.getMessage());
        }
    }

    public static Music getMusic(String key) {
        return music.get(key);
    }

    public static Sound getSound(String key) {
        return sound.get(key);
    }
}
