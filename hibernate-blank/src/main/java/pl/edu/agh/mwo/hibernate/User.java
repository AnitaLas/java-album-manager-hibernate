package pl.edu.agh.mwo.hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "UserId")
    private Set<Album> users = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "PhotoLikes",
            joinColumns = @JoinColumn(name = "UserId"),
            inverseJoinColumns = @JoinColumn(name = "PhotoId"))

    private Set<Photo> photos = new HashSet<>();

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

    public void addAlbum(Album album) {
        this.users.add(album);
    }

    public void removeAlbum(Album album) {
        this.users.remove(album);
    }

    public Set<Album> getAlbums() {
        return this.users;
    }

    public void addPhoto(Photo photo) {
        this.photos.add(photo);
    }
    public void removePhoto(Photo photo) {
        this.photos.remove(photo);
    }
    public Set<Photo> getPhotos() {
        return this.photos;
    }



    @Override
    public String toString() {
        return "User name=" + name + ", " + id;
    }



}
