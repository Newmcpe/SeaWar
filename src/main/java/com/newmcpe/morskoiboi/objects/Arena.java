package com.newmcpe.morskoiboi.objects;

import com.newmcpe.morskoiboi.Main;
import com.newmcpe.morskoiboi.enums.GameState;
import com.newmcpe.morskoiboi.enums.TeamType;
import com.newmcpe.morskoiboi.schedule.Schedule;
import com.newmcpe.morskoiboi.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Arena {
    private static Map<TeamType, Team> teams;
    private String name;
    private ArrayList<Player> players;

    private GameState gameState = GameState.WAITING;

    public Arena(String name) {
        this.name = name;

        players = new ArrayList<>();
        teams = new HashMap<>();

        Team redTeam = new Team(this, TeamType.RED);
        Team greenTeam = new Team(this, TeamType.GREEN);

        teams.put(TeamType.RED, redTeam);
        teams.put(TeamType.GREEN, greenTeam);
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
        return teams
                .entrySet()
                .stream()
                .filter(team -> team
                        .getValue()
                        .getTeamPlayers()
                        .stream()
                        .anyMatch(player -> p
                                .getName()
                                .equalsIgnoreCase(player.getName()))).findAny().get().getValue();
    }

    public void stop() {
        setGameState(GameState.WAITING);

        players.clear();
        teams.forEach((teamType, team) -> {
            team.getTeamPlayers().clear();
        });

        Utils.reloadArena(this.name);
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
        Team team = this.getTeam(teamType);
        team.addPlayer(p);
        p.teleport(team.getSpawn());
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

        if (players.size() >= 2 && gameState == GameState.WAITING) {
            this.start();
            broadcast("Стартую арену....");

            //debug
            players.forEach(player -> {
                System.out.println("all playerz" + player.getDisplayName());
            });
            this.getTeam(TeamType.GREEN).getTeamPlayers().forEach(player -> {
                System.out.println("green team: " + player.getDisplayName());
            });
            this.getTeam(TeamType.RED).getTeamPlayers().forEach(player -> {
                System.out.println("red team" + player.getDisplayName());
            });
        }
    }

    private void broadcast(String msg) {
        Utils.sendMessageToPlayers(this.getTeam(TeamType.RED).getTeamPlayers(), msg);
        Utils.sendMessageToPlayers(this.getTeam(TeamType.GREEN).getTeamPlayers(), msg);
    }
    private Team getTeam(TeamType teamType){
        return teams.get(teamType);
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
        return players.size();
    }

    public void removePlayer(Player p) {
        System.out.println(players.removeIf(player -> Objects.equals(player.getName(), p.getName())));
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
            EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent) event;
            Entity damagerEntity = damageByEntityEvent.getDamager();
            if (entity instanceof Player && damagerEntity instanceof Player) {
                Player damager = (Player) damagerEntity;
                Player victim = (Player) entity;
                if (getPlayerArena(victim) != null && getPlayerArena(damager) != null) {
                    Team team = getPlayerTeam(damager);
                    Team teamSecond = getPlayerTeam(victim);
                    if (team.getType().equals(teamSecond.getType())) {
                        event.setCancelled(true);
                    }
                }
                if (((Player) entity).getHealth() <= event.getFinalDamage()) {
                    event.setCancelled(true);
                    Player p = (Player) entity;
                    Arena arena = Arena.getPlayerArena(p);
                    if (arena != null) {
                        if (arena.getGameState() == GameState.RUNNING) {
                            Team team = Arena.getPlayerTeam(p);
                            team.removePlayer(p);
                            arena.removePlayer(p);
                            broadcast(p.getDisplayName() + " покинул игру :(");
                            p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                            p.getInventory().clear();
                            if (team.getTeamPlayers().size() == 0) {
                                Team vTeam = getVersusTeam(team);
                                System.out.println("versus=" + vTeam.getType() + "; this=" + team.getType());
                                vTeam.getTeamPlayers().forEach(player -> {
                                    player.sendMessage("Ваша команда " + vTeam.getType().name() + " победила!");
                                    team.removePlayer(player);
                                    arena.removePlayer(player);
                                    player.getInventory().clear();
                                    player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                                });
                                this.stop();
                            }
                        }
                    }
                }
            }
        }
    }


    public void onLeave(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        p.getInventory().clear();
        Arena arena = Arena.getPlayerArena(p);
        if (arena != null) {
            if (arena.getGameState() == GameState.RUNNING) {
                Team team = Arena.getPlayerTeam(p);
                team.removePlayer(p);
                arena.removePlayer(p);
                broadcast(p.getDisplayName() + " покинул игру :(");
                p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                p.getInventory().clear();
                if (team.getTeamPlayers().size() == 0) {
                    Team vTeam = getVersusTeam(team);
                    vTeam.getTeamPlayers().forEach(player -> {
                        player.sendMessage("Ваша команда " + vTeam.getType().name() + " победила!");
                        player.getInventory().clear();
                        player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                    });
                    this.stop();
                }
            }
        }
    }

    private Team getVersusTeam(Team team) {
        return team.getType() == TeamType.RED ? teams.get(TeamType.GREEN) : teams.get(TeamType.RED);
    }
}
