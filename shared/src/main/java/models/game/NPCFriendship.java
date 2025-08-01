package models.game;

import models.DailyUpdate;

import java.io.Serializable;
import java.util.HashMap;

public class NPCFriendship implements DailyUpdate, Serializable {
    private Player player;
    private FriendshipLevel Level;
    private int friendshipXP;
    private boolean todayMeet = false;
    private boolean todayGift = false;
    private HashMap<Integer, String> winterGreetings = new HashMap<>();
    private HashMap<Integer, String> springGreetings = new HashMap<>();
    private HashMap<Integer, String> summerGreetings = new HashMap<>();
    private HashMap<Integer, String> autumnGreetings = new HashMap<>();

    public NPCFriendship() {}

    public NPCFriendship(Player player, FriendshipLevel level , int friendshipXP) {
        this.player = player;
        this.Level = level;
        this.friendshipXP = friendshipXP;

        winterGreetings.put(0, "A chilly midnight in winter—stay warm under the stars!");
        winterGreetings.put(1, "Late-night winter whispers—hot cocoa time?");
        winterGreetings.put(2, "The coldest hour—blankets and dreams await.");
        winterGreetings.put(3, "Winter's deep night—rest while snowflakes fall.");
        winterGreetings.put(4, "Dawn is distant, but winter's quiet is magical.");
        winterGreetings.put(5, "Frosty morning approaches—wrap up tight!");
        winterGreetings.put(6, "Sunrise glows on icy windows—winter’s beauty awakens.");
        winterGreetings.put(7, "Good morning! Winter’s crisp air greets you.");
        winterGreetings.put(8, "A fresh winter day begins—time for scarves and smiles.");
        winterGreetings.put(9, "Mid-morning in winter—snowflakes or sunshine?");
        winterGreetings.put(10, "Winter’s golden hour—cozy vibes only.");
        winterGreetings.put(11, "Noon in winter—soup season is here!");
        winterGreetings.put(12, "Lunchtime—warm bread and winter stews.");
        winterGreetings.put(13, "Winter afternoon—perfect for a fireplace.");
        winterGreetings.put(14, "Snowy daylight—stay snug indoors.");
        winterGreetings.put(15, "Winter sun hangs low—tea time?");
        winterGreetings.put(16, "Sunset comes early—candles and comfort.");
        winterGreetings.put(17, "Twilight in winter—hot chocolate o’clock!");
        winterGreetings.put(18, "Dinner by winter’s moonlight—enjoy!");
        winterGreetings.put(19, "Evening chill sets in—socks and stories.");
        winterGreetings.put(20, "Winter nightfall—time for woolly pajamas.");
        winterGreetings.put(21, "Late evening—winter’s silence is golden.");
        winterGreetings.put(22, "Almost bedtime—dream of snow-capped mountains.");
        winterGreetings.put(23, "Winter’s last hour—sleep well under thick quilts.");

        // Spring (Mar-May)
        springGreetings.put(0, "Midnight in spring—a whisper of blossoms.");
        springGreetings.put(1, "Late-night spring breeze—new beginnings stir.");
        springGreetings.put(2, "The world sleeps, but flowers prepare to bloom.");
        springGreetings.put(3, "Spring’s quiet hour—dawn will bring color.");
        springGreetings.put(4, "Birds begin to sing—spring mornings are near!");
        springGreetings.put(5, "Sunrise peeks—dew on fresh petals.");
        springGreetings.put(6, "Good morning! Spring’s light warms the earth.");
        springGreetings.put(7, "A bright spring day starts—breathe in renewal.");
        springGreetings.put(8, "Mid-morning—butterflies dance in sunlight.");
        springGreetings.put(9, "Spring’s energy grows—time for a garden stroll.");
        springGreetings.put(10, "Golden spring hour—picnics await!");
        springGreetings.put(11, "Noon in spring—cherry blossoms sway.");
        springGreetings.put(12, "Lunch under flowering trees—bliss!");
        springGreetings.put(13, "Spring afternoon—bees buzz happily.");
        springGreetings.put(14, "Warm sun, cool breeze—perfect balance.");
        springGreetings.put(15, "Tea time with spring’s gentle hum.");
        springGreetings.put(16, "Sunset paints pastels—spring’s artistry.");
        springGreetings.put(17, "Twilight fireflies emerge—magic hour!");
        springGreetings.put(18, "Dinner alfresco—spring nights are mild.");
        springGreetings.put(19, "Evening blooms release fragrance—breathe deep.");
        springGreetings.put(20, "Spring nightfall—crickets begin their song.");
        springGreetings.put(21, "Late evening—moonlight on fresh leaves.");
        springGreetings.put(22, "Almost bedtime—dream of endless meadows.");
        springGreetings.put(23, "Spring’s last whispers—rest well, grow tomorrow.");

        // Summer (Jun-Aug)
        summerGreetings.put(0, "Midnight in summer—warm and starry!");
        summerGreetings.put(1, "Late-night summer air—fireflies still glow.");
        summerGreetings.put(2, "The night is alive—crickets sing loudly.");
        summerGreetings.put(3, "Summer’s deepest dark—dawn will come hot.");
        summerGreetings.put(4, "Early birds chirp—sunrise is coming!");
        summerGreetings.put(5, "Golden dawn—beach day ahead?");
        summerGreetings.put(6, "Good morning! Summer sun already shines.");
        summerGreetings.put(7, "A scorching day begins—sunscreen ready!");
        summerGreetings.put(8, "Mid-morning heat—time for icy lemonade.");
        summerGreetings.put(9, "Summer’s peak warmth—seek shade or swim!");
        summerGreetings.put(10, "Golden hour—tan lines and laughter.");
        summerGreetings.put(11, "Noon in summer—the sun reigns supreme.");
        summerGreetings.put(12, "Lunch by the pool—splash away!");
        summerGreetings.put(13, "Lazy summer afternoon—hammock time.");
        summerGreetings.put(14, "Heat shimmers—watermelon saves the day.");
        summerGreetings.put(15, "Ice cream o’clock—melt fast, eat faster!");
        summerGreetings.put(16, "Sunset lingers—BBQ smells fill the air.");
        summerGreetings.put(17, "Twilight beach walks—barefoot bliss.");
        summerGreetings.put(18, "Dinner outdoors—mosquitoes join too!");
        summerGreetings.put(19, "Evening warmth lingers—stargazing soon.");
        summerGreetings.put(20, "Summer nightfall—bonfire stories begin.");
        summerGreetings.put(21, "Late evening—popsicles and porch swings.");
        summerGreetings.put(22, "Almost bedtime—fan humming softly.");
        summerGreetings.put(23, "Summer’s last heat—sleep with thin sheets.");

        autumnGreetings.put(0, "Midnight in autumn—crisp and quiet.");
        autumnGreetings.put(1, "Late-night autumn—leaves rustle secrets.");
        autumnGreetings.put(2, "The owl hoots—harvest moon glows.");
        autumnGreetings.put(3, "Deep autumn night—pumpkin spice dreams.");
        autumnGreetings.put(4, "Pre-dawn chill—frost on pumpkins.");
        autumnGreetings.put(5, "Sunrise through fog—autumn’s mystery.");
        autumnGreetings.put(6, "Good morning! Scarlet leaves greet you.");
        autumnGreetings.put(7, "A brisk autumn day—sweater weather!");
        autumnGreetings.put(8, "Mid-morning—apple-picking time.");
        autumnGreetings.put(9, "Golden autumn light—perfect for photos.");
        autumnGreetings.put(10, "Coffee and cinnamon—autumn’s aroma.");
        autumnGreetings.put(11, "Noon in autumn—soup simmers happily.");
        autumnGreetings.put(12, "Lunch with a view—fall foliage dazzles.");
        autumnGreetings.put(13, "Afternoon crunch—walk through leaves.");
        autumnGreetings.put(14, "Golden hour—pumpkin carving begins!");
        autumnGreetings.put(15, "Tea time—warm cup, cool breeze.");
        autumnGreetings.put(16, "Sunset sets trees aflame—breathtaking.");
        autumnGreetings.put(17, "Twilight—bonfire smoke curls upward.");
        autumnGreetings.put(18, "Dinner with roasted squash—cozy!");
        autumnGreetings.put(19, "Evening chill—blanket fort time.");
        autumnGreetings.put(20, "Autumn nightfall—woodstove crackles.");
        autumnGreetings.put(21, "Late evening—reading by lantern light.");
        autumnGreetings.put(22, "Almost bedtime—warm cider nightcap.");
        autumnGreetings.put(23, "Autumn’s last whisper—sweet dreams.");
    }

