/*
 * Représentation actif
 */

public final class Asset {
  public Asset(int id, Double sharpe, Double vol) {
    id_ = id;
    sharpe_ = sharpe;
    vol_ = vol;
  }

  public int id_;

  public EnumAssetType assetType_;

  public Double sharpe_;
  public Double return_;
  public Double vol_;
  public List<> cots_; // FIXME
  private AssetCategory cat_;

  public AssetCategory cat_get() { return cat_; }
  public void cat_set(AssetCategory cat) { cat_ = cat; }
}
