package com.gwc.quizapp.sourcedata;

public enum SourceType {
    SAMPLE_SOURCE("SampleSource");

    public Class sourceClass;

    private SourceType(String clazzName){
        if("SampleSource".equalsIgnoreCase(clazzName)){
            this.sourceClass = SampleSource.class;
        }
    }
}
