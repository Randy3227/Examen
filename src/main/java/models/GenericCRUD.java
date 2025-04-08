package models;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.*;

@SuppressWarnings("rawtypes")
public class GenericCRUD extends BaseObject implements CRUD {
    private Class clazz;

    public GenericCRUD(Class clazz) {
        this.clazz = clazz;
    }

    private Connection getConnection() throws SQLException {
        return UtilDAO.createConnection();
    }

    public GenericCRUD() {

    }

    @Override
    public List<Object> findAll() throws Exception {
        List<Object> result = new ArrayList<>();
        String table = clazz.getSimpleName();

        Field[] fields = clazz.getDeclaredFields();
        Field[] parentFields = BaseObject.class.getDeclaredFields(); // Ajout de BaseObject

        String sql = "SELECT * FROM " + table;
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                @SuppressWarnings("unchecked")
                Object entity = clazz.getDeclaredConstructor().newInstance();

                for (Field field : fields) {
                    field.setAccessible(true);
                    field.set(entity, rs.getObject(field.getName()));
                }

                for (Field field : parentFields) {
                    field.setAccessible(true);
                    field.set(entity, rs.getObject(field.getName()));
                }

                result.add(entity);
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object findById(int id) throws Exception {
        String table = clazz.getSimpleName();
        Field[] fields = clazz.getDeclaredFields();
        Object entity = null;
    
        String sql = "SELECT * FROM " + table + " WHERE id = ?";
        System.out.println("Requête SQL préparée : " + sql);
        System.out.println("ID recherché : " + id);
    
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setInt(1, id);
            System.out.println("Exécution de la requête...");
    
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Données trouvées pour ID " + id);
                    entity = clazz.getDeclaredConstructor().newInstance();
    
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Object value = rs.getObject(field.getName());
                    
                        if (field.getName().equalsIgnoreCase("id")) {  
                            System.out.println("ID trouvé dans la BDD : " + value); // Debug
                        }
                    
                        field.set(entity, value);
                        System.out.println("Attribut : " + field.getName() + " = " + value);
                    }
                } else {
                    System.out.println("Aucun résultat trouvé pour ID " + id);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception attrapée : " + e.getMessage());
            e.printStackTrace();
        }
    
        System.out.println("Objet retourné : " + entity);
        return entity;
    }

    @Override
    public void save(Object entity) throws Exception {
        String table = clazz.getSimpleName();
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder columns = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();

        for (Field field : fields) {
            columns.append(field.getName()).append(",");
            placeholders.append("?,");

            field.setAccessible(true);
        }
        columns.setLength(columns.length() - 1);
        placeholders.setLength(placeholders.length() - 1);

        String sql = "INSERT INTO " + table + " (" + columns + ") VALUES (" + placeholders + ")";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int index = 1;
            for (Field field : fields) {
                pstmt.setObject(index++, field.get(entity));
            }
            pstmt.executeUpdate();
        }
    }
    @Override
public void update(Object entity) throws Exception {
    String table = clazz.getSimpleName();
    Field[] fields = clazz.getDeclaredFields();
    Field[] parentFields = BaseObject.class.getDeclaredFields();
    StringBuilder setClause = new StringBuilder();
    Field idField = null;

    for (Field field : fields) {
        field.setAccessible(true);
        if (field.getName().equalsIgnoreCase("id")) {
            idField = field;
        } else {
            setClause.append(field.getName()).append(" = ?, ");
        }
    }

    for (Field field : parentFields) {
        field.setAccessible(true);
        if (field.getName().equalsIgnoreCase("id")) {
            idField = field;
        }else {
            setClause.append(field.getName()).append(" = ?, ");
        }
    }

    if (idField == null) {
        throw new IllegalArgumentException("L'entité ne contient pas de champ 'id'.");
    }

    setClause.setLength(setClause.length() - 2);

    System.out.println("ID utilisé dans l'UPDATE : " + idField.get(entity));  // Vérification de l'ID avant la requête

    String sql = "UPDATE " + table + " SET " + setClause + " WHERE id = ?";
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        int index = 1;
        for (Field field : fields) {
            if (!field.getName().equalsIgnoreCase("id")) {
                pstmt.setObject(index++, field.get(entity));
            }
        }
        for (Field field : parentFields) {
            if (!field.getName().equalsIgnoreCase("id")) {
                pstmt.setObject(index++, field.get(entity));
            }
        }

        pstmt.setObject(index, idField.get(entity));
        pstmt.executeUpdate();
    }
}

    
    @Override
    public void delete(Object entity) throws SQLException {
        if (!clazz.isInstance(entity)) {
            System.err.println("L'objet fourni n'est pas du bon type !");
            return;
        }

        String table = clazz.getSimpleName();
        String sql = "DELETE FROM " + table + " WHERE id = ?";

        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            @SuppressWarnings("unchecked")
            int id = (int) clazz.getMethod("getId").invoke(entity);

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (ReflectiveOperationException e) {
            throw new SQLException("Impossible d'accéder à l'identifiant de l'entité", e);
        }
    }

    public List<Object> paginated(int limit, int offset) throws Exception {
        List<Object> resultList = new ArrayList<>();
        String tableName = clazz.getSimpleName();

        String query = "SELECT * FROM " + tableName + " LIMIT ? OFFSET ?";

        try (Connection con = getConnection();
                PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setInt(1, limit);
            pstmt.setInt(2, offset);

            try (ResultSet rs = pstmt.executeQuery()) {
                Field[] fields = clazz.getDeclaredFields();

                while (rs.next()) {
                    @SuppressWarnings("unchecked")
                    Object entityTemp = clazz.getDeclaredConstructor().newInstance();

                    for (Field field : fields) {
                        field.setAccessible(true);
                        try {
                            field.set(entityTemp, rs.getObject(field.getName()));
                        } catch (IllegalArgumentException e) {
                            System.err.println("Champ non trouvé ou type incompatible : " + field.getName());
                        }
                    }

                    resultList.add(entityTemp);
                }
            }
        } catch (SQLException e) {
            throw new Exception("Erreur lors de l'obtention des données paginées pour la table " + tableName, e);
        }
        return resultList;
    }
}
