package models;

public class Prevision extends GenericCRUD{
    private String libelle;
    private int montant;
    private int idUser;
    public Prevision() {
        super(Prevision.class);
    }
    public Prevision(String libelle, int montant, int idUser) {
        super(Prevision.class);
        this.libelle = libelle;
        this.montant = montant;
        this.idUser = idUser;
    }
    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    public int getMontant() {
        return montant;
    }
    public void setMontant(int montant) {
        this.montant = montant;
    }
    public int getIdUser() {
        return idUser;
    }
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
