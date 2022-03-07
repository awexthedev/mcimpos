package xyz.awexxx.main;

import xyz.awexxx.game.GameState;
import xyz.awexxx.game.Tasks;
import xyz.awexxx.game.Teams;
import xyz.awexxx.game.Voting;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import redempt.redlib.inventorygui.InventoryGUI;
import redempt.redlib.inventorygui.ItemButton;
import redempt.redlib.itemutils.ItemBuilder;

public class Menus {
    private static Integer i = 0;
    public static InventoryGUI createVotingMenu(Player player) {
        InventoryGUI gui = new InventoryGUI(Bukkit.createInventory(null, 9, "Vote for someone!"));
        addPlayers(gui, player);
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
    ChatUtils.sendToOne("Complete!", "Continue with your tasks..", player);
    Tasks.completeTask(player, "Tie up in Electrical!");

    if(GameState.isState(GameState.LIGHTS_OFF)) {
        GameState.setState(GameState.IN_GAME);
        for (Player loop : Bukkit.getOnlinePlayers()) {
            loop.removePotionEffect(PotionEffectType.BLINDNESS);
        }              
    }
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

    public static InventoryGUI createSabMenu(Player executor) {
        InventoryGUI gui = new InventoryGUI(Bukkit.createInventory(null, 9, "Sabotage"));

        ItemButton lightsCont = ItemButton.create(new ItemBuilder(Material.REDSTONE_LAMP)
        .setName("Shut the lights!"), e -> {
            executor.closeInventory();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!player.getName().equals(player.getName())) {
                    player.sendTitle("The lights are out!", "Turn them back on via Electrical!", 10, 70, 20);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999999, 1));
                }
            }
        });

        gui.addButton(lightsCont, 0);
        return gui;
    }

    public static InventoryGUI createGarbMenu(Player executor) {
        InventoryGUI gui = new InventoryGUI(Bukkit.createInventory(null, 9, "Garbage"));

        ItemButton button1 = ItemButton.create(new ItemBuilder(Material.GREEN_CONCRETE)
        .setLore("Click 4 times!")
        .setName("Garbage"), e -> {
            i++;

            if(i.equals(5)) {
                i = 0;
                gui.destroy();
                executor.closeInventory();

                ChatUtils.sendToOne("Complete!", "Continue with your tasks..", executor);
                Tasks.completeTask(executor, "Clear the garbage!");
            }
    });

        gui.addButton(button1, 0);
        return gui;
    }

    public static InventoryGUI createAstMenu(Player executor) {
        InventoryGUI gui = new InventoryGUI(Bukkit.createInventory(null, 27, "Asteroids"));
        addAsteroids(gui, executor);
        return gui;
    }

    public static InventoryGUI createSwipeMenu(Player executor) {
        InventoryGUI gui = new InventoryGUI(Bukkit.createInventory(null, 9, "Card Swipe"));
        addCard(gui, executor);
        return gui;
    }

    public static void displayVotingMenu(Player player) {
        InventoryGUI gui = createVotingMenu(player);
        gui.open(player);
    }

    public static void displayElectricMenu(Player player) {
        InventoryGUI gui = createElectricMenu(player);
        gui.open(player);
    }

    public static void displayGarbMenu(Player player) {
        InventoryGUI gui = createGarbMenu(player);
        gui.open(player);
    }

    public static void displaySaboMenu(Player player) {
        InventoryGUI gui = createSabMenu(player);
        gui.open(player);
    }

    public static void displayAstMenu(Player player) {
        InventoryGUI gui = createAstMenu(player);
        gui.open(player);
    }

    public static void displayCardMenu(Player player) {
        InventoryGUI gui = createSwipeMenu(player);
        gui.open(player);
    }

    private static Integer count = 0;
    
    public static void addPlayers(InventoryGUI gui, Player p) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            ItemButton button = ItemButton.create(new ItemBuilder(Teams.getColor(p))
                .setName(player.getName()), e -> {
                Voting.vote(Bukkit.getPlayer(e.getCurrentItem().getItemMeta().getDisplayName()));

                gui.destroy();
                player.closeInventory();

                player.setWalkSpeed(0.2f);
                player.removePotionEffect(PotionEffectType.BLINDNESS);
            });

            i = 0;
            gui.addButton(button, i);
            i++;
        }
    }

    public static void addAsteroids(InventoryGUI gui, Player p) {
            ItemButton buttons = ItemButton.create(new ItemBuilder(Material.GREEN_CONCRETE)
            .setLore("Click me!")
            .setName("Asteroid"), e -> {
                count++;

                if(count == 5) {
                    count = 0;
                    gui.destroy();
                    p.closeInventory();

                    ChatUtils.sendToOne("Complete!", "Continue with your tasks..", p);
                    Tasks.completeTask(p, "Hit the asteroids!");
                } else {
                    addAsteroids(gui, p);
                }
        });

        Random rand = new Random();
        gui.addButton(buttons, rand.nextInt(26 - 0 + 1) + 0);
    }

    public static void addCard(InventoryGUI gui, Player p) {
            ItemButton buttons = ItemButton.create(new ItemBuilder(Material.GREEN_CONCRETE)
            .setLore("Click me!")
            .setName("Card"), e -> {
                ItemButton newButton = ItemButton.create(new ItemBuilder(Material.GREEN_CONCRETE)
                .setLore("Click me!")
                .setName("Asteroid"), ev -> {
                    p.closeInventory();
                    gui.destroy();
                    ChatUtils.sendToOne("Complete!", "Continue with your tasks..", p);
            });
            gui.addButton(newButton, 8);
        });
        gui.addButton(buttons, 0);
    }
}
