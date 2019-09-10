package assignment.translator.controllers;

import io.restassured.http.ContentType;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class LanguageTranslatorAPITest {

    private static final String HOST = "http://localhost:5000";
    private static final String GET_LANGUAGES = "/languages";
    private static final String TRANSLATE_LANGUAGE = "/translate";

    @Test public void
    language_list_returns_language_codes_with_200_response_code() {
        when()
            .get(HOST + GET_LANGUAGES)
            .then()
            .statusCode(200)
            .body("languages.languageCode[0]", equalTo("en"))
            .body("languages.languageCode[1]", equalTo("pi"));
    }

    @Test public void
    language_list_returns_language_codes_with_english_display_names_with_200_response_code() {
        when()
                .get(HOST + GET_LANGUAGES + "?displayLanguageCode=en")
                .then()
                .statusCode(200)
                .body("languages[0].displayName", equalTo("English"))
                .body("languages[1].displayName", equalTo("Pig Latin"));
    }

    @Test public void
    language_list_returns_language_codes_with_pi_display_names_with_200_response_code() {
        when()
                .get(HOST + GET_LANGUAGES + "?displayLanguageCode=pi")
                .then()
                .statusCode(200)
                .body("languages[0].displayName", equalTo("English-ay"))
                .body("languages[1].displayName", equalTo("Ig-pay atin-lay"));
    }

    @Test public void
    language_list_returns_language_codes_when_language_code_omitted() {
        when()
                .get(HOST + GET_LANGUAGES + "?displayLanguageCode=")
                .then()
                .statusCode(200)
                .body("languages.languageCode[0]", equalTo("en"))
                .body("languages.languageCode[1]", equalTo("pi"));
    }

    @Test public void
    translate_to_pig_latin() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("sourceLanguage", "en");
        requestMap.put("targetLanguage", "pi");
        requestMap.put("text", "Winter is coming");
        given()
            .contentType(ContentType.JSON)
        .when()
            .body(requestMap)
            .post(HOST + TRANSLATE_LANGUAGE)
        .then()
            .statusCode(200)
            .body("languageCode", equalTo("pi"))
            .body("translation", equalTo("Inter-way is-ay oming-cay"));
    }

    @Test public void
    translate_to_english() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("sourceLanguage", "pi");
        requestMap.put("targetLanguage", "en");
        requestMap.put("text", "Old-hay e-thay oor-day");
        given()
            .contentType(ContentType.JSON)
        .when()
            .body(requestMap)
            .post(HOST + TRANSLATE_LANGUAGE)
        .then()
            .statusCode(200)
            .body("languageCode", equalTo("en"))
            .body("translation", equalTo("Hold the door"));
    }

    @Test public void
    test_error_code_for_no_translator() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("sourceLanguage", "fr");
        requestMap.put("targetLanguage", "en");
        requestMap.put("text", "J'aimerais une bière");
        given()
            .contentType(ContentType.JSON)
        .when()
            .body(requestMap)
            .post(HOST + TRANSLATE_LANGUAGE)
        .then()
            .statusCode(418);
    }

    @Test public void
    test_punctuation_and_capitalization() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("sourceLanguage", "en");
        requestMap.put("targetLanguage", "pi");
        requestMap.put("text", "This is a full sentence, right?");
        given()
            .contentType(ContentType.JSON)
        .when()
            .body(requestMap)
            .post(HOST + TRANSLATE_LANGUAGE)
        .then()
            .statusCode(200)
            .body("languageCode", equalTo("pi"))
            .body("translation", equalTo("Is-thay is-ay a-ay ull-fay entence-say, ight-ray?"));
    }
}