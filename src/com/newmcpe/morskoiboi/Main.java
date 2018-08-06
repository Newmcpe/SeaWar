package com.newmcpe.morskoiboi;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Newmcpe on 16.08.2017.
 */

public class Main extends JavaPlugin {
    private static void unloadWorld(World world) {
        for (Player p : world.getPlayers()) {
            p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
        }
        //  Bukkit.getServer().unloadWorld(world, false);
        System.out.println(world.getName() + " unloaded!");
        Bukkit.getServer().createWorld(WorldCreator.name(world.getName()));
        System.out.println(world.getName() + " loaded!");
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onEnable() {

        EventListener.playersinarena = new ArrayList<Player>();
        EventListener.players1 = new ArrayList<Player>();
        EventListener.players2 = new ArrayList<Player>();
        EventListener.playerarena = new HashMap<Player, String>();
        EventListener.gameStatus = 0;
        EventListener listener = new EventListener(this);
        getServer().getPluginManager().registerEvents(listener, this);
        getServer().getPluginCommand("tpworld").setExecutor(new CommandExecutor() {
            public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
                if (commandSender instanceof Player) {
                    if (commandSender.getServer().getWorld(strings[0]) != null) {
                        ((Player) commandSender).teleport(commandSender.getServer().getWorld(strings[0]).getSpawnLocation());
                    } else {
                        Bukkit.createWorld(WorldCreator.name(strings[0]));
                        ((Player) commandSender).teleport(commandSender.getServer().getWorld(strings[0]).getSpawnLocation());
                    }

                }

                return true;
            }
        });
        getServer().getPluginCommand("pidoras").setExecutor(new CommandExecutor() {
            public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
                Bukkit.unloadWorld("battle", false);
                Bukkit.createWorld(WorldCreator.name("battle"));
                Bukkit.getWorld("battle").setAutoSave(false);
                return true;
            }
        });
        getServer().getPluginCommand("minigames").setExecutor(new CommandExecutor() {
            public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
                if (commandSender instanceof Player) {
                    EventListener.openGUIChooseMinigame((Player) commandSender);
                }
                return true;
            }
        });
        getServer().getPluginCommand("spawn").setExecutor(new CommandExecutor() {
            public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
                if (commandSender instanceof Player) {
                    commandSender.sendMessage("§8(§a§lMine§c§lLuxe§8) §aВы телепортированы на §eспавн");
                    ((Player) commandSender).teleport(new Location(Bukkit.getWorld("world"), -62.500, 65, 1277.500, 90, 1));
                }
                return true;
            }
        });
        getServer().getPluginCommand("menu").setExecutor(new CommandExecutor() {
            public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
                if (commandSender instanceof Player) {
                    EventListener.openGUIMenu((Player) commandSender);
                }
                return true;
            }
        });
        Bukkit.getWorld("battle").setAutoSave(false);
        //  Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new BukkitRunnable() {
//
        //          @Override
        //        public void run() {
        //          for(int i = 0; i < listener.chunkstoload.size(); i++){
        //             listener.chunkstoload.get(i).load();
        //      }
        //}
        // }, 0L /*Задержка перед выполнением(в тиках)*/, 1200L /*задержка после выполнения(в тиках)*/);*/
    }


}
