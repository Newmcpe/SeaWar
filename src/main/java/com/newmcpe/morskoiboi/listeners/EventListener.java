package com.newmcpe.morskoiboi.listeners;

import com.newmcpe.morskoiboi.Main;
import com.newmcpe.morskoiboi.enums.GameState;
import com.newmcpe.morskoiboi.enums.TeamType;
import com.newmcpe.morskoiboi.objects.Arena;
import com.newmcpe.morskoiboi.objects.Team;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class EventListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        try {
            Player player = event.getPlayer();

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Material clickedBlock = event.getClickedBlock().getType();
                if (clickedBlock == Material.SIGN || clickedBlock == Material.SIGN_POST || clickedBlock == Material.WALL_SIGN) {
                    Sign a = (Sign) event.getClickedBlock().getState();
                    Main.arenas.forEach(arena -> {
                        if (a.getLine(0).equalsIgnoreCase(arena.getName())) {
                            if (a.getLine(1).equalsIgnoreCase("1"))
                                arena.joinPlayer(player, TeamType.RED);
                            if (a.getLine(1).equalsIgnoreCase("2"))
                                arena.joinPlayer(player, TeamType.GREEN);
                        }
                    });
                }
                if (clickedBlock == Material.WOOD_BUTTON) {
                    event.setCancelled(true);
                }
            } else if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                if (event.getItem().getType() == Material.TNT) {
                    player.getInventory().removeItem(new ItemStack(Material.TNT, 1));
                    TNTPrimed tnt = player.getWorld().spawn(player.getLocation(), TNTPrimed.class);
                    tnt.setVelocity(player.getLocation().getDirection().normalize().multiply(2));
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Main.arenas.forEach(arena -> {
            arena.onDeath(event);
        });
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
       Main.arenas.forEach(arena -> {
           arena.onDamage(event);
       });
    }
    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Main.arenas.forEach(arena -> {
            arena.onLeave(event);
        });
    }
}
