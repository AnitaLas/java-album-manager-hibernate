package pl.edu.agh.mwo.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class Main {

    Session session;

    public static void main(String[] args) {
        Main main = new Main();

        // tu wstaw kod aplikacji

        //main.addUser();
        //main.printUsers();

		/*main.addUser();
		main.addAlbum("John album 1");
		main.addAlbum("John album 2");*/
        //main.addPhoto("Z 1", "13");
        //main.addPhoto("Z 2", "13");

        /*main.addPhoto("Z 1", "13");
        main.addPhotoLlike("Z 1");
        main.addPhotoLlike2("Z 1");
        main.addPhotoLlike3("lodowisko 1");
        main.addPhotoLlike4("lodowisko 1");*/

        // USER
        //main.deleteUser();
        //main.deleteUser2();


        // ALBUM
        //main.printAlbums();
        //main.deleteAlbum();
        //main.deleteAlbum2();


        // PICTURE
        // main.deletePhoto();


        // PHOTO LIKES
        //main.deletePhotoLlike();


        //main.addUser("Estera");
        //main.addUser("Marek");

        //main.addAlbum2("Marek", "nowy m2");
        //main.addAlbum3("Marek1", "nowy m2");


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        User userLogged = null;
        try {
            do {
                System.out.println();
                System.out.println("***** ALBUM MANAGERvO1 *****");
                boolean isUserAccountExis = false;
                boolean isLogin;
                do {
                    System.out.println("select option: 1 - log in, 2 - create account");
                    String decision0 = br.readLine();
                    if (decision0.equals("1")) {

                        do {
                            isLogin = false;
                            System.out.println("log in. user name: ");
                            String userName = br.readLine();
                            //isUserAccountExis = main.isUserExistsInDatabase(userName); // to do - change name xD
                            //if (isUserAccountExis) {
                            userLogged = main.getUserFromDatabase(userName);
                            if (userLogged != null) {
                                //userLogged = main.getUserFromDatabase(userName);
                                System.out.println("Welcome " + userLogged.getName());
                                isUserAccountExis = true;
                                isLogin = true;
                            } else {
                                System.out.println("user not found. Do you want to try login again or create account?");
                                System.out.println("select option: 1 - create account, 2 - try again");
                                String decision1 = br.readLine();
                                if (decision1.equals("1")) {
                                    main.addUser(userName); // to do ????
                                    userLogged = main.getUserFromDatabase(userName);
                                    isUserAccountExis = true;
                                    isLogin = true;
                                    System.out.println("Welcome " + userLogged.getName() + ". User account created.");
                                } else if (decision1.equals("2")) {
                                    isUserAccountExis = false;
                                } else {
                                    System.out.println("[E2] input is not correct");
                                }
                            }
                        } while (!isLogin);


                    } else if (decision0.equals("2")) {

                        //boolean isLogin;
                        do {
                            isLogin = false;
                            System.out.println("Create account. user name: ");
                            String userName = br.readLine();
                            //isUserAccountExis = main.isUserExistsInDatabase(userName);
                            //if (isUserAccountExis) {
                            userLogged = main.getUserFromDatabase(userName);
                            if (userLogged != null) {
                                userLogged = main.getUserFromDatabase(userName);
                                System.out.println("Welcome " + userLogged.getName());
                                System.out.println("Account exist. You have been automaticale login.");
                                isUserAccountExis = true;
                                isLogin = true;
                            } else {
                                main.addUser(userName);
                                userLogged = main.getUserFromDatabase(userName);
                                isUserAccountExis = true;
                                isLogin = true;
                                System.out.println("Welcome " + userLogged.getName() + ". User account created.");
                            }

                        } while (!isLogin);


                    } else {
                        System.out.println("[E3] input is not correct");
                    }


                } while (isUserAccountExis == false);


                do {
                    System.out.println();
                    System.out.println("WHAT DO YOU WANT TO DO? select option: ");

                    System.out.println(
                            //"select option: \n" +
                            "1-addAlbum, " +
                            "2-deleteAlbum, \n" +
                            "3-showMyAlbums, " +
                            //"4 - showAllUsersAlbums, " +
                            "5-showUserAlbums, " +
                            //"6 - showMyPhotos, " +
                            "7 - showPhotoInMyAlbum, \n" +
                            //"8 - showPhotoInUserAlbum, \n" +
                            "9-addPhoto, " +
                            "10-deletePhoto, \n" +
                            "11-likePhoto, " +
                            "12-noLikePhoto, \n" +
                            "20-addFriend, " +
                            "21-deleteFriend, \n" +
                            "999-logout, " +
                            "666-removeYourselfFromDatabase, ");

                    input = br.readLine();

                    // addAlbum
                    if (input.equals("1")) {
                        System.out.println("ADD album name: ");
                        //String albumName = br.readLine();

                        boolean isAlbumExist;
                        do {
                            isAlbumExist = false;
                            String albumName = br.readLine();
                            int result = main.getProcessingStatusWhileAddingAlbum(userLogged, albumName);
                            if (result == 1) {
                                System.out.println("Album added successfully.");
                                main.createNewAlbum(userLogged, albumName);
                                isAlbumExist = true;
                                //input = "";
                            } else if (result == 2) {
                                System.out.println("Album already exists.");
                                System.out.println("Do you want to try again with new name? 1-yes, 2-no");
                                String decision2 = br.readLine();
                                if (decision2.equals("1")) {
                                    isAlbumExist = false;
                                } else if (decision2.equals("2")) {
                                    isAlbumExist = true;
                                } else {
                                    System.out.println("[E33] input is not correct");
                                    isAlbumExist = true;
                                }
                            }
                        } while (isAlbumExist == false);


                    }
                    // deleteAlbum
                    else if (input.equals("2")) {
                        System.out.println("REMOVE album name: ");
                        String albumName = br.readLine();

                        if (main.isAlbumBelongToUser(userLogged, albumName)) {
                            main.deleteAlbum(userLogged, albumName);
                            System.out.println("Album removed successfully. All album data were deleted.");
                            // input = "";
                        } else {
                            System.out.println("Album does not exist or does not belong to user " + userLogged.getName() + ". Album cannot be deleted.");
                            // input = "";
                        }


                    }
                    //showMyAlbums
                    else if (input.equals("3")) {
                        main.printUserAlbums(userLogged);
                    }
                    // showUserAlbums
                    else if (input.equals("5")) {
                        System.out.println("User name which albums you want to see:");
                        String userName = br.readLine();
                        User user = main.getUserFromDatabase(userName);
                        if (user != null) {
                            main.printUserAlbums(user);
                        } else {
                            System.out.println("User not found.");
                        }
                    }
                    // showPhotoInMyAlbum
                    else if (input.equals("7")) {
                        System.out.println("Album name which picture you want to see:");
                        String albumName = br.readLine();

                        if (main.isAlbumBelongToUser(userLogged, albumName)) {
                            main.printPhoto(userLogged, albumName);
                        } else {
                            System.out.println("Album not found.");
                        }
                    }
                    // addPhoto
                    else if (input.equals("9")) {
                        System.out.println("album name where you want to add photo:");
                        String albumName = br.readLine();

                        if (main.isAlbumBelongToUser(userLogged, albumName)) {
                            System.out.println("ADD photo name: ");
                            String photoName = br.readLine();

                            boolean isPictureExist;
                            do {
                                isPictureExist = false;
                                int result = main.getProcessingStatusWhileAddingPhoto(userLogged, albumName, photoName);
                                if (result == 1) {
                                    main.addPhoto(photoName, albumName, userLogged);
                                    System.out.println("Photo added successfully.");
                                    isPictureExist = true;
                                } else if (result == 2) {
                                    System.out.println("Photo already exists in album.");
                                    System.out.println("Do you want to try again with new name? 1 - yes, 2 - no");
                                    String decision2 = br.readLine();
                                    if (decision2.equals("1")) {
                                        isPictureExist = false;
                                    } else if (decision2.equals("2")) {
                                        isPictureExist = true;
                                    } else {
                                        System.out.println("[E7] input is not correct");
                                        isPictureExist = true;
                                    }
                                }
                            } while (isPictureExist == false);

                        } else {
                            System.out.println("Album does not exist or does not belong to user " + userLogged.getName() + ". Photo cannot be added.");
                        }
                    }
                    // deletePhoto
                    else if (input.equals("10")) {
                        System.out.println("REMOVE photo name:");
                        String photoName = br.readLine();
                        System.out.println("album name:");
                        String albumName = br.readLine();

                        if (main.isPictureBelongToUser(userLogged, albumName, photoName)) {
                            main.deletePhoto(photoName, albumName, userLogged);
                            System.out.println("Picture deleted successfully.");
                        } else {
                            System.out.println("Picture/ album does not exist or does not belong to user " + userLogged.getName() + ". Picture cannot be deleted.");
                        }

                        /*int result = main.getProcessingStatusWhileAddingPhoto(user, albumName, photoName);
                        if (result == 1) {
                            System.out.println("Picture does not exist.");
                        }
                        else if (result == 2) {
                            main.deletePhoto(photoName, albumName, user);
                            System.out.println("Picture deleted successfully.");
                        }*/
                    }
                    // addPhotoLike
                    else if (input.equals("11")) {
                        System.out.println("ADD like photo name: ");
                        String photoName = br.readLine();
                        System.out.println("album name for photo to like: ");
                        String albumName = br.readLine();

                        int result = main.getProcessingStatusForPhotoLike(userLogged, albumName, photoName);
                        if (result == 4) {
                            main.addPhotoLike(userLogged, albumName, photoName);
                            System.out.println("You like photo - added successfully.");
                        } else if (result == 1) {
                            System.out.println(userLogged.getName() + " already like photo.");
                        } else if (result == 2) {
                            System.out.println("Photo does not exist in album.");
                        } else if (result == 3) {
                            System.out.println("Album does not exist.");
                        } else if (result == 5) {
                            System.out.println("You are not a friend with a person who take a photo.");
                        }

                    }
                    // deletePhotoLike
                    else if (input.equals("12")) {
                        System.out.println("REMOVE photoLIKE, photo name:");
                        String photoName = br.readLine();
                        System.out.println("album name:");
                        String albumName = br.readLine();

                        int result = main.getProcessingStatusForPhotoLike(userLogged, albumName, photoName);
                        if (result == 4) {
                            System.out.println("You never like photo.");
                        } else if (result == 1) {
                            main.deletePhotoLlike(userLogged, albumName, photoName);
                            System.out.println("You don't like photo - remove successfully.");
                        } else if (result == 2) {
                            System.out.println("Photo does not exist in album.");
                        } else if (result == 3) {
                            System.out.println("Album does not exist.");
                        } else if (result == 5) {
                            System.out.println("You are not a friend with a person who take a photo. You don't like photo.");
                        }
                    }
                    // removeYourselfFromDatabase
                    else if (input.equals("666")) {
                        System.out.println("Are you sure you want to remove yourself from database? 1-yes, 2-no");
                        String goodbye = br.readLine();
                        if (goodbye.equals("1")) {
                            input = "exit2";
                            System.out.println("Goodbye " + userLogged.getName() + "!");
                            main.deleteUser(userLogged);
                        } else if (goodbye.equals("2")) {
                            System.out.println("hmmm - wise choose (?) ");
                        }
                    }
                    // logout
                    else if (input.equals("999")) {
                        System.out.println("Are you sure you want to logout? 1-yes, 2-no");
                        String goodbye = br.readLine();
                        if (goodbye.equals("1")) {
                            input = "exit2";
                            System.out.println("Goodbye " + userLogged.getName() + "!");
                        } else if (goodbye.equals("2")) {
                            System.out.println();
                        }
                    }
                    // addFriend
                    else if (input.equals("20")) {
                        System.out.println("ADD FRIEND user name: ");
                        String friendName = br.readLine();
                        isUserAccountExis = main.isUserExistsInDatabase(friendName);
                        if (isUserAccountExis) {

                            if (main.areWeFriends(userLogged, friendName)) {
                                System.out.println("You are already friend of " + friendName + ".");
                            } else {
                                main.addFriend(userLogged, friendName);
                                System.out.println("You are now friend of " + friendName + ".");
                            }
                        } else {
                            System.out.println("User " + friendName + " does not exist.");
                        }
                    }
                    // removeFriend
                    else if (input.equals("21")) {
                        System.out.println("DELETE FRIEND, user name: ");
                        String friendName = br.readLine();
                        isUserAccountExis = main.isUserExistsInDatabase(friendName);
                        if (isUserAccountExis) {
                            if (main.areWeFriends(userLogged, friendName)) {
                                main.deleteFriend(userLogged, friendName);

                            } else {
                                System.out.println("You are not friend of " + friendName + ".");
                            }
                        } else {
                            System.out.println("User " + friendName + " does not exist.");
                        }
                    }


                } while (!input.equals("exit2"));


                //line = "exit";
            } while (!(input.equals("exit")));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        main.close();
    }

    public Main() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public void close() {
        session.close();
        HibernateUtil.shutdown();
    }

    //---------------------------------------  Users
    private void printUsers() {
        Query<User> query = session.createQuery("from User", User.class);
        List<User> users = query.list();

        System.out.println("### Users");
        for (User user : users) {
            System.out.println(user);
        }
    }

    private boolean isUserExistsInDatabase(String userName) {
        String hql = "From User u where u.name=" + "'" + userName + "'";

        Query<User> query = session.createQuery(hql, User.class);
        User user = query.uniqueResult();
        //System.out.println(query);
        if (user != null) {
            return true;
        }
        return false;
    }

    /*private int getUserId(User user) {

        try {
            String hql = "From User u where u.name=" + "'" + user.ge + "'";
            Query<User> query = session.createQuery(hql, User.class);
            User user = query.uniqueResult();

            if (user != null) {
                return user.getId();
            }


        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return 0;

    }*/

    private void addUser(String userName) {

        // if (checkIfUserExistsInDatabase(userName)) {
        //System.out.println("User " + userName + " already exists");
        //} else {
        User user = new User();
        user.setName(userName);

        Transaction transaction = session.beginTransaction();
        session.save(user); // gdzie user to instancja nowego usera
        transaction.commit();
        //}
    }

    private User getUserFromDatabase(String userName) {
        String hql = "From User u where u.name=" + "'" + userName + "'";
        Query<User> query = session.createQuery(hql, User.class);
        //User user = query.uniqueResult();
        return query.uniqueResult();
    }


    // delete user + albums + photos + likes
    private void deleteUser(User userLogged) {

        // int idToDelete = 8;

        // String hql = "From User a where a.id=" + idToDelete;

        //Query<User> query = session.createQuery(hql, User.class);
        //User user = query.uniqueResult();
        Transaction deleteTransaction = session.beginTransaction();

        for (Album album : userLogged.getAlbums()) {

            for (Photo photo : album.getPhotos()) {
                //photo.removeUser(user);
                //user.removePhoto(photo);
                session.delete(photo);
            }
            session.delete(album);
        }

        for(User user : userLogged.getUsers()) {

           deleteFriend(userLogged, user.getName()); //->  -->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> to do IllegalStateException -> session call twice
        }


        session.delete(userLogged);


        deleteTransaction.commit();
    }


    //---------------------------------------  Albums
    private void printAlbums() {
        Query<Album> query = session.createQuery("from Album", Album.class);
        List<Album> albums = query.list();

        System.out.println("### Albums");
        for (Album album : albums) {
            System.out.println(album);
        }
    }

    private void printUserAlbums(User user) {

        /*String hql = "From Album a where a.userId=" + user.getId();
        Query<Album> query = session.createQuery(hql, Album.class);
        List<Album> albums = query.list();*/

        System.out.println("### Albums, owner: " + user.getName());
        /*for (Album album : albums) {
            //System.out.println("Id: " + album.getId() + ", NAME: " + album.getName() + ", OPIS: " + album.getDescription());
            System.out.println(album);
        }*/

        for (Album album : user.getAlbums()) {
            //System.out.println("Id: " + album.getId() + ", NAME: " + album.getName() + ", OPIS: " + album.getDescription());
            System.out.println(album);
        }

    }

    private boolean checkIfAlbumNameForUserDoesNotExistsInDatabase(int userId, String albumName) {
        String hql = "From Album a where a.name=" + "'" + albumName + "'" + " and a.userId=" + userId;

        Query<Album> query = session.createQuery(hql, Album.class);
        Album album = query.uniqueResult();
        //System.out.println(query);
        if (album == null) {
            return true;
        }
        return false;
    }

    private int getAlbumId(int userId, String albumName) {

        //try {
        String hql = "From Album a where a.name=" + "'" + albumName + "'" + " and a.userId=" + userId;

        Query<Album> query = session.createQuery(hql, Album.class);
        Album album = query.uniqueResult();

        if (album != null) {
            System.out.println("ID = " + album.getId());
            return album.getId();
        } else {
            return 0;
        }


        //} catch (HibernateException e) {
        //  e.printStackTrace();
        //}
        // return 0;

    }

    private void createNewAlbum(User user, String albumName) {
        Album album = new Album();
        album.setName(albumName);
        album.setUserId(user.getId());
        Transaction transaction = session.beginTransaction();
        session.save(album);
        transaction.commit();
    }


    // void change for boolean ? int / better to do -> int 1 for done, 2 for album, 3 user
    private int getProcessingStatusWhileAddingAlbum(User userLogged, String albumName) {
        int userId = userLogged.getId();
        int albumId = getAlbumId(userId, albumName);
        if (userId > 0) {
            //System.out.println("albumId" + albumId);
            if (albumId == 0) {
                //createNewAlbum(userId, albumName);
                return 1;
            } else {
                // System.out.println("[E2] User already has album: " + albumName);
                return 2;
            }
        } else {
            System.out.println("[E1] User " + userLogged.getName() + " does not exist");
            return 3;
        }
    }

    private void deleteAlbum() {
        Transaction transaction = session.beginTransaction();
        session.delete(session.get(Album.class, 13));
        transaction.commit();
    }

    private boolean isAlbumBelongToUser(User userLogged, String albumName) {
        //int userId = user.getId();
        //boolean isAlbumBelongToUser = false;
        String hql = "From Album a where a.name=" + "'" + albumName + "'" + "and a.userId=" + userLogged.getId();
        Query<Album> query = session.createQuery(hql, Album.class);
        Album album = query.uniqueResult();
        if (album != null) {
            // if (album.getUserId() == user.getId()) {
            return true;
            // }
        }
        return false;
    }

    private boolean isUserLikePhoto(User userLogged, String photoName) {

        String hql = "From User u where u.id= " + userLogged.getId();
        Query<User> query = session.createQuery(hql, User.class);
        User user1 = query.uniqueResult();

        for (Photo photo : user1.getPhotos()) {

            if (photo.getName().equals(photoName)) {
                return true;
            }
        }
        return false;
    }

    // delete album + pictures + likes
    private void deleteAlbum(User userLogged, String albumName) {

        String hql = "From Album a where a.name=" + "'" + albumName + "'" + " and a.userId=" + userLogged.getId();

        Query<Album> query = session.createQuery(hql, Album.class);
        Album album = query.uniqueResult();
        Transaction deleteTransaction = session.beginTransaction();

        for (Photo photo : album.getPhotos()) {
            session.delete(photo);
        }

        session.delete(session.get(Album.class, album.getId()));

        deleteTransaction.commit();
    }

    //---------------------------------------  Photo

    private boolean isPictureBelongToUser(User userLogged, String albumName, String photoName) {

        String hql1 = "From Album a  where a.name=" + "'" + albumName + "'" + "and a.userId=" + userLogged.getId();
        Query<Album> query1 = session.createQuery(hql1, Album.class);
        Album album = query1.uniqueResult();

        String hql2 = "From Photo p where p.name=" + "'" + photoName + "'" + "and p.albumId=" + album.getId();
        Query<Photo> query = session.createQuery(hql2, Photo.class);
        Photo photo = query.uniqueResult();

        if (album != null && photo != null) {
            // if (album.getUserId() == user.getId()) {
            return true;
            // }
        }
        return false;
    }

    private int getProcessingStatusWhileAddingPhoto(User userLogged, String albumName, String photoName) {

        String hql1 = "From Album a  where a.name=" + "'" + albumName + "'" + "and a.userId=" + userLogged.getId();
        Query<Album> query1 = session.createQuery(hql1, Album.class);
        Album album = query1.uniqueResult();

        String hql2 = "From Photo p where p.name=" + "'" + photoName + "'" + "and p.albumId=" + album.getId();
        Query<Photo> query = session.createQuery(hql2, Photo.class);
        Photo photo = query.uniqueResult();

        if (album != null) {
            if (photo == null) {
                System.out.println("photot = " + 1);
                return 1; // can add picture / picture does not exist
            } else {
                System.out.println("photot = " + 2);
                return 2; // picture with that name exists
            }
        } else {
            return 3; // album does not exist
        }

    }

    private void addPhoto(String photoName, String albumName, User userLogged) {
        String hql = "From Album a where a.name=" + "'" + albumName + "'" + "and a.userId=" + userLogged.getId();
        Query<Album> query = session.createQuery(hql, Album.class);
        Album album = query.uniqueResult();

        Photo photo = new Photo();
        photo.setName(photoName);
        photo.setAlbumId(album.getId());
        photo.setDate(getLocalDate());

        Transaction transaction = session.beginTransaction();
        session.save(photo);
        transaction.commit();
    }

    private void deletePhoto(String photoName, String albumName, User userLogged) {

        String hql1 = "From Album a  where a.name=" + "'" + albumName + "'" + "and a.userId=" + userLogged.getId();
        Query<Album> query1 = session.createQuery(hql1, Album.class);
        Album album = query1.uniqueResult();

        String hql2 = "From Photo p where p.name=" + "'" + photoName + "'" + "and p.albumId=" + album.getId();
        Query<Photo> query = session.createQuery(hql2, Photo.class);
        Photo photo = query.uniqueResult();

        if (album != null && photo != null) {
            Transaction deleteTransaction = session.beginTransaction();
            session.delete(photo);
            deleteTransaction.commit();
        } else {
            System.out.println("Album does not exist or photo does not exist");
        }
    }

    private void printPhoto(User userLogged, String albumName) {
        String hql = "From Album a where a.name=" + "'" + albumName + "'" + "and a.userId=" + userLogged.getId();
        Query<Album> query = session.createQuery(hql, Album.class);
        Album album = query.uniqueResult();

        for(Photo photo : album.getPhotos()) {
            System.out.println(photo);
            System.out.print(" , likes: "+countedPhotoLikes(photo));

        }

    }

    private void printPhoto(User userLogged, String albumName, String photoName) {}

    //---------------------------------------  Photo Likes

    private void addPhotoLlike1(User userLogged, String photoName) {
        int albumId = 13;
        String hql = "From Photo p where p.name=" + "'Z 1'" + " and p.albumId=" + albumId;
        Query<Photo> query = session.createQuery(hql, Photo.class);
        Photo photo = query.uniqueResult();


        /*int userId = 8;
        String hql2 = "From User a where a.id=" + user.getId();
        Query<User> query2 = session.createQuery(hql2, User.class);
        User user1 = query2.uniqueResult();*/

        // from user side
        userLogged.addPhoto(photo);

        // from photo side
        //photo.addUser(user);

        Transaction transaction = session.beginTransaction();

        transaction.commit();


    }

    // to do - when album name is wrong
    private int getProcessingStatusForPhotoLike(User userLogged, String albumName, String photoName) {
        String hql1 = "From Album a  where a.name=" + "'" + albumName + "'";
        Query<Album> query1 = session.createQuery(hql1, Album.class);
        Album album = query1.uniqueResult();

        if (album != null) {

            String hql3 = "From User a  where a.id=" + album.getUserId();
            Query<User> query3 = session.createQuery(hql3, User.class);
            User user1 = query3.uniqueResult();

            if (areWeFriends(userLogged, user1.getName()) || userLogged.equals(user1)) {

                String hql2 = "From Photo p where p.name=" + "'" + photoName + "'" + "and p.albumId=" + album.getId();
                Query<Photo> query2 = session.createQuery(hql2, Photo.class);
                Photo photo = query2.uniqueResult();

                if (photo != null) {

                    //Set<User> users = photo.getUsers();
                    if (photo.getUsers().contains(userLogged)) {
                        //System.out.println("like = " + 1);
                        return 1; //  like
                    } else {
                        // System.out.println("like = " + 4);
                        return 4; // no like
                    }


                } else {
                    // System.out.println("like = " + 2);
                    return 2; // photo does not exist
                }
            } else {
                // return 3; // album does not exist
                return 5; // users are not friends
            }

        } else {

            //return 5; // users are not friends
            return 3; // album does not exist
        }


    }

    private void addPhotoLike(User userLogged, String albumName, String photoName) {

        String hql1 = "From Album a  where a.name=" + "'" + albumName + "'";
        Query<Album> query1 = session.createQuery(hql1, Album.class);
        Album album = query1.uniqueResult();

        String hql2 = "From Photo p where p.name=" + "'" + photoName + "'" + "and p.albumId=" + album.getId();
        Query<Photo> query = session.createQuery(hql2, Photo.class);
        Photo photo = query.uniqueResult();
        photo.addUser(userLogged);

        Transaction transaction = session.beginTransaction();
        // from user side
        //user.addPhoto(photo);

        // from photo side
        session.save(photo);

        transaction.commit();


    }

    private void addPhotoLlike2(String photoName) {
        int albumId = 13;
        String hql = "From Photo p where p.name=" + "'Z 1'" + " and p.albumId=" + albumId;
        Query<Photo> query = session.createQuery(hql, Photo.class);
        Photo photo = query.uniqueResult();


        int userId = 4;
        String hql2 = "From User a where a.id=" + userId;
        Query<User> query2 = session.createQuery(hql2, User.class);
        User user = query2.uniqueResult();

        // from user side
        user.addPhoto(photo);

        // from photo side
        //photo.addUser(user);

        Transaction transaction = session.beginTransaction();

        transaction.commit();

    }

    private void addPhotoLlike3(String photoName) {
        int albumId = 10;
        String hql = "From Photo p where p.name=" + "'lodowisko'" + " and p.albumId=" + albumId;
        Query<Photo> query = session.createQuery(hql, Photo.class);
        Photo photo = query.uniqueResult();


        int userId = 5;
        String hql2 = "From User a where a.id=" + userId;
        Query<User> query2 = session.createQuery(hql2, User.class);
        User user = query2.uniqueResult();

        // from user side
        user.addPhoto(photo);

        // from photo side
        //photo.addUser(user);

        Transaction transaction = session.beginTransaction();

        transaction.commit();


    }

    private void addPhotoLlike4(String photoName) {
        int albumId = 10;
        String hql = "From Photo p where p.name=" + "'lodowisko'" + " and p.albumId=" + albumId;
        Query<Photo> query = session.createQuery(hql, Photo.class);
        Photo photo = query.uniqueResult();


        int userId = 8;
        String hql2 = "From User a where a.id=" + userId;
        Query<User> query2 = session.createQuery(hql2, User.class);
        User user = query2.uniqueResult();

        // from user side
        user.addPhoto(photo);

        // from photo side
        //photo.addUser(user);

        Transaction transaction = session.beginTransaction();

        transaction.commit();
    }

    private void deletePhotoLlike(User userLogged, String albumName, String photoName) {

        String hql1 = "From Album a  where a.name=" + "'" + albumName + "'";
        Query<Album> query1 = session.createQuery(hql1, Album.class);
        Album album = query1.uniqueResult();

        String hql2 = "From Photo p where p.name=" + "'" + photoName + "'" + "and p.albumId=" + album.getId();
        Query<Photo> query = session.createQuery(hql2, Photo.class);
        Photo photo = query.uniqueResult();
        //photo.removeUser(userLogged); // ??

        Transaction deleteTransaction = session.beginTransaction();
        photo.removeUser(userLogged);
        session.save(photo);

        deleteTransaction.commit();

        /* int photoId = 25;
        String hql = "From Photo p where p.id=" + photoId;
        Query<Photo> query = session.createQuery(hql, Photo.class);
        Photo photo = query.uniqueResult();


        int userId = 8;
        String hql2 = "From User u where u.id=" + userId;
        Query<User> query2 = session.createQuery(hql2, User.class);
        User user = query2.uniqueResult();
        Transaction deleteTransaction = session.beginTransaction();
        //session.delete(photo);
        photo.removeUser(user);
        deleteTransaction.commit();*/

    }

    private int countedPhotoLikes(Photo photo) {
        return photo.getUsers().size();
    }

    //---------------------------------------  Friends

    private boolean areWeFriends(User userLogged, String friendName) {

       /* String hql = "From User u where u.name=" + "'" + friendName + "'";
        Query<User> query = session.createQuery(hql, User.class);
        User friend = query.uniqueResult();*/

        User friend = getUserFromDatabase(friendName);

        boolean areWeFriends = false;

        for (User u : userLogged.getUsers()) {
            if (friend.equals(u)) {
                //System.out.println("userLogged ");
                areWeFriends = true;
            }
        }

        for (User u : friend.getUsers()) {
            if (userLogged.equals(u)) {
                //System.out.println("friend ");
                areWeFriends = true;
            }
        }

        if(userLogged.equals(friend)) {
            areWeFriends = true; // it is good to be your own friend, but not in this case xD
        }

        return areWeFriends;
    }

    private void addFriend(User user, String friendName) {

        String hql = "From User u where u.name=" + "'" + friendName + "'";
        Query<User> query = session.createQuery(hql, User.class);
        User friend = query.uniqueResult();

        Transaction insertTransaction = session.beginTransaction();
        user.addUser(friend);
        insertTransaction.commit();

    }

    private void deleteFriend(User userLogged, String friendName) {

        String hql = "From User u where u.name=" + "'" + friendName + "'";
        Query<User> query = session.createQuery(hql, User.class);
        User friend = query.uniqueResult();

        Transaction deleteTransaction = session.beginTransaction();
        System.out.println("0 = " + userLogged.getName());
        System.out.println("1 = " + friend.getName());

        for (User u : friend.getUsers()) {
            if (userLogged.equals(u)) {
                // System.out.println("3 = " + u.getName());
                friend.removeUser(userLogged);
                userLogged.removeUser(friend);

            }
        }




        for (User u : userLogged.getUsers()) {
            if (friend.equals(u)) {
               //println("2 = " + u.getName());
                // userLogged.removeUser(friend);
                 //session.save(userLogged);
                friend.removeUser(userLogged);
               userLogged.removeUser(friend);
                //session.save(friend);


            }
        }

        session.save(friend);
        session.save(userLogged);

      // session.save(friend);
       //session.save(userLogged);
        deleteTransaction.commit();

    }

    //---------------------------------------  Date

    private String getLocalDate() {
        return LocalDate.now().toString();
    }

}
