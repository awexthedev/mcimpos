package xyz.awexxx;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffectType;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.mask.Mask;
import org.ipvp.canvas.slot.Slot;
import org.ipvp.canvas.type.ChestMenu;
import org.json.JSONObject;

public class Menus {
    public static Menu createVotingMenu() {
        Menu menu = ChestMenu.builder(1)
        .title("Menu")
        .build();

        addPlayers(menu);
        return menu;
    }

    public static Menu createElectricMenu() {
        Menu menu = ChestMenu.builder(4) 
        .title("Electric")
        .build();

        addBorder(menu);
        menu.getSlot(12).setItem(new ItemStack(Material.GRAY_CONCRETE));
        menu.getSlot(14).setItem(new ItemStack(Material.GRAY_CONCRETE));
        menu.getSlot(21).setItem(new ItemStack(Material.GREEN_CONCRETE));
        menu.getSlot(23).setItem(new ItemStack(Material.GRAY_CONCRETE));
        addElecClick(menu.getSlot(21));

        return menu;
    } 

    public static void displayVotingMenu(Player player) {
        Menu menu = createVotingMenu();
        menu.open(player);
    }

    public static void displayElectricMenu(Player player) {
        Menu menu = createElectricMenu();
        menu.open(player);
    }

    public static void addClickHandler(Slot slot) {
        slot.setClickHandler((player, clicked) -> {
            JSONObject j = new JSONObject(JsonItemStack.toJson(clicked.getClickedSlot().getItem(player)));

            Voting.vote(Bukkit.getPlayer(j.getJSONObject("item-meta").getJSONObject("extra-meta").get("owner").toString()));
            clicked.getClickedMenu().close();
            player.setWalkSpeed(0.2f);
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.sendMessage("You voted! You can move while I decide.");
        });
    }

    public static void addElecClick(Slot slot) {
        slot.setClickHandler((player, clicked) -> {

            Bukkit.getLogger().info(clicked.getClickedSlot().getItem(player).toString());
            clicked.getClickedMenu().close();
            player.sendMessage("Task completed, continue to the next.");
        });
    }

    public static void addBorder (Menu menu) {
        Mask mask = BinaryMask.builder(menu)
        .item(new ItemStack(Material.GRAY_STAINED_GLASS_PANE))
        .pattern("111111111") // First row
        .pattern("100000001") // Second row
        .pattern("100000001") // Third row
        .pattern("111111111").build(); // Fourth row
        mask.apply(menu);
    }
    
    public static void addPlayers(Menu menu) {
        int i = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            ItemStack myAwesomeSkull = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short) 3);
            SkullMeta myAwesomeSkullMeta = (SkullMeta) myAwesomeSkull.getItemMeta();
            myAwesomeSkullMeta.setOwner(player.getName());
            myAwesomeSkull.setItemMeta(myAwesomeSkullMeta);
            menu.getSlot(i).setItem(myAwesomeSkull);

            addClickHandler(menu.getSlot(i));
            i++;
        }
    }
}
