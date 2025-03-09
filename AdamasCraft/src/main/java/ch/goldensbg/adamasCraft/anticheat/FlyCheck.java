package ch.goldensbg.adamasCraft.anticheat;

public class FlyCheck implements PacketListener {

    private final Map<UUID, Double> lastY = new HashMap<>();

    public FlyCheck(ProtocolManager protocolManager) {
        protocolManager.addPacketListener(new PacketAdapter(YourPlugin.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Client.POSITION) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                double y = event.getPacket().getDoubles().read(1);
                double lastYValue = lastY.getOrDefault(player.getUniqueId(), y);
                lastY.put(player.getUniqueId(), y);

                if (!player.isOnGround() && (y - lastYValue) > 1.2) {
                    player.sendMessage(ChatColor.RED + "Fly-Hack erkannt!");
                }
            }
        });
    }
}
