package ch.goldensbg.adamasCraft.ranks;


import ch.goldensbg.adamasCraft.utils.Spacing;

public enum Ranks {
    PLAYER(Spacing.NEGATIVE64PIXEl.getSpacing() + "\uD83C\uDFAE" ),
    SUBSCRIBER("\uD83C\uDF40" + Spacing.POSITIVE16PIXEl.getSpacing()),
    VIP(Spacing.POSITIVE16PIXEl.getSpacing() + "\uD83D\uDCB3"),
    MODERATION("■" + Spacing.POSITIVE16PIXEl.getSpacing()),
    ADMIN("\uD83C\uDF6A" + Spacing.POSITIVE16PIXEl.getSpacing()),
    STREAMER("♪" + Spacing.POSITIVE16PIXEl.getSpacing()),
    OWNER("\uD83D\uDC51" + Spacing.NEGATIVE32PIXEl.getSpacing());

    private final String rankPrefix;

    Ranks(String rankPrefix) {
        this.rankPrefix = rankPrefix;
    }
}