package com.newmcpe.morskoiboi;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * Created by Newmcpe
 * VK: vk.com/newmcpead
 */
class ItemUtils {

     public static ItemStack create(Material material, int amount, String displayName, String lore1, String lore2, String lore3, String lore4, String lore5) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        if (displayName != null) {
            meta.setDisplayName(displayName);
        }
        ArrayList<String> lore = new ArrayList<String>();

        if (lore1 != null) {
            lore.add(lore1);

        }
        if (lore2 != null) {
            lore.add(lore2);

        }
        if (lore3 != null) {
            lore.add(lore3);

        }
        if (lore4 != null) {
            lore.add(lore4);

        }
        if (lore5 != null) {
            lore.add(lore5);

            meta.setLore(lore);
            item.setItemMeta(meta);

            return item;

        }
        return item;
    }

    public static ItemStack create(Material material, String displayName) {
        return create(material, 1, displayName, null, null, null, null, null);
    }


    public static ItemStack create(Material material, int amount, byte data, String displayName) {
        return create(material, amount, displayName, null, null, null, null, null);
    }
}