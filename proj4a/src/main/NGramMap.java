package main;

import edu.princeton.cs.algs4.In;

import java.util.*;

import static main.TimeSeries.MAX_YEAR;
import static main.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    Map<String, TimeSeries> wordCounts = new HashMap<>();
    TimeSeries yearCounts = new TimeSeries();

    /**
     * Constructs an NGramMap from WORDHISTORYFILENAME and YEARHISTORYFILENAME.
     */
    public NGramMap(String wordHistoryFilename, String yearHistoryFilename) {

        wordsFileReader(wordHistoryFilename);
        yearsFileReader(yearHistoryFilename);

    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {

        if (wordCounts.containsKey(word)) {
            return new TimeSeries(wordCounts.get(word), startYear, endYear);
        }
        return new TimeSeries();

    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        return countHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(yearCounts, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {

        TimeSeries wordHistoryCount = countHistory(word, startYear, endYear);

        // Loop through if wordHistoryCount is not empty
        for (Map.Entry<Integer, Double> entry : wordHistoryCount.entrySet()) {
            Double totalYearlyCount = yearCounts.get(entry.getKey());
            entry.setValue(entry.getValue() / totalYearlyCount);
        }

        return wordHistoryCount;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        return weightHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {

        TimeSeries ts = new TimeSeries();

        // Add total counts
        for (String word : words) {
            TimeSeries wordTS = countHistory(word, startYear, endYear);
            for (Map.Entry<Integer, Double> entry : wordTS.entrySet()) {
                Integer k = entry.getKey();
                Double v = entry.getValue();

                if (ts.containsKey(k)) {
                    ts.put(k, ts.get(k) + v);
                } else {
                    ts.put(k, v);
                }
            }
        }

        // Get frequencies
        for (Map.Entry<Integer, Double> entry : ts.entrySet()) {
            Integer k = entry.getKey();
            Double v = entry.getValue();
            entry.setValue(v / yearCounts.get(k));
        }

        return ts;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, MIN_YEAR, MAX_YEAR);
    }

    private void wordsFileReader(String fileName) {

        In in = new In(fileName);

        while (!in.isEmpty()) {
            String nextLine = in.readLine();
            String[] words = nextLine.split("\t");

            // Get individual items
            String word = words[0];
            Integer year = Integer.parseInt(words[1]);
            Double freq = Double.parseDouble(words[2]);

            if (!wordCounts.containsKey(word)) {
                TimeSeries ts = new TimeSeries();
                ts.put(year, freq);
                wordCounts.put(word, ts);
            } else {
                wordCounts.get(word).put(year, freq);
            }
        }
    }


    private void yearsFileReader(String fileName) {

        In in = new In(fileName);

        while (!in.isEmpty()) {
            String nextLine = in.readLine();
            String[] words = nextLine.split(",");
            Integer year = Integer.parseInt(words[0]);
            Double freq = Double.parseDouble(words[1]);

            yearCounts.put(year, freq);
        }
    }
}
