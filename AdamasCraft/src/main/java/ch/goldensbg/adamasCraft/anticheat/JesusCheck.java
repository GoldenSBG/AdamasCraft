package ch.goldensbg.adamasCraft.anticheat;

public class JesusCheck implements PacketListener {

    public JesusCheck(ProtocolManager protocolManager) {
        protocolManager.addPacketListener(new PacketAdapter(YourPlugin.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Client.POSITION) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                if (player.getLocation().getBlock().getType() == Material.WATER && player.isOnGround()) {
                    player.sendMessage(ChatColor.RED + "Jesus-Hack erkannt!");
                }
            }
        });
    }
}
