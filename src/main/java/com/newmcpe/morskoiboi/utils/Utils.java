package com.newmcpe.morskoiboi.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Newmcpe
 * VK: vk.com/newmcpead
 */
public class Utils {
    public static void sendMessageToPlayers(ArrayList<Player> players, String message) {
        for (Player player : players) {
            player.sendMessage(message);
        }
    }

    public static void sendTitlesToPlayers(ArrayList<Player> players, String title, String subtitle) {
        for (Player player : players) {
            player.sendTitle(title, subtitle);
        }
    }

    public static void reloadArena(String arena) {
        try {
            Bukkit.getServer().unloadWorld(Bukkit.getServer().getWorld(arena), false);
            World world = Bukkit.getServer().createWorld(WorldCreator.name(arena));
            System.out.println("loaded new world: " + world.getName());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
