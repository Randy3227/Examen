package models;

public class BaseObject {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        System.out.println("ID mis à jour dans setId : " + id);  // Ajout d'un log pour débogage
    }
}