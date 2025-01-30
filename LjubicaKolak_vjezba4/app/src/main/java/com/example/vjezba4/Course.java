package com.example.vjezba4;

public class Course {
    private Integer godina;
    private String ime;
    private String predavac;

    public Course() {
    }

    public Course(Integer godina, String ime, String predavac) {
        this.godina = godina;
        this.ime = ime;
        this.predavac = predavac;
    }

    public Integer getGodina() {
        return godina;
    }

    public String getIme() {
        return ime;
    }

    public String getPredavac() {
        return predavac;
    }

    public void setGodina(Integer godina) {
        this.godina = godina;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPredavac(String predavac) {
        this.predavac = predavac;
    }
}
