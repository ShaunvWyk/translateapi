package assignment.translator.controllers;

import assignment.translator.adapters.AbstractLanguageAdapter;
import assignment.translator.adapters.LanguageAdapter;
import assignment.translator.domain.Languages;
import assignment.translator.domain.UntranslatedLanguage;
import assignment.translator.exception.NoTranslatorFOundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping
public class LanguageTranslatorAPI extends AbstractLanguageAdapter {

    @Resource
    private LanguageAdapter languageAdapter;

    /**
     *
     * @param displayLanguageCode Optional parameter that can be used to choose which language to display supported languages in.
     * @return a List of supported languages
     */
    @GetMapping(path = "/languages")
    public ResponseEntity retrieveLanguages(@RequestParam Optional<String> displayLanguageCode) {
        return displayLanguageCode.isPresent()
                ? new ResponseEntity<>(translateLanguageList(languageAdapter, displayLanguageCode.get()), HttpStatus.OK)
                : new ResponseEntity<>(new Languages(populateDefaultLanguageListCodes()), HttpStatus.OK);
    }

    /**
     * @param untranslatedLanguage Request object containing the source language code, target language code and the text to translate
     * @return a ResponseEntity containing the HTTP response.
     */
    @PostMapping(path = "/translate", consumes = MediaType.ALL_VALUE)
    public @ResponseBody
    ResponseEntity translateLanguage(@RequestBody UntranslatedLanguage untranslatedLanguage) {
        try {
            languageAdapter.determineLanguageTranslatorAndTranslate(untranslatedLanguage.getSourceLanguage(),
                    untranslatedLanguage.getTargetLanguage(),
                    untranslatedLanguage.getText());
        } catch (NoTranslatorFOundException e) {
            return new ResponseEntity<>(handleException(e), HttpStatus.I_AM_A_TEAPOT);
        }
        return new ResponseEntity<>(languageAdapter.getTranslatedLanguage(), HttpStatus.OK);
    }

    /**
     * @param displayLanguageCode the language code to display the language list in
     * @return a new Languages object containing the list of translated languages
     */
    private Languages translateLanguageList(LanguageAdapter englishLanguageAdapter, String displayLanguageCode) {
        return populateLanguageListWithDisplayNames(englishLanguageAdapter, displayLanguageCode);
    }
}
