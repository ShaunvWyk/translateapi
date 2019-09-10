package assignment.translator;

import assignment.translator.enums.ESupportedLanguages;
import assignment.translator.enums.EVowels;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TranslatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TranslatorApplication.class, args);
		ESupportedLanguages.initLanguages();
		EVowels.initVowels();
	}

}
