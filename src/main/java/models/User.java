package models;
public class User extends GenericCRUD{
    private String name;
    private String password;
    public User( String name, String password) {
        super(User.class);
        this.name = name;
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public User(){
        super(User.class);
    }
}