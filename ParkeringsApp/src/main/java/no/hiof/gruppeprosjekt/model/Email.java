package no.hiof.gruppeprosjekt.model;

abstract class Email {
private String mail;

    public Email (){ }

    public Email(String mail){
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
