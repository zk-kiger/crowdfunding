package com.kiger.atcrowdfunding.bean;

/**
 * @ClassName Ticket
 * @Description TODO
 * @Author zk_kiger
 * @Date 2019/10/10 19:41
 * @Version 1.0
 */

public class Ticket {

    private int id;

    private int memberid;

    private String piid;

    private String status;

    private String authcode;

    private String pstep;

    public Ticket() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberid() {
        return memberid;
    }

    public void setMemberid(int memberid) {
        this.memberid = memberid;
    }

    public String getPiid() {
        return piid;
    }

    public void setPiid(String piid) {
        this.piid = piid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode;
    }

    public String getPstep() {
        return pstep;
    }

    public void setPstep(String pstep) {
        this.pstep = pstep;
    }
}
