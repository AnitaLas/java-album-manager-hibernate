package pl.edu.agh.mwo.hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private Date date;
    @Column
    private int albumId;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "PhotoLikes",
            joinColumns = @JoinColumn(name = "PhotoId"),
            inverseJoinColumns = @JoinColumn(name = "userId"))
    private Set<User> users = new HashSet<>();

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAlbumId() {
        return this.albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public Set<User> getUsers() {
        return this.users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }
    public void removeUser(User user) {
        this.users.remove(user);
    }

}
