package jx.edu.com.jiangxue.https;

import java.util.List;

/**
 * Created by Brian Wu on 2016/12/16.
 */

public class HttpResponse<T, K> {
    private boolean result;
    private int code;
    private String msg;

    @Override
    public String toString() {
        return "HttpResponse{" +
                "result=" + result +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", dataList=" + dataList +
                ", data=" + data +
                '}';
    }

    private List<K> dataList;
    private T data;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<K> getDataList() {
        return dataList;
    }

    public void setDataList(List<K> dataList) {
        this.dataList = dataList;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
