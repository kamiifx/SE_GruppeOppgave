package no.hiof.gruppeprosjekt.model;

public class User extends Email{

    private String name;
    private String lastName;
    private String password;
    private int id ;

    public User(){

    }

    public User(int id, String name,String lastName,String password, String email){
        super(email);
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return getName() + " " + getLastName();
    }
}
