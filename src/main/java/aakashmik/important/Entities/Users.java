package aakashmik.important.Entities;




public class Users {

    private String name;
    private String number;
    private String category;
    private String province;
    private String district;
    private String city;
    private double longitude;
    private double latitude;


    public Users() {
        super();
    }

    public Users(String name, String number, String category, String province, String district, String city, double longitude, double latitude) {
        this.name = name;
        this.number = number;
        this.category = category;
        this.province = province;
        this.district = district;
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Users{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", category='" + category + '\'' +
                ", province='" + province + '\'' +
                ", district='" + district + '\'' +
                ", city='" + city + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
