package models.Item;

public enum RecipeType {
    COOKING(3),
    CRAFTING(2);
    public int energy;

    RecipeType(int energy) {
        this.energy = energy;
    }

    public int getEnergy() {
        return energy;
    }
}
