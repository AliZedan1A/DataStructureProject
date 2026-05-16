
package com.mycompany.datastructureproject.core.models;


public class Result<T> {
    private T responseData;
    private boolean isSuccess;
    private String comment = "None";
    public Result(boolean isSuccess){
        this.isSuccess = isSuccess;
    }
    public Result(boolean isSuccess,String comment){
        this.isSuccess = isSuccess;
        this.comment=comment;
    }
    public Result(boolean isSuccess,T respnseData)
    {
        this.isSuccess = isSuccess;
        this.responseData = respnseData;
    }
    public Result(boolean isSuccess,T respnseData,String comment)
    {
        this.isSuccess = isSuccess;
        this.responseData = respnseData;
        this.comment = comment;
    }
    public boolean isSuccess() {
        return this.isSuccess;
    }
    public T getData()
    {
        return this.responseData;
    }
    public String getComment()
    {
        return this.comment;
    }
}
