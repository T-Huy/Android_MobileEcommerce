package vn.iotstar.AppMobileEcommerce.model;

public class CustomerModel {
    private String userName;

    private String address;

    private String avatar;

    private String fullname;

    private String phonenumber;

    private String province;

    private String district;

    private String subdistrict;

    public CustomerModel() {
    }

    public CustomerModel(String userName, String address, String avatar, String fullname, String phonenumber, String province, String district, String subdistrict) {
        this.userName = userName;
        this.address = address;
        this.avatar = avatar;
        this.fullname = fullname;
        this.phonenumber = phonenumber;
        this.province = province;
        this.district = district;
        this.subdistrict = subdistrict;
    }

    public CustomerModel(String string) {
        this.userName = string;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
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

    public String getSubdistrict() {
        return subdistrict;
    }

    public void setSubdistrict(String subdistrict) {
        this.subdistrict = subdistrict;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "userName='" + userName + '\'' +
                ", address='" + address + '\'' +
                ", avatar='" + avatar + '\'' +
                ", fullname='" + fullname + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", province=" + province +
                ", district=" + district +
                ", subdistrict=" + subdistrict +
                '}';
    }


}
