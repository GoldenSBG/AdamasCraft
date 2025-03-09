package ch.goldensbg.adamasCraft.anticheat;

import com.comphenix.protocol.events.PacketListener;

public class XRayCheck implements PacketListener {

    private final Set<Material> valuableBlocks = Set.of(Material.DIAMOND_ORE, Material.ANCIENT_DEBRIS, Material.EMERALD_ORE);

    public XRayCheck(ProtocolManager protocolManager) {
        protocolManager.addPacketListener(new PacketAdapter(YourPlugin.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Client.BLOCK_DIG) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                Block block = player.getTargetBlockExact(6);

                if (block != null && valuableBlocks.contains(block.getType())) {
                    player.sendMessage(ChatColor.RED + "X-Ray verdächtig!");
                }
            }
        });
    }
}
