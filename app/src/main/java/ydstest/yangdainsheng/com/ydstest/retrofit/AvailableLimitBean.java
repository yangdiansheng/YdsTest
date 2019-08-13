package ydstest.yangdainsheng.com.ydstest.retrofit;

public class AvailableLimitBean {
    public String cashLimit;
    public String maxLimit;
    public String validateTime;
    public String status;
    public String controlDays;
    public String totalLimit;
    public String title;
    public String content1;

    @Override
    public String toString() {
        return "AvailableLimitBean{" +
                "cashLimit='" + cashLimit + '\'' +
                ", maxLimit='" + maxLimit + '\'' +
                ", validateTime='" + validateTime + '\'' +
                ", status='" + status + '\'' +
                ", controlDays='" + controlDays + '\'' +
                ", totalLimit='" + totalLimit + '\'' +
                ", title='" + title + '\'' +
                ", content1='" + content1 + '\'' +
                '}';
    }
}
