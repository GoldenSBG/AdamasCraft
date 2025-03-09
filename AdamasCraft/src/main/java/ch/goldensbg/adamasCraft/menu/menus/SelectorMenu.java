package ch.goldensbg.adamasCraft.menu.menus;

import ch.goldensbg.aurumcraft.events.LobbyTeleportEvent;
import ch.goldensbg.aurumcraft.main.Utils;
import ch.goldensbg.aurumcraft.AurumCraft;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SelectorMenu implements Listener {

	int task;
	AurumCraft plugin;
	FileConfiguration cfg;
	public static List<Inventory> lobbyinvs = new ArrayList<>();

	public SelectorMenu(AurumCraft main, FileConfiguration config) {
		this.cfg = config;
		this.plugin = main;
	}

	public static void initializeWorldItems(Inventory inv, Player player) {
		if(inv.getContents() != null) {
			for(int i = 0; i < inv.getSize() ; i++) {
				if(inv.getItem(i) == null) {
					inv.setItem(i, createGuiItem(Material.BLACK_STAINED_GLASS_PANE, " "));
				}
			}
			inv.setItem(13, createGuiItem(Material.GRASS_BLOCK,
					Utils.format(player, "&5Craft&6Attack", AurumCraft.getPrefix()), // Name
					Utils.format(player, "&7Players: &7(&b%ca_onlineplayers%&7/&b%ca_players%&7)", AurumCraft.getPrefix()),
					Utils.format(player, "&2Hier kommst du zur CraftAttack Welt!", AurumCraft.getPrefix()), // Lore...
					Utils.format(player, "&4Natürlich nur wenn du ein Teilnehmer bist!", AurumCraft.getPrefix())));
			inv.setItem(22, createGuiItem(Material.BEACON,
					Utils.format(player, "&4Random Lobby", AurumCraft.getPrefix()), // Name
					Utils.format( player, "&7Players: (&b%ca_playersinlobby%&7/&b" + Bukkit.getOnlinePlayers().size() + "&7)", AurumCraft.getPrefix()),
					Utils.format(player, "&2Teleportieren dich zu einer Random Lobby", AurumCraft.getPrefix()))); // Lore
		}
	}

	protected static ItemStack createGuiItem(final Material material, final String name, final String... lore) {
		final ItemStack item = new ItemStack(material, 1);
		final ItemMeta meta = item.getItemMeta();

		assert meta != null;
		meta.setDisplayName(name);

		meta.setLore(Arrays.asList(lore));

		item.setItemMeta(meta);

		return item;
	}

	public static void openSelectorMenu(Player player) {
		Inventory inventory = Bukkit.createInventory(null, 45, "§5§lSelector");
		initializeWorldItems(inventory, player);
		lobbyinvs.add(inventory);
		player.openInventory(inventory);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(final InventoryClickEvent e) {
		if(!lobbyinvs.contains(e.getInventory())) return;

		e.setCancelled(true);

		final ItemStack clickedItem = e.getCurrentItem();

		if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

		Player player = (Player) e.getWhoClicked();
		LobbyTeleportEvent lobbyTeleportEvent;
		if(clickedItem.getType() == Material.GRASS_BLOCK) {
			if(clickedItem.getItemMeta().getDisplayName().equals(Utils.format(null, "&5Craft&6Attack", AurumCraft.getPrefix()))) {
				World world = plugin.getCraftAttackWorld();
				if (world == null)
					world = new WorldCreator(Objects.requireNonNull(plugin.spawnconfig.getString("CraftAttack.Spawn.World"))).createWorld();

				lobbyTeleportEvent = new LobbyTeleportEvent(player, player.getLocation(), world.getSpawnLocation(), "CraftAttack", Utils.format(player, "&4Sending you to the Craft Attack World!", AurumCraft.getPrefix()));
				Bukkit.getPluginManager().callEvent(lobbyTeleportEvent);
				if(!lobbyTeleportEvent.isCancelled()) {
					player.sendMessage(lobbyTeleportEvent.getMessage());
					player.teleport(lobbyTeleportEvent.getEndlocation());
					player.closeInventory();
				}
			}
		}
		if(clickedItem.getType() == Material.BEACON) {
			if(clickedItem.getItemMeta().getDisplayName().equals(Utils.format(player, "&4Random Lobby", AurumCraft.getPrefix()))) {
				if(!plugin.isLobbySystemEnabled()) {
					player.sendMessage("§cLobby System is disabled!");
					return;
				}
				World randomLobby = Utils.randomLobby();
				lobbyTeleportEvent = new LobbyTeleportEvent(player, player.getLocation(), randomLobby.getSpawnLocation(), "RandomLobby", Utils.format(player, "&4Sending you to a Random Lobby!", AurumCraft.getPrefix()));
				Bukkit.getPluginManager().callEvent(lobbyTeleportEvent);
				if(!lobbyTeleportEvent.isCancelled()) {
					player.sendMessage(lobbyTeleportEvent.getMessage());
					player.teleport(lobbyTeleportEvent.getEndlocation());
					player.closeInventory();
				}
			}
		}
	}

	@EventHandler
	public void onLobbyTP(LobbyTeleportEvent e) {
		if(e.getLocName().equals("RandomLobby")) {
			e.setMessage("&aSending you to a &1R&2a&3n&4d&5o&6m &7L&8o&9b&ab&by!");
		} else if (e.getLocName().equals("CraftAttack")) {
			e.setMessage("&aSending you to: &5Craft&6Attack!");
		} else {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		World world = e.getPlayer().getWorld();
		if(plugin.getConfig().getStringList("Lobby-Worlds").contains(world.getName())) {
			if(!e.getPlayer().hasMetadata("teleportanimationstoppedmoving")) return;

			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onInventoryClick(final InventoryDragEvent e) {
		if(lobbyinvs.contains(e.getInventory())) {
			e.setCancelled(true);
		}
	}
}