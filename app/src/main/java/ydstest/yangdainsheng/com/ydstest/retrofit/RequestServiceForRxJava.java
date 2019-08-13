package ydstest.yangdainsheng.com.ydstest.retrofit;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import rx.Observable;

public interface RequestServiceForRxJava {

    @POST("user/availablelimit")
    Observable<ResponseBody> getAvailableLimit();

}
