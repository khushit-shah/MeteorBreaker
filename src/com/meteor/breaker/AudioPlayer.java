package com.meteor.breaker;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Khushit on 5/28/2018.
 */

public class AudioPlayer {
    static Map<String,Sound> sound = new HashMap<String,Sound>();
    static Map<String, Music> music = new HashMap<String, Music>();
    public static void load(){
        try {
            music.put("background",new Music("background.ogg"));
            sound.put("bullet",new Sound("Bullet.ogg"));
            sound.put("missile",new Sound("Missile.ogg"));
            sound.put("bullet_Over",new Sound("BulletOver.ogg"));
            sound.put("Game_Over",new Sound("GameOver.ogg"));
            sound.put("missile_Blast",new Sound("MissileBlast.ogg"));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public static Music getMusic(String key){
        return music.get(key);
    }

    public static Sound getSound(String key){
        return sound.get(key);
    }
}