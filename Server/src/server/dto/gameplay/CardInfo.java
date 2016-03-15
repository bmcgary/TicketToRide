package server.dto.gameplay;

import com.google.gson.annotations.SerializedName;

/**
 * Generalized Card Info class
 * Created by rodriggl on 3/10/2016.
 */
public class CardInfo {
    @SerializedName("color")
    private String cardColor;
    @SerializedName("amount")
    private int amount;
}
