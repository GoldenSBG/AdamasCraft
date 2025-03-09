package ch.goldensbg.adamasCraft.anticheat;

public class KillAuraCheck implements PacketListener {

    private final Map<UUID, Long> lastAttack = new HashMap<>();

    public KillAuraCheck(ProtocolManager protocolManager) {
        protocolManager.addPacketListener(new PacketAdapter(YourPlugin.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                long currentTime = System.currentTimeMillis();
                long lastTime = lastAttack.getOrDefault(player.getUniqueId(), 0L);
                lastAttack.put(player.getUniqueId(), currentTime);

                if (currentTime - lastTime < 50) {
                    player.sendMessage(ChatColor.RED + "KillAura verdächtig!");
                }
            }
        });
    }
}
