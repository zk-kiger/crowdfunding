package com.kiger.atcrowdfunding.util;

import java.util.List;

/**
 * @ClassName Page
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/9/15 18:38
 * @Version 1.0
 */

public class Page {

    private Integer pageno;
    private Integer pagesize;
    private List data;
    private Integer totalSize;
    private Integer totalno;

    public Page() {
    }

    public Page(Integer pageno, Integer pagesize) {
        if (pageno <= 0) {
            this.pageno = 1;
        }
        if (pagesize <= 0) {
            this.pageno = 10;
        }
        this.pageno = pageno;
        this.pagesize = pagesize;
    }

    public Integer getPageno() {
        return pageno;
    }

    public void setPageno(Integer pageno) {
        this.pageno = pageno;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
        this.totalno = (totalSize%pagesize) == 0 ? (totalSize/pagesize) : (totalSize/pagesize+1);
    }

    public Integer getTotalno() {
        return totalno;
    }

    private void setTotalno(Integer totalno) {
        this.totalno = totalno;
    }

    public Integer  getStartIndex() {
        return (this.pageno - 1) * pagesize;
    }
}
