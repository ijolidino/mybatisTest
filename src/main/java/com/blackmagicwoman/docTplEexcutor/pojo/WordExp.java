package com.blackmagicwoman.docTplEexcutor.pojo;

public class WordExp {
    /**表达式开始节点*/
    private int startIdx;
    /**表达式结束节点*/
    private int endIdx;
    /**表格开始纵坐标*/
    private int startVIdx;
    /**表格结束横坐标*/
    private int endVIdx;
    /**表达式内容*/
    private String wordExp;
    /**表达式类型*/
    private String expType;

    public void setStartIdx(int startIdx) {
        this.startIdx = startIdx;
    }
    public int getStartIdx() {
        return startIdx;
    }
    public int getEndIdx() {
        return endIdx;
    }
    public void setEndIdx(int endIdx) {
        this.endIdx = endIdx;
    }
    public void setStartVIdx(int startVIdx) {
        this.startVIdx = startVIdx;
    }
    public int getStartVIdx() {
        return startVIdx;
    }

    public int getEndVIdx() {
        return endVIdx;
    }
    public void setEndVIdx(int endVIdx) {
        this.endVIdx = endVIdx;
    }

    public void setWordExp(String wordExp) {
        this.wordExp = wordExp;
    }
    public String getWordExp() {
        return wordExp;
    }
    public void setExpType(String expType) {
        this.expType = expType;
    }
    public String getExpType() {
        return expType;
    }
}
