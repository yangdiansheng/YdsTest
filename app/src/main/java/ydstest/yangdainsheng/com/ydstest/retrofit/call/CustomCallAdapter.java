package ydstest.yangdainsheng.com.ydstest.retrofit.call;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import ydstest.yangdainsheng.com.ydstest.R;

public class CustomCallAdapter implements CallAdapter<Class<?>,CustomCall<?>> {

    private final Type responseType;

    // 下面的 responseType 方法需要数据的类型
    CustomCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }


    @Override
    public CustomCall<?> adapt(Call<Class<?>> call) {
        return new CustomCall<>(call);
    }
}
