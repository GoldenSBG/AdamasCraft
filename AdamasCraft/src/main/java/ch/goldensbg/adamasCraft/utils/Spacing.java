package ch.goldensbg.adamasCraft.utils;

public enum Spacing {
    POSITIVE128PIXEl("\udb00\udc80"),
    POSITIVE64PIXEl("\udb00\udc40"),
    POSITIVE32PIXEl("\udb00\udc20"),
    POSITIVE16PIXEl("\udb00\udc10"),
    POSITIVE8PIXEl("\udb00\udc08"),
    POSITIVE4PIXEl("\udb00\udc04"),
    POSITIVE2PIXEl("\udb00\udc02"),
    POSITIVE1PIXEl("\udb00\udc01"),
    ZEROPIXEl("\udb00\udc00"),
    NEGATIVE128PIXEl("\udaff\udf80"),
    NEGATIVE64PIXEl("\udaff\udfc0"),
    NEGATIVE32PIXEl("\udaff\udfe0"),
    NEGATIVE16PIXEl("\udaff\udff0"),
    NEGATIVE8PIXEl("\udaff\udff8"),
    NEGATIVE4PIXEl("\udaff\udffc"),
    NEGATIVE2PIXEl("\udaff\udffe"),
    NEGATIVE1PIXEl("\udaff\udfff");

    private final String spacing;

    private Spacing(String spacing) {
        this.spacing = spacing;
    }

    public String getSpacing() {
        return this.spacing;
    }
}