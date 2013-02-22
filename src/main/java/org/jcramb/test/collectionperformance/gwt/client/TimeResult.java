package org.jcramb.test.collectionperformance.gwt.client;

public class TimeResult {

    private final long createTime;

    private final long findTime;

    private final long deleteTime;

    private final long overallTime;

    public TimeResult(long createTime, long findTime, long deleteTime, long overallTime) {
        this.createTime = createTime;
        this.findTime = findTime;
        this.deleteTime = deleteTime;
        this.overallTime = overallTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public long getFindTime() {
        return findTime;
    }

    public long getDeleteTime() {
        return deleteTime;
    }

    public long getOverallTime() {
        return overallTime;
    }
}