import com.google.gson.annotations.SerializedName;

public class JumpValue {
  @SerializedName("type")
  public final String type_;

  @SerializedName("value")
  public final String value_;

  public JumpValue(String type, String value) {
    type_ = type;
    value_ = value;
  }
}
