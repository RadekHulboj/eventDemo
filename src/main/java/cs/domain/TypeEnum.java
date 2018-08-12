package cs.domain;

public enum TypeEnum {

    APPLICATION_LOG(1),
    DB_LOG(2);

    private final int data;

    TypeEnum(int data) {
        this.data = data;
    }

    public int toValue() {
        return data;
    }

    public static TypeEnum getEnum(int i) {
        switch (i) {
            case 1:
                return APPLICATION_LOG;
            case 2:
                return DB_LOG;
            default:
                return APPLICATION_LOG;
        }
    }
}
