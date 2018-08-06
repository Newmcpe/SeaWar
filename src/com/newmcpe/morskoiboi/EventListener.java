package com.newmcpe.morskoiboi;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Newmcpe
 * VK: vk.com/newmcpead
 */
public class EventListener implements Listener {
    public static ArrayList<Player> playersinarena = new ArrayList<Player>();
    public static ArrayList<Player> players1 = new ArrayList<Player>();
    public static ArrayList<Player> players2 = new ArrayList<Player>();
    public static Map<Player, String> playerarena = new HashMap<Player, String>();
    public static int gameStatus = 0;
    public static int gameStatusSkyBattle;
    private Main main;

    EventListener(Main main) {
        this.main = main;


    }

    public static void openGUIChooseChip(Player p) {
        Inventory inv = Bukkit.createInventory(null, 9, "§aВыбор корабля");

        ItemStack is = new ItemStack(Material.MINECART, 1);
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName("1 корабль");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("§aНа арене: §5[" + (players1.size()) + "/5]");
        meta.setLore(lore);
        is.setItemMeta(meta);
        inv.setItem(0, is);

        is = new ItemStack(Material.MINECART, 1);
        meta = is.getItemMeta();
        meta.setDisplayName("2 корабль");
        lore = new ArrayList<String>();
        lore.add("§aНа арене: §5[" + (players2.size()) + "/5]");
        meta.setLore(lore);
        is.setItemMeta(meta);
        inv.setItem(8, is);

        p.openInventory(inv);
    }

    public static void openGUIMenu(Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, "§aМеню сервера");

        ItemStack is = new ItemStack(Material.BEACON, 1);
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName("§aВарпы");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("§6Список варпов сервера");
        meta.setLore(lore);
        is.setItemMeta(meta);
        inv.setItem(0, is);

        is = new ItemStack(Material.ELYTRA, 1);
        meta = is.getItemMeta();
        meta.setDisplayName("§aМинирежимы");
        lore = new ArrayList<String>();
        lore.add("§6Список миниигр сервера");
        meta.setLore(lore);
        is.setItemMeta(meta);
        inv.setItem(1, is);


