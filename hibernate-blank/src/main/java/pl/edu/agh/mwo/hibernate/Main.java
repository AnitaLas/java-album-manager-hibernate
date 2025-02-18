package pl.edu.agh.mwo.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

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
        User user = null;
        try {
            do {

                System.out.println("****** START    ");
                boolean isUserAccountExis = false;
                boolean isLogin;
                do {
                    System.out.println("select option: 1 - log in, 2 - create account");
                    input = br.readLine();
                    if (input.equals("1")) {

                        //boolean isLogin;
                        do {
                            isLogin = false;
                            System.out.println("log in. user name: ");
                            String userName = br.readLine();
                            isUserAccountExis = main.checkIfUserExistsInDatabase(userName);
                            if (isUserAccountExis) {
                                user = main.getUserFromDatabase(userName);
                                System.out.println("Welcome " + user.getName());
                                isLogin = true;
                            } else {
                                System.out.println("user not found. Do you want to try login again or create account?");
                                System.out.println("selec option: 1 - create account, 2 - try again");
                                String decision1 = br.readLine();
                                if (decision1.equals("1")) {
                                    main.addUser(userName);
                                    user = main.getUserFromDatabase(userName);
                                    isUserAccountExis = true;
                                    isLogin = true;
                                    System.out.println("Welcome " + user.getName() + ". User account created.");
                                } else if (decision1.equals("2")) {
                                    isUserAccountExis = false;
                                } else {
                                    System.out.println("[E2] input is not correct");
                                }
                            }

                            //System.out.println("islogin: " + isLogin);
                        } while (!isLogin);


                    } else if (input.equals("2")) {

                        //boolean isLogin;
                        do {
                            isLogin = false;
                            System.out.println("Create account. user name: ");
                            String userName = br.readLine();
                            isUserAccountExis = main.checkIfUserExistsInDatabase(userName);
                            if (isUserAccountExis) {
                                user = main.getUserFromDatabase(userName);
                                System.out.println("Welcome " + user.getName());
                                System.out.println("Account exist. You have been automaticale login.");
                                isLogin = true;
                            } else {
                                main.addUser(userName);
                                user = main.getUserFromDatabase(userName);
                                isUserAccountExis = true;
                                isLogin = true;
                                System.out.println("Welcome " + user.getName() + ". User account created.");
                            }

                        } while (!isLogin);


                    } else {
                        System.out.println("[E3] input is not correct");
                    }


                } while (isUserAccountExis == false);


                do {
                    System.out.println();
                    System.out.println("NEXT DO WHILE     ");

                    System.out.println("select option: " +
                            "1 - addAlbum," +
                            "2 - removeAlbum," +
                            "3 - showMyAlbums, " +
                            "4 - showAllUsersAlbums, " +
                            "5 - showUserAlbums" +
                            "6 - showMyPictures, " +
                            "7 - showUsersPicures" +
                            "8 - showPictureInAlbum" +
                            "9 - addPicture" +
                            "10 - removePicture" +
                            "11 - thumbsUp" +
                            "11 - thombsDown" +
                            "99 - logout");
                    
                    input = br.readLine();

                    // addAlbum
                    if (input.equals("1")) {
                        System.out.println("ADD album name: ");
                        String albumName = br.readLine();

                        boolean isAlbumExist;
                        do{
                            isAlbumExist = false;
                           int result = main.addAlbum3(user.getName(),albumName);
                           if (result == 1) {
                               System.out.println("Album added successfully.");
                               isAlbumExist = true;
                           }else{
                               System.out.println("Album already exists.");
                               System.out.println("Do you want to try again? 1 - yes, 2 - no");
                               String decision2 = br.readLine();
                               if (decision2.equals("1")) {
                                   isAlbumExist = false;
                               }
                               else if (decision2.equals("2")) {
                                   isAlbumExist = true;
                               }
                               else{
                                   System.out.println("[E33] input is not correct");
                               }
                           }
                        }while(isAlbumExist == false);


                    }
                    // removeAlbum
                    else if (input.equals("2")) {
                        System.out.println("REMOVE album name: ");
                        String albumName = br.readLine();

                      if(main.isAlbumBelongToUser(user, albumName)){
                          main.deleteAlbum2(user, albumName);
                          System.out.println("Album removed successfully. All albums data were deleted.");
                      }
                      else{
                          System.out.println("Album does not belong to user "+ user.getName() + ". Album cannot be deleted.");
                      }


                    }
                    //showMyAlbums
                    else if (input.equals("3")) {
                        main.printAlbums();
                    }



                } while (input.equals(input));

                //input = br.readLine();

                /*if(line.equals("u")) {
                    System.out.println("user name:");
                    line = br.readLine();
                    main.addUser(line);
                }


                int result = 3;
                if(line.equals("a")) {
                    System.out.println("album name:");
                    String line1 = br.readLine();
                    System.out.println("user name:");
                    String line2 = br.readLine();
                    result = main.addAlbum3(line2, line1);
                    if(result == 1) {
                        System.out.println("Album added successfully");
                    }
                    else if(result == 2) {
                        System.out.println("Do you want to add new album for user ? select option: newAlbumName, exit");
                        String line3 = br.readLine();
                        if(line3.equals("yes")) {
                            System.out.println("album name:");
                            String line4 = br.readLine();
                            result = main.addAlbum3(line2, line1);
                        }
                    }
                    else if(result == 3) {
                        System.out.println("Do you want to add this user to database with this album? select option: yes, no");
                        String line3 = br.readLine();
                        if(line3.equals("yes")) {
                            main.addUser(line2);
                            main.addAlbum3(line2, line1);
                            System.out.println("User and album added successfully");
                        }
                    }
                }*/


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

    private boolean checkIfUserExistsInDatabase(String userName) {
        String hql = "From User u where u.name=" + "'" + userName + "'";

        Query<User> query = session.createQuery(hql, User.class);
        User user = query.uniqueResult();
        //System.out.println(query);
        if (user != null) {
            return true;
        }
        return false;
    }

    private int getUserId(String userName) {

        try {
            String hql = "From User u where u.name=" + "'" + userName + "'";
            Query<User> query = session.createQuery(hql, User.class);
            User user = query.uniqueResult();

            if (user != null) {
                return user.getId();
            }


        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return 0;

    }

    private void addUser(String userName) {

        if (checkIfUserExistsInDatabase(userName)) {
            System.out.println("User " + userName + " already exists");
        } else {
            User user = new User();
            // user.setName("John");
            user.setName(userName);

            Transaction transaction = session.beginTransaction();
            session.save(user); // gdzie user to instancja nowego usera
            transaction.commit();
            //System.out.println("User " + userName + " added");
        }
    }

    private User getUserFromDatabase(String userName) {
        String hql = "From User u where u.name=" + "'" + userName + "'";
        Query<User> query = session.createQuery(hql, User.class);
        //User user = query.uniqueResult();
        return query.uniqueResult();
    }

    private void deleteUser() {
        Transaction transaction = session.beginTransaction();
        session.delete(session.get(User.class, 8));
        transaction.commit();
    }

    // delete user + albums + photos + likes
    private void deleteUser2() {

        int idToDelete = 8;

        String hql = "From User a where a.id=" + idToDelete;

        Query<User> query = session.createQuery(hql, User.class);
        User user = query.uniqueResult();
        Transaction deleteTransaction = session.beginTransaction();

        for (Album album : user.getAlbums()) {

            for (Photo photo : album.getPhotos()) {
                //photo.removeUser(user);
                //user.removePhoto(photo);
                session.delete(photo);
            }
            session.delete(album);
        }

        session.delete(user);


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

    private void printUserAlbums(User uesr){

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

        try {
            String hql = "From Album a where a.name=" + "'" + albumName + "'" + " and a.userId=" + userId;

            Query<Album> query = session.createQuery(hql, Album.class);
            Album album = query.uniqueResult();

            if (album != null) {
                System.out.println("ID = " + album.getId());
                return album.getId();
            } else {
                return 0;
            }


        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return 0;

    }

    private void createNewAlbum(int userId, String albumName) {
        Album album = new Album();
        album.setName(albumName);
        album.setUserId(userId);
        Transaction transaction = session.beginTransaction();
        session.save(album);
        transaction.commit();
    }

    private void addAlbum2(String userName, String albumName) {

        int userId = getUserId(userName);

        if (userId > 0) {

            if (checkIfAlbumNameForUserDoesNotExistsInDatabase(userId, albumName)) {
                Album album = new Album();
                album.setName(albumName);
                album.setUserId(userId);

                Transaction transaction = session.beginTransaction();
                session.save(album);
                transaction.commit();

            } else {
                System.out.println("Album name already exists");
            }

        } else {
            System.out.println("User " + userName + " does not exist");
        }

    }


    // void change for boolean ? int / better to do -> int 1 for done, 2 for album, 3 user
    private int addAlbum3(String userName, String albumName) {

        int userId = getUserId(userName);
        int albumId = getAlbumId(userId, albumName);

        if (userId > 0) {

            if (albumId == 0) {
                createNewAlbum(userId, albumName);
                return 1;
            } else {
                System.out.println("[E2] User already has album: " + albumName);
                return 2;
            }

        } else {
            System.out.println("[E1] User " + userName + " does not exist");
            return 3;
        }

    }

    private void deleteAlbum() {
        Transaction transaction = session.beginTransaction();
        session.delete(session.get(Album.class, 13));
        transaction.commit();
    }

    //  XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    private boolean isAlbumBelongToUser(User user, String albumName) {
        //int userId = user.getId();
        //boolean isAlbumBelongToUser = false;
        String hql = "From Album a where a.name=" + "'" + albumName + "'" +  "and a.userId=" + user.getId();
        Query<Album> query = session.createQuery(hql, Album.class);
        Album album = query.uniqueResult();
        if (album != null) {
           // if (album.getUserId() == user.getId()) {
                return true;
           // }
        }
        return false;
    }

    // delete album + pictures + likes
    private void deleteAlbum2(User user, String albumName) {

        String hql = "From Album a where a.name=" + "'" + albumName+ "'" + " and a.userId=" + user.getId();

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

    private void addPhoto(String photoName, String albumName) {
        int albumId = Integer.parseInt(albumName);
        Photo photo = new Photo();
        photo.setName(photoName);
        photo.setAlbumId(albumId);
        Transaction transaction = session.beginTransaction();
        session.save(photo);
        transaction.commit();
    }

    private void deletePhoto() {
        int idToDelete = 26;
        String hql = "From Photo a where a.id=" + idToDelete;
        Query<Photo> query = session.createQuery(hql, Photo.class);
        Photo photo = query.uniqueResult();
        Transaction deleteTransaction = session.beginTransaction();
        session.delete(photo);
        deleteTransaction.commit();
    }

    //---------------------------------------  Photo Likes

    private void addPhotoLlike(String photoName) {
        int albumId = 13;
        String hql = "From Photo p where p.name=" + "'Z 1'" + " and p.albumId=" + albumId;
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

    private void deletePhotoLlike() {
        int photoId = 25;
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
        deleteTransaction.commit();

    }
}
