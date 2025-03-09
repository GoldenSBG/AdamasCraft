package ch.goldensbg.adamasCraft.listeners.settings;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BlockBreakEvent implements Listener {

    private Set<Material> getLogs() {
        Set<Material> logs = new HashSet<>();
        logs.add(Material.OAK_LOG);
        logs.add(Material.SPRUCE_LOG);
        logs.add(Material.BIRCH_LOG);
        logs.add(Material.JUNGLE_LOG);
        logs.add(Material.ACACIA_LOG);
        logs.add(Material.DARK_OAK_LOG);
        logs.add(Material.MANGROVE_LOG);
        logs.add(Material.PALE_OAK_LOG);
        return logs;
    }

    @EventHandler
    public void onBlockBreak(org.bukkit.event.block.BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Material type = block.getType();
        Set<Material> logs = getLogs(); // Hole die Log-Materialien

        if (logs.contains(type)) {
            removeTree(block, logs, player);
        }
    }

    private void removeTree(Block startBlock, Set<Material> logs, Player player) {
        // Verwende eine Liste, um die zu überprüfenden Blöcke zu speichern
        List<Block> blocksToCheck = new ArrayList<>();
        blocksToCheck.add(startBlock);

        // Verwende ein Set, um bereits überprüfte Blöcke zu speichern und doppelte Überprüfungen zu vermeiden
        Set<Block> checkedBlocks = new HashSet<>();

        while (!blocksToCheck.isEmpty()) {
            Block block = blocksToCheck.remove(0);

            // Vermeide doppelte Überprüfungen
            if (checkedBlocks.contains(block)) {
                continue;
            }

            checkedBlocks.add(block);
            Material type = block.getType();

            if (logs.contains(type)) {
                block.setType(Material.AIR);
                player.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(type));

                // Füge benachbarte Blöcke zur Liste hinzu
                for (BlockFace face : BlockFace.values()) {
                    Block adjacentBlock = block.getRelative(face);
                    if (!checkedBlocks.contains(adjacentBlock) && logs.contains(adjacentBlock.getType())) {
                        blocksToCheck.add(adjacentBlock);
                    }
                }
            }
        }
    }

}