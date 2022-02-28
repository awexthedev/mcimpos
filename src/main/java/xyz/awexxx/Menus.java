package xyz.awexxx;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffectType;
import org.json.JSONObject;

import redempt.redlib.inventorygui.InventoryGUI;
import redempt.redlib.inventorygui.ItemButton;
import redempt.redlib.itemutils.ItemBuilder;

public class Menus {
    public static InventoryGUI createVotingMenu() {
        InventoryGUI gui = new InventoryGUI(Bukkit.createInventory(null, 9, "Vote for someone!"));
        addPlayers(gui);
        return gui;
    }

    public static InventoryGUI createElectricMenu(Player player) {
        InventoryGUI gui = new InventoryGUI(Bukkit.createInventory(null, 36, "Electric Task"));

        ItemButton button1 = ItemButton.create(new ItemBuilder(Material.GRAY_CONCRETE)
        .setName("Wire"), e -> {
        player.sendMessage("Oops! Wrong wire. Try again.");

        gui.destroy();
        player.closeInventory();
    });

    ItemButton button2 = ItemButton.create(new ItemBuilder(Material.GRAY_CONCRETE)
    .setName("Wire"), e -> {
    player.sendMessage("Oops! Wrong wire. Try again.");

    gui.destroy();
    player.closeInventory();
});

    ItemButton button3 = ItemButton.create(new ItemBuilder(Material.GREEN_CONCRETE)
    .setName("Wire"), e -> {
    gui.destroy();
    player.closeInventory();
    player.sendMessage("Awesome, you completed your task!");
});

    ItemButton button4 = ItemButton.create(new ItemBuilder(Material.GRAY_CONCRETE)
    .setName("Wire"), e -> {
    player.sendMessage("Oops! Wrong wire. Try again.");
    gui.destroy();
    player.closeInventory();
});

    gui.addButton(button1, 12);
    gui.addButton(button2, 14);
    gui.addButton(button3, 21);
    gui.addButton(button4, 23);

        return gui;
    } 

    public static void displayVotingMenu(Player player) {
        InventoryGUI gui = createVotingMenu();
        gui.open(player);
    }

    public static void displayElectricMenu(Player player) {
        InventoryGUI gui = createElectricMenu(player);
        gui.open(player);
    }
    
    public static void addPlayers(InventoryGUI gui) {
        int i = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            ItemStack myAwesomeSkull = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short) 3);
            SkullMeta myAwesomeSkullMeta = (SkullMeta) myAwesomeSkull.getItemMeta();
            myAwesomeSkullMeta.setOwner(player.getName());
            myAwesomeSkull.setItemMeta(myAwesomeSkullMeta);
            
            ItemButton button = ItemButton.create(new ItemBuilder(myAwesomeSkull)
                .setName(player.getName()), e -> {
                JSONObject j = new JSONObject(JsonItemStack.toJson(e.getCurrentItem()));
                Voting.vote(Bukkit.getPlayer(j.getJSONObject("item-meta").getJSONObject("extra-meta").get("owner").toString()));

                gui.destroy();
                player.closeInventory();

                player.setWalkSpeed(0.2f);
                player.removePotionEffect(PotionEffectType.BLINDNESS);

            });

            gui.addButton(button, i);
            i++;
        }
    }
}
