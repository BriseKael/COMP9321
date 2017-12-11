package bean;

import java.sql.Date;

public class UserBean {
    private int userId;
    private String username;
    private String password;
    private String email;
    private String nickname;
    private String firstName;
    private String lastName;
    private String gender;
    private Date dateOfBirth;
    private String photo;
    
    private boolean isAdmin;
    private boolean isActived;
    private boolean isBanned;
    
    
    public int getUserId() {
        return userId;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public String getNickname() {
        return nickname;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getGender() {
        return gender;
    }
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public String getPhoto() {
        return photo;
    }
    public boolean isAdmin() {
        return isAdmin;
    }
    public boolean isActived() {
        return isActived;
    }
    public boolean isBanned() {
        return isBanned;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    public void setActived(boolean isActived) {
        this.isActived = isActived;
    }
    public void setBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }
    
}
