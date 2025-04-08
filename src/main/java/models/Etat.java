package models;

import java.util.ArrayList;
import java.util.List;


public final class Etat {
    @SuppressWarnings("unchecked")
    public static int somme_depense_par_prevision_par_user(int idPrevision, int idUser){
        int somme_depense = 0;
       List<Depense> depenses = new ArrayList<>(); 
       
       try {
        depenses = (List<Depense>)(List<?>)new Depense().findAll();
        for (Depense depense : depenses) {
            if(depense.getIdUser() == idUser && depense.getLibelle() == idPrevision){
                somme_depense += depense.getMontant();
            }
        }
    }catch(Exception e){
        e.printStackTrace();
    }
    return somme_depense;
}
}
