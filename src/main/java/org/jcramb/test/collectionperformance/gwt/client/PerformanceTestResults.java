package org.jcramb.test.collectionperformance.gwt.client;

public class PerformanceTestResults {

    private final TimeResult setResults;

    private final TimeResult listResults;

    public PerformanceTestResults(TimeResult listResults, TimeResult setResults) {
        this.setResults = setResults;
        this.listResults = listResults;
    }

    public TimeResult getSetResults() {
        return setResults;
    }

    public TimeResult getListResults() {
        return listResults;
    }
}