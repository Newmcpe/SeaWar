package com.newmcpe.morskoiboi;

import com.newmcpe.morskoiboi.listeners.EventListener;
import com.newmcpe.morskoiboi.objects.Arena;
import com.newmcpe.morskoiboi.utils.Utils;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 * Created by Newmcpe on 16.08.2017.
 */

public class Main extends JavaPlugin {

    public static Main instance;
    public static ArrayList<Arena> arenas;

    public Main() {
        instance = this;
    }

    @Override
    public void onDisable() {
        System.out.println("плагину пизда, бб");
    }

    @Override
    public void onEnable() {
        this.getDataFolder().mkdir();
        this.getServer().getPluginManager().registerEvents(new EventListener(), this);

        addArenas();
    }

    private void addArenas() {
        arenas = new ArrayList<>();
        arenas.add(new Arena("mb1"));
        arenas.add(new Arena("mb2"));
    }
}
