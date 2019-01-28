package ppcraft.objects;

import javafx.beans.property.SimpleStringProperty;

public class Site{

    private SimpleStringProperty address = new SimpleStringProperty("");
    private SimpleStringProperty login = new SimpleStringProperty("");
    private SimpleStringProperty password = new SimpleStringProperty("");

    public Site(){
    }

    public Site(String address, String login, String password){
        this.address = new SimpleStringProperty(address);
        this.login = new SimpleStringProperty(login);
        this.password = new SimpleStringProperty(password);
    }

    public String getAddress(){
        return address.get();
    }

    public void setAddress(String address){
        this.address.set(address);
    }

    public String getLogin(){
        return login.get();
    }

    public void setLogin(String login){
        this.login.set(login);
    }

    public String getPassword(){
        return password.get();
    }

    public void setPassword(String password){
        this.password.set(password);
    }

    public SimpleStringProperty addressProperty(){
        return address;
    }

    public SimpleStringProperty loginProperty(){
        return login;
    }

    public SimpleStringProperty passwordProperty(){
        return password;
    }
}
