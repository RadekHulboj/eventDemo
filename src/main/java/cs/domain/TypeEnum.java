package cs.domain;

/**
 * Created by radoslaw on 11.08.18.
 */
public enum TypeEnum {
    APPLICATION_LOG(1),
    DB_LOG(2);

    private final int data;

    TypeEnum(int data) {
        this.data = data;
    }
}
