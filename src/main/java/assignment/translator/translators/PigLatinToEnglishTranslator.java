package assignment.translator.translators;

import assignment.translator.domain.TranslatedDisplayName;
import assignment.translator.domain.TranslatedLanguage;

public class PigLatinToEnglishTranslator {

    /**
     * Translates the provided text from Pig Latin to English
     * Sets the results on relevant Singleton objects, that can be accessed in controller.
     *
     * @param languageCode language code to translate language to
     * @param text         text to translate
     */
    public void translateToEnglish(String languageCode, String text) {
        String[] wordArray = text.split(" ");
        StringBuilder translatedSentence = new StringBuilder();
        for (String word : wordArray) {
            translatedSentence.append(translateWord(word.toLowerCase())).append(" ");
        }
        String translatedString = formatCapitalizationAndFullStop(translatedSentence.toString());
        TranslatedLanguage.getInstance().setLanguageCode(languageCode);
        TranslatedLanguage.getInstance().setTranslation(translatedString);
        TranslatedDisplayName.getInstance().setDisplayName(translatedString);
    }

    /**
     * Translates the specific word from pig latin to english
     *
     * @param wordToTranslate specific word to translate
     * @return translated word
     */
    private String translateWord(String wordToTranslate) {
        String[] splitOnHyphen = wordToTranslate.split("-");
        if (splitOnHyphen[1].length() < 2) {
            return splitOnHyphen[0];
        }
        return translateConsonant(splitOnHyphen[0], splitOnHyphen[1]);
    }

    /**
     * @param remainder          string remainder after word has been split on hyphen
     * @param untranslatedString string unaffected by translation
     * @return
     */
    private String translateConsonant(String remainder, String untranslatedString) {
        return untranslatedString.substring(0, untranslatedString.length() - 2) + remainder;
    }

    /**
     * Handles sentence Capitalization by making first letter in sentence a capital letter and removes full stop at the end.
     *
     * @param translatedSentence sentence translated to Pig Latin
     * @return translated sentence
     */
    private String formatCapitalizationAndFullStop(String translatedSentence) {
        return translatedSentence.substring(0, 1).toUpperCase() + translatedSentence.substring(1, translatedSentence.length() - 1);
    }
}
