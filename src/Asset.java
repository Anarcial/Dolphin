/*
 * Représentation actif
 */

import java.util.ArrayList;

public final class Asset {
    
  public Asset(int id) { id_ = id; }


    public int id_;
    public EnumAssetType assetType_;

    public Double sharpe_;
    public Double return_;
    public Double vol_;
    public ArrayList<Double> cots_;
    private AssetCategory cat_;

    public AssetCategory cat_get() { return cat_; }
    public void cat_set(AssetCategory cat) { cat_ = cat; }
}
