package models;

public class Depense extends GenericCRUD{
    private int libelle;
    private int montant;
    private int idUser;
    public Depense(){
        super(Depense.class);
    }
    public Depense(int libelle, int montant, int idUser) {
        super(Depense.class);
        this.libelle = libelle;
        this.montant = montant;
        this.idUser = idUser;
    }
    public int getLibelle() {
        return libelle;
    }
    public void setLibelle(int libelle) {
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
