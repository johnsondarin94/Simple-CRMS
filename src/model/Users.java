package model;

/**Users class provides a model for a Users object*/
public class Users {
    private int userID;
    private String userName;
    private String passWord;
    private boolean active;

    public Users(){

    }

    /**Constructor for a Users object
     *
     * @param userID UserID(int) for a Users object
     * @param userName UserName(String) for a Users object
     * @param passWord Password(String) for a Users object
     * @param active Sets User at Login(boolean)
     *
     * */
    public Users(int userID, String userName, String passWord, boolean active) {
        this.userID = userID;
        this.userName = userName;
        this.passWord = passWord;
        this.active = false;
    }

    /**Getter for User ID
     * @return Return User ID*/
    public int getUserID() { return userID; }

    /**Setter for User ID
     * @param userID Sets User ID*/
    public void setUserID(int userID) {this.userID = userID; }

    /**Getter for User Name
     * @return Return User Name*/
    public String getUserName() { return userName; }

    /**Setter for User Name
     * @param userName  Sets UserName*/
    public void setUserName(String userName) { this.userName = userName; }

    /**Getter for Password
     * @return Return Password*/
    public String getPassWord() { return passWord; }

    /**Setter for Password
     * @param passWord Sets Password*/
    public void setPassWord(String passWord) { this.passWord = passWord; }

    /**Getter for active
     * @return Boolean*/
    public boolean isActive() { return active; }

    /**Setter for active
     * @param active Sets active*/
    public void setActive(boolean active) { this.active = active; }

    /**Overrides the String class to provide proper display of a Users object in the UI.*/
    @Override
    public String toString(){ return(userID + " - " + userName); }

}
