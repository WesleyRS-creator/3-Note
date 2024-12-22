package connect.dao.object.etude;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import connect.dao.DAOExecuter;
import main.object.etude.Matiere;

public class MatiereDAO {
    private Map<String, Connection> myConnections;
    private Class<?> c;

    public void setC(Class<?> c) {
        this.c = c;
    }

    public Class<?> getC() {
        return c;
    }

    public void setMyConnections(Map<String, Connection> myConnections) {
        Matiere aa = new Matiere();
        setC(aa.getClass());
        this.myConnections = myConnections;
    }

    public Map<String, Connection> getMyConnections() {
        return myConnections;
    }

    public Matiere getMatiereById(Number value){
        Map<String, Object> mo = new HashMap<>();
        mo.put("ID_MATIERE", value);
        DAOExecuter daoE = new DAOExecuter(getMyConnections());
        List<Object> answer = daoE.getFROM("Oracle", "Matieres", getC(), mo, false);
        boolean estNulle = false;
        if (answer.size() == 0) {
            estNulle = true;
        }
        
        return estNulle ? null : (Matiere) answer.get(0);
    }

    public MatiereDAO(Map<String, Connection> mc){
        setMyConnections(mc);   
    }

}
