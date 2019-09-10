package assignment.translator.enums;


import java.util.HashMap;
import java.util.Map;

public enum ESupportedLanguages {

    ENGLISH("en", "English"),
    PIG_LATIN("pi", "Pig Latin");

    public static final Map<String, String> LANGUAGE_MAP = new HashMap<>();
    private String languageCode;
    private String localName;
    private final String DEFAULT_LANGUAGE = "en";


    ESupportedLanguages(String languageCode, String localName) {
        this.languageCode = languageCode;
        this.localName = localName;
    }

    public static void initLanguages() {
        for (ESupportedLanguages supportedLanguage : values()) {
            LANGUAGE_MAP.put(supportedLanguage.languageCode, supportedLanguage.localName);
        }
    }


}
