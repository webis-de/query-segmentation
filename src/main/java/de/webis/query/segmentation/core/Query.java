package de.webis.query.segmentation.core;

public class Query {
    
    private long id;
    private String queryString;

    public Query(long id, String queryString) {
        super();
        this.id = id;
        this.queryString = QueryHelper.clearQuery(queryString);
    }
    
    public Query(String queryString){
    	this(1L, queryString);
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = QueryHelper.clearQuery(queryString);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String toString(){
        return this.id + "\t" + this.queryString;
    }
}
