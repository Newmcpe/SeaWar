package com.newmcpe.morskoiboi.objects;

import com.newmcpe.morskoiboi.enums.TeamType;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Objects;

public class Team {
    private ArrayList<Player> teamPlayers;
    private Location spawn;
    private TeamType type;

    public Team(TeamType type) {
        teamPlayers = new ArrayList<>();
        this.type = type;
    }

    public void addPlayer(Player p) {
        teamPlayers.add(p);
    }

    public void removePlayer(Player p) {
        teamPlayers.removeIf(player -> Objects.equals(player.getName(), p.getName()));
    }
    public void setSpawn(Location loc){
        this.spawn = loc;
    }

    public ArrayList<Player> getTeamPlayers() {
        return teamPlayers;
    }

    public Location getSpawn() {
        return spawn;
    }

    public TeamType getType() {
        return type;
    }
}
