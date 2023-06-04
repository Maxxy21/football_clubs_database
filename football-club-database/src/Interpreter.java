
/**
 * A functional interface.
 */
public interface Interpreter {

    State resolve(String cmd) throws Exception;

}
