package models.map;

public class Lake extends Building {
    @Override
    public boolean canEnter() {
        return false;
    }
}
