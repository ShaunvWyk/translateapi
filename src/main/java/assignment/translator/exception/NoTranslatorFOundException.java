package assignment.translator.exception;

public class NoTranslatorFOundException extends ClassNotFoundException {
    public NoTranslatorFOundException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
