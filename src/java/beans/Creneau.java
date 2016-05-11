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
    private String jour;
    private String heure;
    private String nbEleveMax;
    private String matiere;
    private String mention;
    private boolean confirmation;

    public boolean getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }


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
        return jour;
    }

    public void setDateCreneau(String jour) {
        this.jour = jour;
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
