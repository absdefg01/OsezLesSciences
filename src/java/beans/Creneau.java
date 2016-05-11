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
public class Creneau {
    private String idCreneau;
    private String dateCreneau;
    private String nbEleveMax;
    private String matiere;
    private String mention;

    public String getMention() {
        return mention;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }
    private String enseignant;

    public String getIdCreneau() {
        return idCreneau;
    }

    public void setIdCreneau(String idCreneau) {
        this.idCreneau = idCreneau;
    }

    public String getDateCreneau() {
        return dateCreneau;
    }

    public void setDateCreneau(String dateCreneau) {
        this.dateCreneau = dateCreneau;
    }

    public String getNbEleveMax() {
        return nbEleveMax;
    }

    public void setNbEleveMax(String nbEleveMax) {
        this.nbEleveMax = nbEleveMax;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(String enseignant) {
        this.enseignant = enseignant;
    }
}