    public String talk(int season , int hour) {
        if(season == 0)
            return springGreetings.get(hour);
        if(season == 1)
            return summerGreetings.get(hour);
        if(season == 2)
            return autumnGreetings.get(hour);
        if(season == 3)
            return winterGreetings.get(hour);
        return null;
    }

    public boolean isTodayMeet() {
        return todayMeet;
    }

    public boolean isTodayGift() {
        return todayGift;
    }

    public void setTodayMeet(boolean todayMeet) {
        this.todayMeet = todayMeet;
    }

    public void setTodayGift(boolean todayGift) {
        this.todayGift = todayGift;
    }

    public Player getPlayer() {
        return player;
    }

    public FriendshipLevel getLevel() {
        return Level;
    }

    public int getFriendshipXP() {
        return friendshipXP;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setLevel(FriendshipLevel level) {
        Level = level;
    }

    public void setFriendshipXP(int friendshipXP) {
        this.friendshipXP = friendshipXP;
        checkOverFlow();

    }

    public void checkOverFlow(){
        if(friendshipXP > 799)
            friendshipXP = 799;
        else if(friendshipXP >= (Level.getLevel() + 1)*200)
            levelUp();
    }

    public void levelUp(){
        if(Level.equals(FriendshipLevel.LEVEL0))
            Level = FriendshipLevel.LEVEL1;
        else if(Level.equals(FriendshipLevel.LEVEL1))
            Level = FriendshipLevel.LEVEL2;
        else if(Level.equals(FriendshipLevel.LEVEL2))
            Level = FriendshipLevel.LEVEL3;
    }

    @Override
    public String toString() {
        return String.format(
                "NPCFriendship{player=%s, level=%s, xp=%d, todayMeet=%b, todayGift=%b}",
                player.getUser().getUsername(),
                Level,
                friendshipXP,
                todayMeet,
                todayGift
        );
    }

    @Override
    public boolean nextDay(Game g) {
        this.setTodayMeet(false);
        this.setTodayGift(false);
        return false;
    }
}
