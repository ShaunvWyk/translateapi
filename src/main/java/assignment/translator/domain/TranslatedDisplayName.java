package assignment.translator.domain;

public class TranslatedDisplayName {
    private String displayName;

    private static final TranslatedDisplayName INSTANCE = new TranslatedDisplayName();

    private TranslatedDisplayName() {
    }

    public static TranslatedDisplayName getInstance() {
        return INSTANCE;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
