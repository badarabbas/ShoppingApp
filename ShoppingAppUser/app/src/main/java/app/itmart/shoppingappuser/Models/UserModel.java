package app.itmart.shoppingappuser.Models;

public class UserModel {
    String name;
    String phone;
    String gender;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String type;

    public UserModel() {
    }

    public UserModel(String name, String phone, String gender, String type) {
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
