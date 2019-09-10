package assignment.translator.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Languages {
    @JsonProperty("languages")
    private List<Language> languages;

    public Languages(List<Language> languages) {
        this.languages = languages;
    }
}
