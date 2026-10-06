package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {

    private final NGramMap ngm;

    public HistoryTextHandler(NGramMap ngm) {
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        StringBuilder result = new StringBuilder();
        for (String word : words) {
            TimeSeries history = ngm.weightHistory(word, startYear, endYear);
            result.append(word).append(": ").append(history).append("\n");
        }

        return result.toString();
    }
}