        p.openInventory(inv);
    }

    public static void openGUIWarps(Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, "§aВарпы сервера");

        ItemStack is = new ItemStack(Material.ENCHANTMENT_TABLE, 1);
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName("§aЗачаровальня");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("§6Зачаруй свои предметы!");
        meta.setLore(lore);
        is.setItemMeta(meta);
        inv.setItem(0, is);
        p.openInventory(inv);

        is = new ItemStack(Material.SLIME_BLOCK, 1);
        meta = is.getItemMeta();
        meta.setDisplayName("§aБатуты");
        lore = new ArrayList<String>();
        lore.add("§6Попрыгай до смерти!");
        meta.setLore(lore);
        is.setItemMeta(meta);
        inv.setItem(1, is);
        p.openInventory(inv);

        is = new ItemStack(Material.GOLD_INGOT, 1);
        meta = is.getItemMeta();
        meta.setDisplayName("§aЦерковь");
        lore = new ArrayList<String>();
        lore.add("§6Найди свою вторую половинку!");
        meta.setLore(lore);
        is.setItemMeta(meta);
        inv.setItem(2, is);
        p.openInventory(inv);

        is = new ItemStack(Material.COOKED_BEEF, 1);
        meta = is.getItemMeta();
        meta.setDisplayName("§aМакДональдс");
        lore = new ArrayList<String>();
        lore.add("§6Покушай!");
        meta.setLore(lore);
        is.setItemMeta(meta);
        inv.setItem(3, is);
        p.openInventory(inv);

        is = new ItemStack(Material.EMERALD, 1);
        meta = is.getItemMeta();
        meta.setDisplayName("§aСбербанк");
        lore = new ArrayList<String>();
        lore.add("§6Сделай свой вклад!");
        meta.setLore(lore);
        is.setItemMeta(meta);
        inv.setItem(4, is);
        p.openInventory(inv);

        is = new ItemStack(Material.IRON_FENCE, 1);
        meta = is.getItemMeta();
        meta.setDisplayName("§aТюрьма");
        lore = new ArrayList<String>();
        lore.add("§6Посмотри какой твоя жизнь может стать");
        meta.setLore(lore);
        is.setItemMeta(meta);
        inv.setItem(5, is);
        p.openInventory(inv);
    }

    public static void openGUIChooseMinigame(Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, "§aВыбор миниигры");

        ItemStack is = new ItemStack(Material.MINECART, 1);
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName("§aSeaWar");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("§6Уникальная самописная игра про войну двух враждебных кораблей");
        meta.setLore(lore);
        is.setItemMeta(meta);
        inv.setItem(0, is);
        p.openInventory(inv);
    }

    @EventHandler
    public void respawn(PlayerRespawnEvent e) {
        ItemStack is = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName("§aМеню сервера");
        is.setItemMeta(meta);
        e.getPlayer().getInventory().addItem(is);
        e.getPlayer().sendMessage("§8(§a§lMine§c§lLuxe§8) §aВы телепортированы на §eспавн");
        e.setRespawnLocation(new Location(Bukkit.getWorld("world"), -62.500, 65, 1277.500, 90, 1));
    }

    @EventHandler
    public void anticommand(PlayerCommandPreprocessEvent e) {

        if (e.getPlayer().getWorld().getName().equalsIgnoreCase("battle")) {
            if (e.getMessage().contains("gm") || e.getMessage().contains("gamemode") || e.getMessage().contains("spawn") || e.getMessage().contains("rtp") || e.getMessage().contains("gmc") || e.getMessage().contains("menu") || e.getMessage().contains("minigames") || e.getMessage().contains("fly")) {
                e.setCancelled(true);
                e.getPlayer().sendMessage("§8(§a§lMine§c§lLuxe§8) §aЗапрещено использовать данные команды во время §cигры!");
                return;
            }
        }
    }

    @EventHandler
    public void onjoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        main.getName();
        ItemStack is = new ItemStack(Material.NETHER_STAR, 1); //asd
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName("§aМеню сервера");
        is.setItemMeta(meta);
        event.getPlayer().getInventory().addItem(is);
        event.getPlayer().sendMessage("§8(§a§lMine§c§lLuxe§8) §aВы телепортированы на §eспавн");
        event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), -62.500, 65, 1277.500, 90, 1));
    }

    @EventHandler
    public void leave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        String playerName = p.getName();
        e.setQuitMessage(null);
        if (p.getWorld().getName().equalsIgnoreCase("battle")) {
            playersinarena.remove(p);
            Utils.sendMessageToPlayers(playersinarena, "§0[§eSeaWar§0] §6" + playerName + " §cвышел");
            if (playerarena.get(p).equalsIgnoreCase("first")) {
                players1.remove(p);
                main.getLogger().info(String.valueOf(players1.size()));
                if (players1.size() == 0) {
                    Utils.sendTitlesToPlayers(players1, "§aПоздравляем! Ваша команда победила!", "");
                    Bukkit.broadcastMessage("§0[§eSeaWar§0] §aВторой корабль победил в игре!");
                    EventListener.playersinarena = new ArrayList<Player>();
                    EventListener.players1 = new ArrayList<Player>();
                    EventListener.players2 = new ArrayList<Player>();
                    EventListener.playerarena = new HashMap<Player, String>();
                    EventListener.gameStatus = 0;
                    for (Player player : Bukkit.getServer().getWorld("battle").getPlayers()) {
                        player.teleport(new Location(Bukkit.getWorld("world"), -62, 65, 1277));
                        player.getInventory().clear();
                        player.setHealth(20);
                        player.setFoodLevel(20);
                        player.setGameMode(GameMode.SURVIVAL);
                        ItemStack is = new ItemStack(Material.NETHER_STAR, 1);
                        ItemMeta meta = is.getItemMeta();
                        meta.setDisplayName("§aМеню сервера");
                        is.setItemMeta(meta);
                        player.getPlayer().getInventory().addItem(is);
                    }
                    Bukkit.unloadWorld("battle", false);
                    Bukkit.createWorld(WorldCreator.name("battle"));
                    Bukkit.getWorld("battle").setAutoSave(false);
                }
            } else if (playerarena.get(p).equalsIgnoreCase("second")) {
                players2.remove(p);
                main.getLogger().info(String.valueOf(players2.size()));
                if (players2.size() == 0) {
                    Utils.sendTitlesToPlayers(players2, "§aПоздравляем! Ваш корабль победил!", "");
                    Bukkit.broadcastMessage("§0[§eSeaWar§0] §aПервый корабль победил в игре!");
                    EventListener.playersinarena = new ArrayList<Player>();
                    EventListener.players1 = new ArrayList<Player>();
                    EventListener.players2 = new ArrayList<Player>();
                    EventListener.playerarena = new HashMap<Player, String>();
                    EventListener.gameStatus = 0;
                    for (Player player : Bukkit.getServer().getWorld("battle").getPlayers()) {
                        player.teleport(new Location(Bukkit.getWorld("world"), -62, 65, 1277));
                        player.getInventory().clear();
                        player.setHealth(20);
                        player.setFoodLevel(20);
                        player.setGameMode(GameMode.SURVIVAL);
                        ItemStack is = new ItemStack(Material.NETHER_STAR, 1);
                        ItemMeta meta = is.getItemMeta();
                        meta.setDisplayName("§aМеню сервера");
                        is.setItemMeta(meta);
                        player.getPlayer().getInventory().addItem(is);
                    }
                    Bukkit.unloadWorld("battle", false);
                    Bukkit.createWorld(WorldCreator.name("battle"));
                    Bukkit.getWorld("battle").setAutoSave(false);
                }
            }
        }
    }


    @EventHandler
    public void seawarattack(EntityDamageEvent e) throws IOException {
        Entity entity = e.getEntity();
        if (entity instanceof Player) {
            if (entity.getWorld().getName().equalsIgnoreCase("battle") && e instanceof EntityDamageByEntityEvent) {
                Entity attacker = ((EntityDamageByEntityEvent) e).getDamager();
                if (attacker instanceof Arrow) {
                    Player plada = (Player) ((Arrow) attacker).getShooter();
                    if (players1.contains(entity) && players1.contains(plada)) {
                        e.setCancelled(true);
                    }
                    if (players2.contains(entity) && players2.contains(plada)) {
                        e.setCancelled(true);
                    }
                }
                if (players1.contains(entity) && players1.contains(attacker)) {
                    e.setCancelled(true);
                }
                if (players2.contains(entity) && players2.contains(attacker)) {
                    e.setCancelled(true);
                }
            }
            if (e.getDamage() >= ((Player) entity).getHealth() && entity.getWorld().getName().equalsIgnoreCase("battle")) {
                ((Player) entity).getInventory().clear();
                e.setCancelled(true);
                Player p = (Player) entity;
                String playerName = p.getName();
                if (p.getWorld().getName().equalsIgnoreCase("battle")) {
                    Utils.sendMessageToPlayers(playersinarena, "§0[§eSeaWar§0] §6" + playerName + " §cумер");
                    p.teleport(new Location(Bukkit.getWorld("world"), -62, 65, 1277));
                    ItemStack is = new ItemStack(Material.NETHER_STAR, 1); //asd
                    ItemMeta meta = is.getItemMeta();
                    meta.setDisplayName("§aМеню сервера");
                    is.setItemMeta(meta);
                    p.getPlayer().getInventory().addItem(is);
                    playersinarena.remove(p);
                    main.getLogger().info(playerarena.get(p));
                    if (playerarena.get(p).equalsIgnoreCase("first")) {
                        players1.remove(p);
                        main.getLogger().info(String.valueOf(players1.size()));
                        if (players1.size() == 0) {
                            Utils.sendTitlesToPlayers(players1, "§aПоздравляем! Ваша команда победила!", "");
                            Bukkit.broadcastMessage("§0[§eSeaWar§0] §aВторой корабль победил в игре!");
                            EventListener.playersinarena = new ArrayList<Player>();
                            EventListener.players1 = new ArrayList<Player>();
                            EventListener.players2 = new ArrayList<Player>();
                            EventListener.playerarena = new HashMap<Player, String>();
                            EventListener.gameStatus = 0;
                            for (Player player : Bukkit.getServer().getWorld("battle").getPlayers()) {
                                player.teleport(new Location(Bukkit.getWorld("world"), -62, 65, 1277));
                                player.getInventory().clear();
                                player.setHealth(20);
                                player.setFoodLevel(20);
                                player.setGameMode(GameMode.SURVIVAL);
                                is = new ItemStack(Material.NETHER_STAR, 1);
                                meta = is.getItemMeta();
                                meta.setDisplayName("§aМеню сервера");
                                is.setItemMeta(meta);
                                player.getPlayer().getInventory().addItem(is);
                            }
                            Bukkit.unloadWorld("battle", false);
                            Bukkit.createWorld(WorldCreator.name("battle"));
                            Bukkit.getWorld("battle").setAutoSave(false);
                        }
                    } else if (playerarena.get(p).equalsIgnoreCase("second")) {
                        players2.remove(p);
                        main.getLogger().info(String.valueOf(players2.size()));
                        if (players2.size() == 0) {
                            Utils.sendTitlesToPlayers(players2, "§aПоздравляем! Ваш корабль победил!", "");
                            Bukkit.broadcastMessage("§0[§eSeaWar§0] §aПервый корабль победил в игре!");
                            EventListener.playersinarena = new ArrayList<Player>();
                            EventListener.players1 = new ArrayList<Player>();
                            EventListener.players2 = new ArrayList<Player>();
                            EventListener.playerarena = new HashMap<Player, String>();
                            EventListener.gameStatus = 0;
                            for (Player player : Bukkit.getServer().getWorld("battle").getPlayers()) {
                                player.teleport(new Location(Bukkit.getWorld("world"), -62, 65, 1277));
                                player.getInventory().clear();
                                player.setHealth(20);
                                player.setFoodLevel(20);
                                player.setGameMode(GameMode.SURVIVAL);
                                is = new ItemStack(Material.NETHER_STAR, 1);
                                meta = is.getItemMeta();
                                meta.setDisplayName("§aМеню сервера");
                                is.setItemMeta(meta);
                                player.getPlayer().getInventory().addItem(is);
                            }
                            Bukkit.unloadWorld("battle", false);
                            Bukkit.createWorld(WorldCreator.name("battle"));
                            Bukkit.getWorld("battle").setAutoSave(false);
                        }
                    }

                }
            }
        }
    }

    @EventHandler
    public void flywar(EntityDamageEvent e) {

    }

    @EventHandler
    public void death(PlayerDeathEvent e) throws IOException {
        e.setDeathMessage(null);
    }

    private void goCancel(int taskid) {
        Bukkit.getScheduler().cancelTask(taskid);
        Utils.sendMessageToPlayers(playersinarena, "§0[§eSeaWar§0] §aТест: игра должна остановится!");
    }

    @EventHandler
    public void clickinv(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        String playerName = p.getName();
        Inventory inventory = e.getInventory();
        if (inventory.getName().equalsIgnoreCase("§aВыбор корабля")) {
            e.setCancelled(true);
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("1 корабль")) {
                if (gameStatus == 2) {
                    p.sendMessage("§0[§eSeaWar§0] §aИгра уже идет!");
                    return;
                }
                p.getInventory().clear();
                p.setGameMode(GameMode.ADVENTURE);
                p.setHealth(20);
                p.setFoodLevel(20);
                p.teleport(new Location(Bukkit.getWorld("battle"), 31, 70, 27));
                playersinarena.add(p);
                players1.add(p);
                playerarena.put(p, "first");
                Utils.sendMessageToPlayers(playersinarena, "§0[§eSeaWar§0] §6" + playerName + " §aприсоединился к игре! §5[" + playersinarena.size() + "/10]");
                if (playersinarena.size() == 2) {
                    gameStatus = 1;
                    int[] countdown = {31};
                    new BukkitRunnable() {
                        public void run() {
                            if (countdown[0] > 0) {
                                countdown[0]--;
                            }
                            if (countdown[0] == 30) {
                                Utils.sendMessageToPlayers(playersinarena, "§0[§eSeaWar§0] §aИгра начнется через 30 секунд!");
                            }
                            if (countdown[0] == 10) {
                                Utils.sendMessageToPlayers(playersinarena, "§0[§eSeaWar§0] §aИгра начнется через 10 секунд!");
                            }
                            if (countdown[0] <= 5) {
                                Utils.sendMessageToPlayers(playersinarena, "§0[§eSeaWar§0] §aИгра начнется через " + countdown[0] + " секунд!");
                            }
                            if (countdown[0] == 0) {
                                Utils.sendMessageToPlayers(playersinarena, "§0[§eSeaWar§0] §aИгра началась!");
                                gameStatus = 2;
                                for (Player govnoplayer : playersinarena) {
                                    govnoplayer.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
                                    govnoplayer.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                                    govnoplayer.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
                                    govnoplayer.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
                                    govnoplayer.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
                                    govnoplayer.getInventory().addItem(new ItemStack(Material.BOW));
                                    govnoplayer.getInventory().addItem(new ItemStack(Material.ARROW, 64));
                                    govnoplayer.getInventory().addItem(new ItemStack(Material.TNT, 320));
                                    govnoplayer.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 2));
                                    govnoplayer.getInventory().addItem(new ItemStack(Material.BOAT, 1));
                                    govnoplayer.getInventory().addItem(new ItemStack(Material.getMaterial(364), 32));
                                    govnoplayer.setGameMode(GameMode.SURVIVAL);
                                }
                                cancel();
                            }
                        }
                    }.runTaskTimer(main, 20L, 20L);
                }
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("2 корабль")) {
                if (gameStatus == 2) {
                    p.sendMessage("§0[§eSeaWar§0] §aИгра уже идет!");
                    return;
                }
                p.getInventory().clear();
                p.setGameMode(GameMode.ADVENTURE);
                p.setHealth(20);
                p.setFoodLevel(20);
                p.teleport(new Location(Bukkit.getWorld("battle"), -42, 70, -27));
                playersinarena.add(p);
                players2.add(p);
                playerarena.put(p, "second");
                Utils.sendMessageToPlayers(playersinarena, "§0[§eSeaWar§0] §6" + playerName + " §aприсоединился к игре! §5[" + playersinarena.size() + "/10]");
                if (playersinarena.size() == 2) {
                    gameStatus = 1;
                    int[] countdown = {31};
                    new BukkitRunnable() {
                        public void run() {
                            if (countdown[0] > 0) {
                                countdown[0]--;
                            }
                            if (countdown[0] == 30) {
                                Utils.sendMessageToPlayers(playersinarena, "§0[§eSeaWar§0] §aИгра начнется через 30 секунд!");
                            }
                            if (countdown[0] == 10) {
                                Utils.sendMessageToPlayers(playersinarena, "§0[§eSeaWar§0] §aИгра начнется через 10 секунд!");
                            }
                            if (countdown[0] <= 5) {
                                Utils.sendMessageToPlayers(playersinarena, "§0[§eSeaWar§0] §aИгра начнется через " + countdown[0] + " секунд!");
                            }
                            if (countdown[0] == 0) {
                                Utils.sendMessageToPlayers(playersinarena, "§0[§eSeaWar§0] §aИгра началась!");
                                gameStatus = 2;
                                for (Player govnoplayer : playersinarena) {
                                    govnoplayer.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
                                    govnoplayer.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                                    govnoplayer.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
                                    govnoplayer.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
                                    govnoplayer.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
                                    govnoplayer.getInventory().addItem(new ItemStack(Material.BOW));
                                    govnoplayer.getInventory().addItem(new ItemStack(Material.ARROW, 64));
                                    govnoplayer.getInventory().addItem(new ItemStack(Material.TNT, 320));
                                    govnoplayer.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 2));
                                    govnoplayer.getInventory().addItem(new ItemStack(Material.BOAT, 1));
                                    govnoplayer.getInventory().addItem(new ItemStack(Material.getMaterial(364), 32));
                                    govnoplayer.setGameMode(GameMode.SURVIVAL);
                                }
                                cancel();
                            }
                        }
                    }.runTaskTimer(main, 20L, 20L);
                }
            }
        }
        if (inventory.getName().contains("Выбор миниигры")) {
            e.setCancelled(true);
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("SeaWar")) {
                p.closeInventory();
                openGUIChooseChip(p);
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("SkyBattle")) {
                if (gameStatusSkyBattle == 2) {
                    p.sendMessage("§0[§eSkyBatle§0] §aИгра уже идет!");
                    return;
                }
                p.getInventory().clear();
                p.setGameMode(GameMode.ADVENTURE);
                p.setHealth(20);
                p.setFoodLevel(20);
                p.teleport(new Location(Bukkit.getWorld("flywar"), -42, 70, -27));
                playersinarena.add(p);
                players2.add(p);
                playerarena.put(p, "second");
                Utils.sendMessageToPlayers(playersinarena, "§0[§eSkyBatle§0] §6" + playerName + " §aприсоединился к игре! §5[" + playersinarena.size() + "/10]");
                if (playersinarena.size() == 2) {
                    gameStatus = 1;
                    int[] countdown = {31};
                    new BukkitRunnable() {
                        public void run() {
                            if (countdown[0] > 0) {
                                countdown[0]--;
                            }
                            if (countdown[0] == 30) {
                                Utils.sendMessageToPlayers(playersinarena, "§0[§eSkyBatle§0] §aИгра начнется через 30 секунд!");
                            }
                            if (countdown[0] == 10) {
                                Utils.sendMessageToPlayers(playersinarena, "§0[§eSkyBatle§0] §aИгра начнется через 10 секунд!");
                            }
                            if (countdown[0] <= 5) {
                                Utils.sendMessageToPlayers(playersinarena, "§0[§eSeaWar§0] §aИгра начнется через " + countdown[0] + " секунд!");
                            }
                            if (countdown[0] == 0) {
                                Utils.sendMessageToPlayers(playersinarena, "§0[§eSeaWar§0] §aИгра началась!");
                                gameStatus = 2;
                                for (Player govnoplayer : playersinarena) {
                                    govnoplayer.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
                                    govnoplayer.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                                    govnoplayer.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
                                    govnoplayer.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
                                    govnoplayer.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
                                    govnoplayer.getInventory().addItem(new ItemStack(Material.BOW));
                                    govnoplayer.getInventory().addItem(new ItemStack(Material.ARROW, 64));
                                    govnoplayer.getInventory().addItem(new ItemStack(Material.TNT, 320));
                                    govnoplayer.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 2));
                                    govnoplayer.getInventory().addItem(new ItemStack(Material.BOAT, 1));
                                    govnoplayer.getInventory().addItem(new ItemStack(Material.getMaterial(364), 32));
                                    govnoplayer.setGameMode(GameMode.SURVIVAL);
                                }
                                cancel();
                            }
                        }
                    }.runTaskTimer(main, 20L, 20L);
                }
                p.closeInventory();
            }
        }
        if (inventory.getName().contains("Меню сервера")) {
            e.setCancelled(true);
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Варпы")) {
                p.closeInventory();
                openGUIWarps(p);
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Минирежимы")) {
                p.closeInventory();
                openGUIChooseMinigame(p);
            }
        }
        if (inventory.getName().contains("Варпы сервера")) {
            e.setCancelled(true);
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Зачаровальня")) {
                p.teleport(new Location(Bukkit.getWorld("world"), -111, 70, 1287));
                p.closeInventory();
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Пункт обмена")) {
                p.teleport(new Location(Bukkit.getWorld("world"), -111, 70, 1270));
                p.closeInventory();
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Починка")) {
                p.teleport(new Location(Bukkit.getWorld("world"), -129, 70, 1254));
                p.closeInventory();
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("МакДональдс")) {
                p.teleport(new Location(Bukkit.getWorld("world"), -133, 70, 1254));
                p.closeInventory();
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Церковь")) {
                p.teleport(new Location(Bukkit.getWorld("world"), -133, 70, 1240));
                p.closeInventory();
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Рынок")) {
                p.teleport(new Location(Bukkit.getWorld("world"), -133, 70, 1296));
                p.closeInventory();
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Сбербанк")) {
                p.teleport(new Location(Bukkit.getWorld("world"), -133, 70, 1320));
                p.closeInventory();
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Тюрьма")) {
                p.teleport(new Location(Bukkit.getWorld("world"), -128, 70, 1320));
                p.closeInventory();
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Батуты")) {
                p.teleport(new Location(Bukkit.getWorld("world"), -129, 70, 1240));
                p.closeInventory();
            }
        }
    }


    @EventHandler
    public void tap(PlayerInteractEvent e) throws IOException {
        Block block = e.getClickedBlock();
        if (block.getWorld().getName().equalsIgnoreCase("battle") && block.getType() == Material.CHEST) {
            e.setCancelled(true);
        }
        if (e.getPlayer().getItemInHand().getType() == Material.NETHER_STAR && e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("Меню сервера")) {
            openGUIMenu(e.getPlayer());
        }
    }
}
