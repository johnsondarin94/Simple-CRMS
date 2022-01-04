package model;

public class Users {
    private int userID;
    private String userName;
    private String passWord;

    public Users(){

    }

    public Users(int userID, String userName, String passWord) {
        this.userID = userID;
        this.userName = userName;
        this.passWord = passWord;
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


}
