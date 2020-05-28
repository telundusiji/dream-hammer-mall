package site.teamo.mall.common.enums;

public enum  Sex {
    FEMALE(0,"女"),
    MALE(1,"男"),
    SECRET(2,"保密");

    public final Integer type;
    public final String value;

    Sex(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
