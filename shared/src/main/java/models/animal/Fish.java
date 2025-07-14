package models.animal;

public enum Fish {
    Salmon(75 , "Autumn"),
    ;

    public int cost;
    public String season;

    Fish(int cost , String season) {
        this.cost = cost;
        this.season = season;
    }


}
