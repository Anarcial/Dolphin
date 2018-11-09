public enum EnumAssetType {
  STOCK,
  FUND,
  PORTFOLIO,
  BOND;

  EnumAssetType() {}

  public static EnumAssetType getAssetType(String type) {
    if ("STOCK".equals(type))
      return EnumAssetType.STOCK;
    else if ("FUND".equals(type))
      return EnumAssetType.FUND;
    else if ("PORTFOLIO".equals(type))
      return EnumAssetType.PORTFOLIO;
    else if ("BOND".equals(type))
      return EnumAssetType.BOND;

    return null;
  }
}
