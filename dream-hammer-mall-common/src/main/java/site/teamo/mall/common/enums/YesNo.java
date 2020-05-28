package site.teamo.mall.common.enums;

public enum YesNo {
    NO(0,"否"),
    YES(1,"是");

    public final Integer type;
    public final String value;

    YesNo(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
