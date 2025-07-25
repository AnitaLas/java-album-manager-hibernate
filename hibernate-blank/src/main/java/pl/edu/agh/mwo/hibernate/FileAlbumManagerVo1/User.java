package pl.edu.agh.mwo.hibernate.FileAlbumManagerVo1;

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
    private Set<Album> albums = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "PhotoLikes",
            joinColumns = @JoinColumn(name = "UserId"),
            inverseJoinColumns = @JoinColumn(name = "PhotoId"))
    private Set<Photo> photos = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "Friends",
            joinColumns = @JoinColumn(name = "InvitationSentByUserId"),
            inverseJoinColumns = @JoinColumn(name = "InvitationAcceptedByUserId"))
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

    public void addAlbum(Album album) {
        this.albums.add(album);
    }

    public void removeAlbum(Album album) {
        this.albums.remove(album);
    }

    public Set<Album> getAlbums() {
        return this.albums;
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

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        System.out.println("3 = " + user.getName());
        this.users.remove(user);
    }

    public Set<User> getUsers() {
        return this.users;
    }

    @Override
    public String toString() {
        return "user name: " + this.name;
    }

}
