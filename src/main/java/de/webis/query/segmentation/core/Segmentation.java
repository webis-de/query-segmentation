package de.webis.query.segmentation.core;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

public class Segmentation {
    
    private Long id;
    private List<String> segments;
    private String queryString;
    
    public Segmentation(Long id, List<String> segments) {
        super();
        this.id = id;
        this.segments = segments;
        this.setQueryString();
    }
    
    public Segmentation(Long id, String[] segments) {
       this(id, Lists.newArrayList(segments));
    }

    public List<String> getSegments() {
        return segments;
    }

    public void setSegments(List<String> segments) {
        this.segments = segments;
        this.setQueryString();
    }
    
    public String getQueryString() {
        return queryString;
    }

    private void setQueryString(){
        this.queryString =  StringUtils.join(this.segments, " ");
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String toString(){
        return this.id + "\t" + StringUtils.join(this.segments, "|");
    }
    
    public String toStringSegments(){
        return StringUtils.join(this.segments, "|");
    }
}
