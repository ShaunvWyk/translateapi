package assignment.translator.adapters;

import assignment.translator.domain.TranslatedDisplayName;
import assignment.translator.domain.TranslatedLanguage;
import assignment.translator.enums.ESupportedLanguages;
import assignment.translator.exception.NoTranslatorFOundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Component
public class LanguageAdapter implements ITranslate {

    private TranslatedLanguage translatedLanguage;
    private TranslatedDisplayName displayName;
    @Value("${trnslator.package}")
    private String TRANSLATOR_PACKAGE;

    /**
     * Invokes translation method to do specific language translation.
     * Sets result on Singleton translated language object.
     * @param sourceCode source language code
     * @param targetCode target language code
     * @param text text to translate
     * @throws NoTranslatorFOundException custom exception if no translator is found
     */
    @Override
    public void determineLanguageTranslatorAndTranslate(String sourceCode, String targetCode, String text) throws NoTranslatorFOundException {
        invokeTranslationMethod(sourceCode, targetCode, text);
        translatedLanguage = TranslatedLanguage.getInstance();
        displayName = TranslatedDisplayName.getInstance();
    }

    /**
     * Uses the source and target language codes to build a class name in following format sourceCdToTargetCdTranslator, since all language codes are expected to pbe two letters
     * Finds the class name using reflection
     * Finds the method on the translator (All translators are expected to have 1 public method to handles translation)
     * Invokes the method, passing the target code and text to the method.
     * @param sourceCode source language code
     * @param targetCode target language code
     * @param text text to translate
     * @throws NoTranslatorFOundException custom exception if no translator is found
     */
    private void invokeTranslationMethod(String sourceCode, String targetCode, String text) throws NoTranslatorFOundException {
      String className = (TRANSLATOR_PACKAGE + ESupportedLanguages.LANGUAGE_MAP.get(sourceCode) + "To" + ESupportedLanguages.LANGUAGE_MAP.get(targetCode) + "Translator").replaceAll("\\s", "");
      try {
        Class<?> clazz = Class.forName(className);
        Method translate = Arrays.stream(clazz.getMethods()).findFirst().orElseThrow(ClassNotFoundException::new);
        translate.invoke(clazz.newInstance(), targetCode, text);
      } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
        throw new NoTranslatorFOundException("No translator found for " + sourceCode + " to " + targetCode, e);
      }
    }

    public TranslatedLanguage getTranslatedLanguage() {
        return translatedLanguage;
    }

    public TranslatedDisplayName getDisplayName() {
    return displayName;
  }
}


