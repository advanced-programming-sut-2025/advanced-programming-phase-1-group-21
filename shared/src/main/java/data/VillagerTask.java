package data;

import com.google.gson.annotations.SerializedName;

public class VillagerTask implements java.io.Serializable {
    @SerializedName("request-item")
    private String requestItem;

    @SerializedName("request-amount")
    private int requestAmount;

    @SerializedName("reward-item")
    private String rewardItem;

    @SerializedName("reward-amount")
    private int rewardAmount;

    public String getRequestItem() {
        return requestItem;
    }

    public int getRequestAmount() {
        return requestAmount;
    }

    public String getRewardItem() {
        return rewardItem;
    }

    public int getRewardAmount() {
        return rewardAmount;
    }
}
