package assignment.translator.adapters;

import assignment.translator.adapters.LanguageAdapter;
import assignment.translator.domain.Language;
import assignment.translator.domain.Languages;
import assignment.translator.domain.TranslatedDisplayName;
import assignment.translator.enums.ESupportedLanguages;
import assignment.translator.exception.NoTranslatorFOundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AbstractLanguageAdapter {

    @Value("${default.translator.language}")
    protected String DEFAULT_LANGUAGE;
    @Value("${default.translator.value}")
    protected String NO_LANGUAGE_DEFINED;

    /**
     * Handles exceptions and throws a custom exception
     * @param throwable
     * @return
     */
    protected ResponseEntity handleException(Throwable throwable){
        return new ResponseEntity<>(throwable.getMessage(), HttpStatus.I_AM_A_TEAPOT);
    }

    /**
     *
     * @return a list of supported languages without display names.
     */
    protected List<Language> populateDefaultLanguageListCodes() {
        return ESupportedLanguages.LANGUAGE_MAP.keySet()
                .stream()
                .map(Language::new)
                .sorted(Comparator.comparing(Language::getLanguageCode))
                .collect(Collectors.toList());
    }

    /**
     *
     * @param languageAdapter adapter to handle any language display name translations if required
     * @param displayLanguageCode language code to translate language display names to
     * @return
     */
    protected Languages populateLanguageListWithDisplayNames(LanguageAdapter languageAdapter, String displayLanguageCode) {
        List<Language> languages = ESupportedLanguages.LANGUAGE_MAP.entrySet()
                .stream()
                .map(x -> new Language(x.getKey(), x.getValue()))
                .sorted(Comparator.comparing(Language::getLanguageCode))
                .collect(Collectors.toList());

        for (Language language : languages) {
            if (!displayLanguageCode.equals(DEFAULT_LANGUAGE)) {
                try {
                    languageAdapter.determineLanguageTranslatorAndTranslate(DEFAULT_LANGUAGE, displayLanguageCode, language.getDisplayName());
                } catch (NoTranslatorFOundException e) {
                    handleException(e);
                }
                language.setDisplayName(TranslatedDisplayName.getInstance().getDisplayName());
            }
        }
        return new Languages(languages);
    }
}
