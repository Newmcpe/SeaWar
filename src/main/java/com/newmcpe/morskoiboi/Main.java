package com.newmcpe.morskoiboi;

import com.newmcpe.morskoiboi.listeners.EventListener;
import com.newmcpe.morskoiboi.objects.Arena;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

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
        this.getServer().getPluginManager().registerEvents(new EventListener(), this);

        addArenas();
    }

    private void addArenas() {
        arenas = new ArrayList<>();
        arenas.add(new Arena("mb1"));
        arenas.add(new Arena("mb2"));

        arenas.forEach(arena -> {
            World world = Bukkit.getWorld(arena.getName());
            if (world != null) {
                System.out.println("отключаю автосейв в " + world.getName());
                world.setAutoSave(false);
            } else {
                System.out.println("удаляю арену " + arena.getName() + ", т.к ее мира не существует");
                arenas.removeIf(arena1 -> arena1.getName().equalsIgnoreCase(arena.getName()));
            }
        });
    }
}
