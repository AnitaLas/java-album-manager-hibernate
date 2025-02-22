package pl.edu.agh.mwo.hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private int userId;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "AlbumId")
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Set<Photo> getPhotos() {
        return this.photos;
    }
    public void removePhoto(Photo photo) {
        this.photos.remove(photo);
    }
    public void addPhoto(Photo photo) {
        this.photos.add(photo);
    }



    @Override
    public String toString() {
        return "Album name: " + this.name + "  |||  description: " + this.description;
    }

}
