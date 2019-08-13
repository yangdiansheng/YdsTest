package ydstest.yangdainsheng.com.ydstest.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;

public interface RequestServiceString {

    @POST("user/availablelimit")
    Call<String> getAvailableLimit();

}
