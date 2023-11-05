package com.example.Twitter.dto;

public class FindUsernameDTO {
    private String email;
    private String phone;
    private String username;

    public FindUsernameDTO(String email, String phone, String username) {
        super();
        this.email = email;
        this.phone = phone;
        this.username = username;
    }

    public FindUsernameDTO() {
        super();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "FindUsernameDTO{" +
                "email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
