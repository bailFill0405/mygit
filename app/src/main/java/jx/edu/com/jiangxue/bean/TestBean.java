package jx.edu.com.jiangxue.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by 赖恒熠 on 2018/7/12.
 */

public class TestBean implements Parcelable{
    int type;
    List<TestBean2> test;

    protected TestBean(Parcel in) {
        type = in.readInt();
        test=in.readArrayList(Thread.currentThread().getContextClassLoader());
    }

    public static final Creator<TestBean> CREATOR = new Creator<TestBean>() {
        @Override
        public TestBean createFromParcel(Parcel in) {
            return new TestBean(in);
        }

        @Override
        public TestBean[] newArray(int size) {
            return new TestBean[size];
        }
    };

    @Override
    public String toString() {
        return "TestBean{" +
                "type=" + type +
                ", test=" + test +
                '}';
    }

    public List<TestBean2> getTest() {
        return test;
    }

    public void setTest(List<TestBean2> test) {
        this.test = test;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(type);
        parcel.writeList(test);
    }

    public static class TestBean2 implements Parcelable {
        int num;


        protected TestBean2(Parcel in) {
            num = in.readInt();
        }

        public static final Creator<TestBean2> CREATOR = new Creator<TestBean2>() {
            @Override
            public TestBean2 createFromParcel(Parcel in) {
                return new TestBean2(in);
            }

            @Override
            public TestBean2[] newArray(int size) {
                return new TestBean2[size];
            }
        };

        @Override
        public String toString() {
            return "TestBean2{" +
                    "num=" + num +
                    '}';
        }

        public TestBean2(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(num);
        }
    }

    public TestBean(int type, List<TestBean2> test) {
        this.type = type;
        this.test = test;
    }

    public TestBean(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
