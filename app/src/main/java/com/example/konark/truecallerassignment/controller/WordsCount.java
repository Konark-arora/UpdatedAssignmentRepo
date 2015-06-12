package com.example.konark.truecallerassignment.controller;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by konark on 11/6/15.
 */
public class WordsCount implements ProblemStatementInterface {

    private String dataResponse;

    public WordsCount(String response){
        this.dataResponse = response;
    }

    @Override
    public StringBuilder getData() {

        Map<String, Integer> occurrences = new LinkedHashMap<String, Integer>();

        // Split string into words after every space or newline
        String[] tokens = dataResponse.split(" |\\\n");
        StringBuilder wordsCount = new StringBuilder();

        for (String word : tokens) {
            Integer oldCount = occurrences.get(word);
            if (oldCount == null) {
                oldCount = 0;
            }
            occurrences.put(word, oldCount + 1);
        }

        for (String word : occurrences.keySet()) {
            Integer count = occurrences.get(word);
            wordsCount.append(word + ":" + count + "  ");
        }

        return wordsCount;
    }
}