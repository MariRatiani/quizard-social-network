package model.objects;

public class User {
    String email, name, password;
    int user_id;
    boolean isAdmin;
    public User(String email, String name, String password, int id, boolean isAdmin){
        this.email = email;
        this.name = name;
        this.password = password;
        this.user_id = id;
        this.isAdmin = isAdmin;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getUser_id() {
        return user_id;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}

