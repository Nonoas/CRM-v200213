package per.nonoas.delegate;

/**
 * @author : Nonoas
 * @time : 2020-08-02 23:24
 */
@FunctionalInterface
public interface Event {
    void invoke();
}
