package so.sao.shop.dada.request;

public class DadaCreateShopRequest extends DadaBaseRequest {

    private String stationName;

    /**
     * 业务类型（默认为8便利店）
     * */
    private Integer business;

    /**
     * 城市名称（如：上海）
     * */
    private String cityName;

    /**
     * 区域名称（如：浦东新区）
     * */
    private String areaName;

    /**
     * 门店地址
     * */
    private String stationAddress;

    /**
     * 门店经度
     * */
    private Double lng;

    /**
     * 门店纬度
     * */
    private Double lat;

    private String contactName;

    private String phone;

    /**
     * 门店编号（与我们的门店id保持一致）
     * */
    private String originShopId;

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Integer getBusiness() {
        return business;
    }

    public void setBusiness(Integer business) {
        this.business = business;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getStationAddress() {
        return stationAddress;
    }

    public void setStationAddress(String stationAddress) {
        this.stationAddress = stationAddress;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOriginShopId() {
        return originShopId;
    }

    public void setOriginShopId(String originShopId) {
        this.originShopId = originShopId;
    }

}
