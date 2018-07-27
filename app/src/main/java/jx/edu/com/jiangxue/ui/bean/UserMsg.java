package jx.edu.com.jiangxue.ui.bean;

/**
 * Created by 赖恒熠 on 2018/7/26.
 */

public class UserMsg {
    String account;
    String pwd;
    boolean isLogin;

    public UserMsg() {
    }

    public UserMsg(String account, String pwd, boolean isLogin) {
        this.account = account;
        this.pwd = pwd;
        this.isLogin = isLogin;
    }

    @Override
    public String toString() {
        return "UserMsg{" +
                "account='" + account + '\'' +
                ", pwd='" + pwd + '\'' +
                ", isLogin=" + isLogin +
                '}';
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
