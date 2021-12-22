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

    public String getUserName(){
        return userName;
    }

    public String getPassWord(){
        return passWord;
    }
}
