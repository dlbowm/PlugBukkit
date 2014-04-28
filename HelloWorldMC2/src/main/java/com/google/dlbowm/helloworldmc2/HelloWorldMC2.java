package com.google.dlbowm.helloworldmc2;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class HelloWorldMC2 extends JavaPlugin implements Listener {
	// create a new virtual inventory with 50 diamonds!!
	private Inventory fake; // = getServer().createInventory(null,
							// InventoryType.ENDER_CHEST);
	private FileConfiguration poopcfg;

	@Override
	public void onEnable() {
		// TODO Insert logic to be performed when the plugin is enabled
		getLogger().info("onEnable has been invoked!");
		getServer().getPluginManager().registerEvents(this, this);
		poopcfg = new YamlConfiguration();
		// File poopfile = new File(getDataFolder(), "plugins/poop.yml");
		File poopfile = new File("plugins/poop.yml");
		try {
			poopcfg.load(poopfile);
			getLogger().info(
					"Loading Poopy StinkMeal" + poopcfg.getString("name")
							+ ". It is used for: "
							+ poopcfg.getString("commands.poop.description")
							+ " Version is: " + poopcfg.getString("version"));
		} catch (Exception e) {
			getLogger().info(
					"Error loading poop.yml " + poopfile.getAbsolutePath());
		}

		// poopcfg.getM
		@SuppressWarnings("unchecked")
		List<String> keys = (List<String>) poopcfg.getList("commands.poop.");
		// Iterator<String> keysIter = keys.iterator();
		String foostr;
		// getLogger().info("foo " + keys.toString());
		// while(keys.listIterator().next() != null ){
		// getLogger().info("Key is hi" );// + foostr);
		// }

	}

	@Override
	public void onDisable() {
		// TODO Insert logic to be performed when the plugin is disabled
		getLogger().info("onDisable has been invoked!");
		try {
			// poopcfg.save(getFile());
			poopcfg.save("plugins/poop.yml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("poop")) { // If the player typed
														// /basic then do the
														// following...
			// doSomething
			getLogger().info("Is *VERY* smelly!");
			sender.sendMessage("~~~~~ Is *VERY* smelly! ~~~~~");
			Player player = (Player) sender;
			// open the normal player inventory
			// player.openInventory(player.getInventory());

			// fake = getServer().createInventory(null, InventoryType.CHEST);
			fake = getServer().createInventory(null, 9, "PoopBasket");
			// Inventory fake = getServer().createInventory(null,
			// InventoryType.ENDER_CHEST);
			ItemStack mybling = new ItemStack(Material.DIAMOND, 50);
			// mybling.setType(Material.DIAMOND);
			// mybling.setItemMeta(mybling.getItemMeta().setDisplayName("MyBLING!!"));
			// mybling.setItemMeta(mybling.getItemMeta().setDisplayName("BlingBling"));
			fake.addItem(mybling);
			player.openInventory(fake);
			poopcfg.set("playercoords." + player.getPlayerListName() + ".x",
					player.getLocation().getX());
			poopcfg.set("playercoords." + player.getPlayerListName() + ".y",
					player.getLocation().getY());
			poopcfg.set("playercoords." + player.getPlayerListName() + ".z",
					player.getLocation().getZ());
			return true;
		} // If this has happened the function will return true.
			// If this hasn't happened the value of false will be returned.
		else if (cmd.getName().equalsIgnoreCase("fart")) { // If the player
															// typed
			// /basic then do the
			// following...
			// doSomething
			getLogger().info("Going to where you last pooped");
			sender.sendMessage("Going to where you last poooped");
			Player player = (Player) sender;
			double atX = poopcfg.getDouble("playercoords." + player.getPlayerListName() + ".x");
			double atY = poopcfg.getDouble("playercoords." + player.getPlayerListName() + ".y");
			double atZ = poopcfg.getDouble("playercoords." + player.getPlayerListName() + ".z");
			
			//poopcfg.get;
			sender.sendMessage("Position is " + atX + " " + atY + " " + atZ);
			Location meAt = player.getLocation();
			meAt.setX(atX);
			meAt.setY(atY);
			meAt.setZ(atZ);
			//player.teleport(new Location(null, atX, atY, atZ));
			player.teleport(meAt);

			return true;
		}
		return false;
	}

	@EventHandler
	public void myClicketyClickety(InventoryClickEvent event) {
		// getLogger().info("YOU CLICKED A SOMETHING");
		Player p = (Player) event.getWhoClicked();
		// Only use this handler if it's the server's virtual inventory
		p.sendMessage("This inventory is: " + event.getInventory().getName());

		if (event.getInventory().getName() == "PoopBasket") {
			if (event.isShiftClick()) {
				p.sendMessage("Shift Click by " + p.getDisplayName()
						+ ".  NO BLING FOR YOU!!");
				ItemStack mybling = new ItemStack(Material.DIRT, 5);
				Inventory pinv = p.getInventory();
				pinv.addItem(mybling);
				event.setCancelled(true);
			} else if (event.isLeftClick()) {
				p.sendMessage("Left Click by " + p.getDisplayName()
						+ ".  NO BLING FOR YOU!!");
				ItemStack mybling = new ItemStack(Material.WOOD_DOOR);
				Inventory pinv = p.getInventory();
				pinv.addItem(mybling);
				event.setCancelled(true);
			} else if (event.isRightClick()) {
				p.sendMessage("Right Click by " + p.getDisplayName()
						+ ".  NO BLING FOR YOU!!");
				ItemStack mybling = new ItemStack(Material.ROTTEN_FLESH, 8);
				Inventory pinv = p.getInventory();
				pinv.addItem(mybling);
				Location me = p.getLocation();
				me.add(5, 10, 20);
				p.teleport(me);
				event.setCancelled(true);
			}
		}
	}
}
