package assignment.translator.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Language {
    private String displayName;
    private String languageCode;

    public Language(String languageCode, String displayName) {
        this.displayName = displayName;
        this.languageCode = languageCode;
    }

    public Language(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
