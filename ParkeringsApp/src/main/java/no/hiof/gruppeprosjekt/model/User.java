package no.hiof.gruppeprosjekt.model;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.jetbrains.annotations.NotNull;


public class User extends Email{
    private String name;
    private String lastName;
    private String password;

    public User(){}

    public User(String name,String lastName,String password, String email){
        super(email);
        this.name = name;
        this.lastName = lastName;
        this.password = password;
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
