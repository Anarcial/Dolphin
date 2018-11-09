import com.google.gson.annotations.SerializedName;

/*
 * Représentation actif
 */

public final class Asset {
  
  @SerializedName("ASSET_DATABASE_ID")
  public JumpValue id_;

  @SerializedName("LABEL")
  public JumpValue label_;
  
  @SerializedName("LAST_CLOSE°VALUE_IN_CURR")
  public JumpValue price_;
  public MonetaryNumber monetary_;

  @SerializedName("TYPE")
  public JumpValue typeValue_;
  public EnumAssetType assetType_;
}
