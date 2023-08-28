package com.example.swp490_g25_sse.dto;

public class UserInfoDto {

    private String firstName;
    private String lastName;
    private String email;
    private String imageURL;

    public UserInfoDto() {
    }

    public UserInfoDto(String firstName, String lastName, String email, String imageURL) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.imageURL = imageURL;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
