package jx.edu.com.jiangxue.ui.bean;

/**
 * Created by 赖恒熠 on 2018/7/17.
 */

public class PayTypeEntity {
    private String imgUrl;
    private String type;

    public PayTypeEntity(String type) {
        this.type = type;
    }

    public PayTypeEntity(String imgUrl, String type) {
        this.imgUrl = imgUrl;
        this.type = type;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
