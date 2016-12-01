package c.egco428.a23269.userLocation;

/**
 * Created by USER on 6/11/2559.
 */
public class Comment {
    private String user;
    private String password;
    private String latitude;
    private String longtitude;


    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getUser(){return user;}
    public void setUser(String user){this.user = user;}
    public String getPassword() {return password;}
    public void setPassword(String password){this.password = password;}
    public String getLatitude(){return latitude;}
    public void setLatitude(String latitude){this.latitude=latitude;}
    public String getLongtitude(){return longtitude;}
    public void setLongtitude(String longtitude){this.longtitude=longtitude;}

}

