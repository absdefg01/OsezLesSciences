/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author VIC
 */
public class Eleve {
    private String nom;
    private String prenom;
    private String mail;
    private String mdp;

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setMotDePasse(String motDePasse) {
        this.mdp = motDePasse;
    }

    public String getMotDePasse() {
        return mdp;
    }
}
