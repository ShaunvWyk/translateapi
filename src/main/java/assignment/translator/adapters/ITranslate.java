package assignment.translator.adapters;

import assignment.translator.exception.NoTranslatorFOundException;

public interface ITranslate {
    public abstract void determineLanguageTranslatorAndTranslate(String sourceCode, String targetCode, String textToTranslate) throws NoTranslatorFOundException;
}
