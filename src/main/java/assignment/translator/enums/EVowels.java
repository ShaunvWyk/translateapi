package assignment.translator.enums;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum EVowels {

    A('a'),
    E('e'),
    I('i'),
    O('o'),
    U('u');

    public static final List<Character> VOWEL_LIST = new ArrayList<>();
    private Character letter;


    EVowels(Character letter) {
        this.letter = letter;
    }

    public static void initVowels() {
        for (EVowels vowel : values()) {
            VOWEL_LIST.add(vowel.letter);
        }
    }
}
