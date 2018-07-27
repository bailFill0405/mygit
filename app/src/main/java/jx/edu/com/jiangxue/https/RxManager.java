package jx.edu.com.jiangxue.https;


import rx.subscriptions.CompositeSubscription;

public class RxManager {

    private CompositeSubscription mCompositeSubscription = null;

    public RxManager(CompositeSubscription compositeDisposable) {
        this.mCompositeSubscription = compositeDisposable;
    }

    /**
     * 取消请求
     */
    public void cancel() {
        if (mCompositeSubscription != null && !mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription.unsubscribe();
        }
    }
}