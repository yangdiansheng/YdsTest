package ydstest.yangdainsheng.com.ydstest.retrofit.call;

import java.io.IOException;

import retrofit2.Call;

public class CustomCall<R> {
    public final Call<R> call;


    public CustomCall(Call<R> call){
        this.call = call;
    }

    public R get() throws IOException {
        return call.execute().body();
    }
}
