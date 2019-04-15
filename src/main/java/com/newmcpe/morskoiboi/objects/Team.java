package com.newmcpe.morskoiboi.objects;

import com.newmcpe.morskoiboi.enums.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class Team {
    private Arena teamArena;
    private ArrayList<Player> teamPlayers;
    private Map<TeamType, Location> spawnLocations;
    private TeamType type;

    public Team(Arena teamArena, TeamType type) {
        this.type = type;
        teamPlayers = new ArrayList<>();
        this.teamArena = teamArena;
        spawnLocations.put(TeamType.RED, new Location(Bukkit.getWorld(teamArena.getName()), -44, 70, 0));
        spawnLocations.put(TeamType.GREEN, new Location(Bukkit.getWorld(teamArena.getName()), 29, 70, 0));
    }

    public void addPlayer(Player p) {
        teamPlayers.add(p);
    }

    public void removePlayer(Player p) {
        teamPlayers.removeIf(player -> Objects.equals(player.getName(), p.getName()));
    }

    public ArrayList<Player> getTeamPlayers() {
        return teamPlayers;
    }

    public Location getSpawn() {
        return spawnLocations.get(type);
    }

    public TeamType getType() {
        return type;
    }
}
