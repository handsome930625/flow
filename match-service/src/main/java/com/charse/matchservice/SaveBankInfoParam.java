package com.charse.matchservice;

public class SaveBankInfoParam {
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 银行行号
     */
    private String bankNumber;
    /**
     * 开户城市
     */
    private String cityName;
    /**
     * 开户支行
     */
    private String depositBranchBank;
    /**
     * 开户名
     */
    private String depositName;
    /**
     * 银行卡号
     */
    private String depositNumber;
    /**
     * 手机号
     */
    private String phoneNumber;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDepositBranchBank() {
        return depositBranchBank;
    }

    public void setDepositBranchBank(String depositBranchBank) {
        this.depositBranchBank = depositBranchBank;
    }

    public String getDepositName() {
        return depositName;
    }

    public void setDepositName(String depositName) {
        this.depositName = depositName;
    }

    public String getDepositNumber() {
        return depositNumber;
    }

    public void setDepositNumber(String depositNumber) {
        this.depositNumber = depositNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"bankName\":\"")
                .append(bankName).append('\"');
        sb.append(",\"bankNumber\":\"")
                .append(bankNumber).append('\"');
        sb.append(",\"cityName\":\"")
                .append(cityName).append('\"');
        sb.append(",\"depositBranchBank\":\"")
                .append(depositBranchBank).append('\"');
        sb.append(",\"depositName\":\"")
                .append(depositName).append('\"');
        sb.append(",\"depositNumber\":\"")
                .append(depositNumber).append('\"');
        sb.append(",\"phoneNumber\":\"")
                .append(phoneNumber).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
