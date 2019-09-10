package assignment.translator.translators;

import assignment.translator.domain.TranslatedDisplayName;
import assignment.translator.domain.TranslatedLanguage;
import assignment.translator.enums.EVowels;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnglishToPigLatinTranslator {

    private static final String VOWEL_TRANSLATION = "-ay";
    private static final String CONSONANT_TRANSLATION = "ay";

    /**
     * Translates the provided text from English to Pig Latin
     * Sets the results on relevant Singleton objects, that can be accessed in controller.
     * @param languageCode language code to translate language to
     * @param text text to translate
     */
    public void translateToPigLatin(String languageCode, String text) {
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
     * Checks if first letter of word is a vowel or consonant and handles translation.
     * @param wordToTranslate specific word to translate
     * @return translated word
     */
    private String translateWord(String wordToTranslate) {
        return EVowels.VOWEL_LIST.contains(wordToTranslate.charAt(0)) ? translateVowel(wordToTranslate) : translateConsonant(wordToTranslate);
    }

    /**
     * Translates vowel word to Pig Latin according to below rules:
     *      If the word begins with a vowel, then append "-ay" to the word
     * @param vowelWord word that starts with a vowel
     * @return translated word
     */
    private String translateVowel(String vowelWord) {
        return vowelWord.concat(VOWEL_TRANSLATION);
    }

    /**
     * Translates consonant word to Pig Latin according to below rules:
     *      If the word begins with 1 or more consonants, move the group of consonants to the end of the word with a hyphen and add "ay".
     * @param consonantWord word that starts with a consonant
     * @return translated word
     */
    private String translateConsonant(String consonantWord) {
        StringBuilder consonantString = new StringBuilder();
        int firstVowelIndex = 0;
        char[] consonantWordArray = consonantWord.toCharArray();

        for (int i = 0; i < consonantWordArray.length; i++) {
            if (EVowels.VOWEL_LIST.contains(consonantWordArray[i])) {
                firstVowelIndex = i;
                break;
            } else {
                consonantString.append(consonantWordArray[i]);
            }
        }

        String wordRemainder = consonantWord.substring(firstVowelIndex, consonantWordArray.length);
        String unformatted = wordRemainder + "-" + consonantString + CONSONANT_TRANSLATION;
        return preservePunctuation(unformatted);
    }

    /**
     * Handles sentence Capitalization by making first letter in sentence a capital letter and removes full stop at the end.
     * @param translatedSentence sentence translated to Pig Latin
     * @return translated sentence
     */
    private String formatCapitalizationAndFullStop(String translatedSentence) {
        return translatedSentence.substring(0, 1).toUpperCase() + translatedSentence.substring(1, translatedSentence.length() - 1);
    }

    /**
     * Preserves the punctuation in the word
     * @param unformattedWord translated word with unpreserved punctuation
     * @return word with punctuation preserved
     */
    private String preservePunctuation(String unformattedWord) {
        String pattern = "(?![-])\\p{Punct}";
        Pattern r = Pattern.compile(pattern);    // Create a Pattern object
        Matcher m = r.matcher(unformattedWord);
        if (m.find()) {
            unformattedWord = unformattedWord.replaceAll(pattern, "") + m.group();
        }
        return unformattedWord;
    }
}
