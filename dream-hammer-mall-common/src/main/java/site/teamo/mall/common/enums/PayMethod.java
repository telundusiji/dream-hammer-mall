package site.teamo.mall.common.enums;

public enum PayMethod {
    WEI_XIN(1,"微信"),
    ALI_PAY(2,"支付宝");

    public final Integer type;
    public final String value;

    PayMethod(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
