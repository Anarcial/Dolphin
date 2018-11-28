/*
 * Représentation actif
 */

public final class Asset {
  
  public JumpValue id_;

  public JumpValue label_;
  
  public JumpValue price_;
  public MonetaryNumber monetary_; // FIXME

  public JumpValue typeValue_;
  public EnumAssetType assetType_;

  public Double sharpe_;
  public Double return_;
  public Double vol_;
  public List<> cots_; // FIXME
  private AssetCategory cat_;

  public AssetCategory cat_get() { return cat_; }
  public void cat_set(AssetCategory cat) { cat_ = cat; }
}
