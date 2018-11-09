import com.google.gson.annotations.SerializedName;

public final class AssetCurrency {
  
  public AssetCurrency() {}

  public AssetCurrency(final String code) {
    code_ = code;
  }

  @SerializedName("code")
  public String code_;
}
