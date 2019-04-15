package com.newmcpe.morskoiboi.objects;

import com.newmcpe.morskoiboi.Main;
import com.newmcpe.morskoiboi.enums.GameState;
import com.newmcpe.morskoiboi.enums.TeamType;
import com.newmcpe.morskoiboi.schedule.Schedule;
import com.newmcpe.morskoiboi.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Arena {
    private String name;
    private Team redTeam;
    private Team greenTeam;
    private Location redTeamSpawn;
    private Location greenTeamSpawn;
    private int playerCount = 0;
    private ArrayList<Player> players;

    private BukkitTask task;

    private GameState gameState = GameState.WAITING;

    public Arena(String name) {
        this.name = name;

        players = new ArrayList<>();

        redTeam = new Team(TeamType.RED);
        redTeamSpawn = new Location(Bukkit.getWorld(name), -44, 70, 0);
        redTeam.setSpawn(redTeamSpawn);

        greenTeam = new Team(TeamType.GREEN);
        greenTeamSpawn = new Location(Bukkit.getWorld(name), 29, 70, 0);
        greenTeam.setSpawn(greenTeamSpawn);
    }

    public static Arena getPlayerArena(Player p) {
        for (Arena arena : Main.arenas) {
            for (Player player : arena.players) {
                if (p.getName().equalsIgnoreCase(p.getName())) {
                    return arena;
                }
            }
        }
        return null;
    }

    public static Team getPlayerTeam(Player p) {
        for (Arena arena : Main.arenas) {
            for (Player player : arena.greenTeam.getTeamPlayers()) {
                if (p.getName().equalsIgnoreCase(player.getName())) {
                    return arena.greenTeam;
                }
            }
            for (Player player : arena.redTeam.getTeamPlayers()) {
                if (p.getName().equalsIgnoreCase(player.getName())) {
                    return arena.redTeam;
                }
            }
        }
        return null;
    }

    public void stop(){
        setGameState(GameState.WAITING);

    }

    public void start() {
        setGameState(GameState.COUNTDOWNING);

        AtomicInteger countDown = new AtomicInteger(60);
        Schedule.timer(() -> {
            countDown.getAndDecrement();
            if (countDown.get() == 45) {
                broadcast("До начала: " + countDown.get());
            } else if (countDown.get() == 30) {
                broadcast("До начала: " + countDown.get());
            } else if (countDown.get() < 15 && countDown.get() > 0) {
                broadcast("До начала: " + countDown.get());
            } else if (countDown.get() == 0) {
                setGameState(GameState.RUNNING);
                broadcast("Игра началась!");
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void joinPlayer(Player p, TeamType teamType) {
        playerCount++;
        switch (teamType) {
            case RED:
                redTeam.addPlayer(p);
                p.teleport(redTeamSpawn);
                break;
            case GREEN:
                greenTeam.addPlayer(p);
                p.teleport(greenTeamSpawn);
                break;
        }
        broadcast(p.getDisplayName() + " зашел за команду " + teamType.name());
        players.add(p);
        p.setGameMode(GameMode.ADVENTURE);
        p.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
        p.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
        p.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
        p.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
        p.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
        p.getInventory().addItem(new ItemStack(Material.BOW));
        p.getInventory().addItem(new ItemStack(Material.ARROW, 64));
        p.getInventory().addItem(new ItemStack(Material.TNT, 16));
        p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 2));
        p.getInventory().addItem(new ItemStack(Material.BOAT, 1));
        p.getInventory().addItem(new ItemStack(Material.getMaterial(364), 32));

        if (playerCount >= 2 && gameState == GameState.WAITING) {
            this.start();
            broadcast("Стартую арену....");
        }
    }

    private void broadcast(String msg) {
        Utils.sendMessageToPlayers(redTeam.getTeamPlayers(), msg);
        Utils.sendMessageToPlayers(greenTeam.getTeamPlayers(), msg);
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public String getName() {
        return name;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void removePlayer(Player p) {
        players.removeIf(player -> Objects.equals(player.getName(), p.getName()));
    }

    public void onDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            Player p = (Player) entity;
            Arena arena = Arena.getPlayerArena(p);
            if (arena != null) {
                if (arena.getGameState() != GameState.RUNNING) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
        if (event instanceof EntityDamageByEntityEvent) {
            Entity damager = ((EntityDamageByEntityEvent) event).getDamager();
            if (entity instanceof Player && damager instanceof Player) {
                Player DMGer = (Player) damager;
                Player ENTity = (Player) entity;
                if(getPlayerArena(ENTity) != null && getPlayerArena(DMGer) != null){
                    Team team = getPlayerTeam(DMGer);
                    Team teamSecond = getPlayerTeam(ENTity);
                    if(team.getType().equals(teamSecond.getType())){
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    public void onDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();
        Arena arena = Arena.getPlayerArena(p);
        if (arena != null) {
            if (arena.getGameState() == GameState.RUNNING) {
                Team team = Arena.getPlayerTeam(p);
                team.removePlayer(p);
                arena.removePlayer(p);
                broadcast(p.getDisplayName() + " покинул игру :(");
                p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                if(team.getTeamPlayers().size() == 0){
                    Team vTeam = getVersusTeam(team);
                    vTeam.getTeamPlayers().forEach(player -> {
                        player.sendMessage("Ваша команда " + vTeam.getType().name() + " победила!");
                        team.removePlayer(player);
                        arena.removePlayer(player);
                        player.getInventory().clear();
                        player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());

                        //отчищаем блять нахуй
                        players.clear();
                        redTeam.getTeamPlayers().clear();
                        greenTeam.getTeamPlayers().clear();

                        //ждем
                        setGameState(GameState.WAITING);
                    });
                }
            }
        }
    }
    public void onLeave(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        Arena arena = Arena.getPlayerArena(p);
        if (arena != null) {
            if (arena.getGameState() == GameState.RUNNING) {
                Team team = Arena.getPlayerTeam(p);
                team.removePlayer(p);
                arena.removePlayer(p);
                broadcast(p.getDisplayName() + " покинул игру :(");
                p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                if(team.getTeamPlayers().size() == 0){
                    Team vTeam = getVersusTeam(team);
                    vTeam.getTeamPlayers().forEach(player -> {
                        player.sendMessage("Ваша команда " + vTeam.getType().name() + " победила!");
                        team.removePlayer(player);
                        arena.removePlayer(player);
                        player.getInventory().clear();
                        player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                        //отчищаем блять нахуй
                        players.clear();
                        redTeam.getTeamPlayers().clear();
                        greenTeam.getTeamPlayers().clear();
                        //ждем
                        setGameState(GameState.WAITING);
                    });
                }
            }
        }
    }
    private Team getVersusTeam(Team team){
        if(team.getType() == TeamType.RED){
            return greenTeam;
        } else {
            return redTeam;
        }
    }
}
