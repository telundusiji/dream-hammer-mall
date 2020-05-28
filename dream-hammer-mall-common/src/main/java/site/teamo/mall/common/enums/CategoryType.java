package site.teamo.mall.common.enums;

public enum CategoryType {
    ROOT(1, "yiji"),
    SECOND(2, "erji"),
    THIRD(3, "sanji");

    public final Integer type;
    public final String value;

    CategoryType(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
