package com.newmcpe.morskoiboi.utils;

import com.newmcpe.morskoiboi.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Newmcpe
 * VK: vk.com/newmcpead
 */
public class Utils {
    public static void copyFolder(File src, File dest)
            throws IOException {

        if (src.isDirectory()) {

            //if directory not exists, create it
            if (!dest.exists()) {
                dest.mkdir();
                System.out.println("Directory copied from "
                        + src + "  to " + dest);
            }

            //list all the directory contents
            String files[] = src.list();

            for (String file : files) {
                //construct the src and dest file structure
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                //recursive copy
                copyFolder(srcFile, destFile);
            }

        } else {
            //if file, then copy it
            //Use bytes stream to support all file types
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);

            byte[] buffer = new byte[1024];

            int length;
            //copy the file content in bytes
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            in.close();
            out.close();
            System.out.println("File copied from " + src + " to " + dest);
        }
    }

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

    public static void delete(File f) throws IOException {
        if (f.isDirectory()) {
            for (File c : f.listFiles())
                delete(c);
        }
        if (!f.delete())
            throw new FileNotFoundException("дебилка, у тебя файл не удаляется:  " + f);
    }

    public static void reloadArena(String arena) {
        try {
            Bukkit.getServer().unloadWorld(Bukkit.getServer().getWorld(arena), false);
            //Utils.delete(new File(Bukkit.getServer().getWorldContainer().getAbsolutePath() + "/" + arena));
            //Utils.copyFolder(new File(Main.instance.getDataFolder() + "/arenas/" + arena), new File(Bukkit.getServer().getWorldContainer().getAbsolutePath() + "/" + arena));
            World world = Bukkit.getServer().createWorld(WorldCreator.name(arena));
            System.out.println("loaded new world: " + world.getName());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public static void loadArena(String arena) {
        try {
            boolean isDone = Bukkit.getServer().unloadWorld(Bukkit.getWorld(arena), false);
            System.out.println("unload world: " + isDone);
            //Utils.delete(new File(Bukkit.getServer().getWorldContainer().getAbsolutePath() + "/" + arena));
            //Utils.copyFolder(new File(Main.instance.getDataFolder() + "/arenas/" + arena), new File(Bukkit.getServer().getWorldContainer().getAbsolutePath() + "/" + arena));
            World world = Bukkit.getServer().createWorld(WorldCreator.name(arena));
            System.out.println("loaded new world: " + world.getName());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
