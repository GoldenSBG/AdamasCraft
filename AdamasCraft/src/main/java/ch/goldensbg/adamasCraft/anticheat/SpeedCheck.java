package ch.goldensbg.adamasCraft.anticheat;

public class SpeedCheck implements PacketListener {

    private final Map<UUID, Location> lastLocation = new HashMap<>();

    public SpeedCheck(ProtocolManager protocolManager) {
        protocolManager.addPacketListener(new PacketAdapter(YourPlugin.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Client.POSITION) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                double distance = event.getPacket().getDoubles().read(0);

                if (distance > 0.7) {
                    player.sendMessage(ChatColor.RED + "Speed-Hack erkannt!");
                }
            }
        });
    }
}
