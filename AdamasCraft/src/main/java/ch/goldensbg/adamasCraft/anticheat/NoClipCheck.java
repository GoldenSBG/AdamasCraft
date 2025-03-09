package ch.goldensbg.adamasCraft.anticheat;

public class NoClipCheck implements PacketListener {

    public NoClipCheck(ProtocolManager protocolManager) {
        protocolManager.addPacketListener(new PacketAdapter(YourPlugin.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Client.POSITION) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                if (!player.getWorld().getBlockAt(player.getLocation()).getType().isAir()) {
                    player.sendMessage(ChatColor.RED + "NoClip erkannt!");
                }
            }
        });
    }
}
