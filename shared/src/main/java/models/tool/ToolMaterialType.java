package models.tool;

public enum ToolMaterialType {
    PRIMITIVE("Copper Tool"),
    COPPER("Steel Tool"),
    STEEL("Gold Tool"),
    GOLD("Iridium Tool"),
    IRIDIUM(null),

    EDUCATIONAL(null),
    BAMBOO(null),
    FIBERGLASS(null);

    private final String nextToolName;
    private ToolMaterialType nextType;    // not final—will be set in the static block

    ToolMaterialType(String nextToolName) {
        this.nextToolName = nextToolName;
    }

    // after all enum constants are created, wire up the nextType links:
    static {
        PRIMITIVE.nextType = COPPER;
        COPPER.nextType   = STEEL;
        STEEL.nextType    = GOLD;
        GOLD.nextType     = IRIDIUM;
        // IRIDIUM, EDUCATIONAL, BAMBOO, FIBERGLASS all leave nextType == null
    }

    /** @return this material’s enum name. */
    public String getName() {
        return name();
    }

    /**
     * @return the display string for the next-upgraded tool,
     *         or null if there is no “next” material.
     */
    public String getNextToolName() {
        return nextToolName;
    }

    /**
     * @return the ToolMaterialType of the next upgrade,
     *         or null if there is none.
     */
    public ToolMaterialType getNextType() {
        return nextType;
    }
}
