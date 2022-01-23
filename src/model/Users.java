package model;

public class Users {
    private int userID;
    private String userName;
    private String passWord;
    private boolean active;

    public Users(){

    }

    public Users(int userID, String userName, String passWord, boolean active) {
        this.userID = userID;
        this.userName = userName;
        this.passWord = passWord;
        this.active = false;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString(){
        return(userID + " - " + userName);
    }

}
