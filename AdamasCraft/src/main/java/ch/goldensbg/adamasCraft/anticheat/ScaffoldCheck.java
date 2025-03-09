package ch.goldensbg.adamasCraft.anticheat;

public class ScaffoldCheck implements PacketListener {

    private final Map<UUID, Long> lastPlace = new HashMap<>();

    public ScaffoldCheck(ProtocolManager protocolManager) {
        protocolManager.addPacketListener(new PacketAdapter(YourPlugin.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Client.BLOCK_PLACE) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                long currentTime = System.currentTimeMillis();
                long lastTime = lastPlace.getOrDefault(player.getUniqueId(), 0L);
                lastPlace.put(player.getUniqueId(), currentTime);

                if (currentTime - lastTime < 100) {
                    player.sendMessage(ChatColor.RED + "Scaffold verdächtig!");
                }
            }
        });
    }
}
