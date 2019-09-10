package assignment.translator.domain;

public class TranslatedLanguage {
    private String languageCode;
    private String translation;

    private static final TranslatedLanguage INSTANCE = new TranslatedLanguage();

    private TranslatedLanguage(){}

    public static TranslatedLanguage getInstance()
    {
        return INSTANCE;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
