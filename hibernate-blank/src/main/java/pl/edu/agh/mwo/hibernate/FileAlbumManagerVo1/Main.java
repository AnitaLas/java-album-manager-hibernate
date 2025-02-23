package pl.edu.agh.mwo.hibernate.FileAlbumManagerVo1;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;

public class Main {

    Session session;

    public static void main(String[] args) {
        Main main = new Main();

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
                            userLogged = main.getUserFromDatabase(userName);
                            if (userLogged != null) {
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

                        do {
                            isLogin = false;
                            System.out.println("Create account. user name: ");
                            String userName = br.readLine();
                            userLogged = main.getUserFromDatabase(userName);
                            if (userLogged != null) {
                                userLogged = main.getUserFromDatabase(userName);
                                System.out.println("Welcome " + userLogged.getName());
                                System.out.println("Account exists. You have been automatically logged in.");
                                isUserAccountExis = true;
                                isLogin = true;
                            } else {
                                main.addUser(userName);
                                userLogged = main.getUserFromDatabase(userName);
                                isUserAccountExis = true;
                                isLogin = true;
                                System.out.println("Welcome " + userLogged.getName() + "! User account created.");
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
                                    "21-deleteFriend, " +
                                    "23-showMyFriends, \n" +
                                    "999-logout, " +
                                    "666-removeYourselfFromDatabase, ");

                    input = br.readLine();

                    // addAlbum
                    if (input.equals("1")) {
                        System.out.println("ADD album name: ");
                        boolean isAlbumExist;

                        do {
                            isAlbumExist = false;
                            String albumName = br.readLine();
                            int result = main.getProcessingStatusWhileAddingAlbum(userLogged, albumName);

                            if (result == 1) {
                                System.out.println("Album added successfully.");
                                main.createNewAlbum(userLogged, albumName);
                                isAlbumExist = true;
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
                        } else {
                            System.out.println("Album does not exist or does not belong to the user " + userLogged.getName() + ". Album cannot be deleted.");
                        }
                    }
                    //showMyAlbums
                    else if (input.equals("3")) {
                        main.printUserAlbums(userLogged);
                    }
                    // showUserAlbums
                    else if (input.equals("5")) {
                        System.out.println("Enter the username of the user whose albums you want to see:");
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
                        System.out.println("Enter the name of album the photo belongs to:");
                        String albumName = br.readLine();

                        if (main.isAlbumBelongToUser(userLogged, albumName)) {
                            main.printPhoto(userLogged, albumName);
                        } else {
                            System.out.println("Album not found.");
                        }
                    }
                    // addPhoto
                    else if (input.equals("9")) {
                        System.out.println("Enter the name of album where you want to add photo:");
                        String albumName = br.readLine();

                        if (main.isAlbumBelongToUser(userLogged, albumName)) {
                            System.out.println("ADD enter photo name: ");
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
                                    System.out.println("Would you like to try again to enter the name of the photo? 1 - yes, 2 - no");
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
                            main.deleteUser(userLogged);
                            System.out.println("Goodbye " + userLogged.getName() + "!");
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
                    // showMyFriends
                    else if (input.equals("23")) {
                        main.printMyFriends(userLogged);
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

    // --------- database
    private User getUserFromDatabase(String userName) {
        String hql = "From User u where u.name=" + "'" + userName + "'";
        Query<User> query = session.createQuery(hql, User.class);
        return query.uniqueResult();
    }

    private User getUserFromDatabase(int userId) {
        String hql3 = "From User a  where a.id=" + userId;
        Query<User> query3 = session.createQuery(hql3, User.class);
        return query3.uniqueResult();
    }

    private List<User> getUsersFromDatabase() {
        Query<User> query = session.createQuery("from User", User.class);
        return query.list();
    }

    private Album getAlbumFromDatabase(String albumName) {
        String hql1 = "From Album a  where a.name=" + "'" + albumName + "'";
        Query<Album> query = session.createQuery(hql1, Album.class);
        return query.uniqueResult();
    }

    private Album getAlbumFromDatabase(String albumName, int userId) {
        String hql1 = "From Album a  where a.name=" + "'" + albumName + "'" + "and a.userId=" + userId;
        Query<Album> query = session.createQuery(hql1, Album.class);
        return query.uniqueResult();
    }

    private List<Album> getAlbumsFromDatabase(int userId) {
        String hql1 = "From Album a  where a.userId=" + userId;
        Query<Album> query = session.createQuery(hql1, Album.class);
        return query.list();
    }

    private Photo getPhotoFromDatabase(String photoName, int albumId) {
        String hql2 = "From Photo p where p.name=" + "'" + photoName + "'" + "and p.albumId=" + albumId;
        Query<Photo> query = session.createQuery(hql2, Photo.class);
        return query.uniqueResult();
    }

    private List<Photo> getPhotosFromDatabase(int albumId) {
        String hql2 = "From Photo p where p.albumId=" + albumId;
        Query<Photo> query = session.createQuery(hql2, Photo.class);
        return query.list();
    }


    //---------------------------------------  Users
    private void printUsers() {
        List<User> users = getUsersFromDatabase();
        System.out.println("### Users");

        for (User user : users) {
            System.out.println(user);
        }
    }

    private boolean isUserExistsInDatabase(String userName) {
        User user = getUserFromDatabase(userName);
        if (user != null)
            return true;
        return false;
    }

    private void addUser(String userName) {
        User user = new User();
        user.setName(userName);

        Transaction transaction = session.beginTransaction();
        session.save(user); // gdzie user to instancja nowego usera
        transaction.commit();
    }

    private void deleteUser(User userLogged) {
        Transaction deleteTransaction = session.beginTransaction();
        // System.out.println("album nr " + userLogged.getAlbums().size());

        List<Album> albums = getAlbumsFromDatabase(userLogged.getId());
        for (Album album : albums) {

            List<Photo> photos = getPhotosFromDatabase(album.getId());
            for (Photo photo : photos) {
                // from database, else from memory?
                if(!photo.getUsers().isEmpty()){
                    System.out.println("> 1 photo side");
                    System.out.println("> ?????????????????");

                    photo.getUsers().clear();
                    session.delete(photo);
                }
                //
                else{
                    System.out.println("> 2 users side");
                    List<User> users = getUsersFromDatabase();
                    for(User user : users){
                        System.out.println("> 2a " + user);
                        if(!user.getPhotos().isEmpty()){
                            for (Photo p : user.getPhotos()) {
                                System.out.println("> 2b " + p);
                                if(p.equals(photo)){
                                    System.out.println("> 2c " + user);
                                    user.removePhoto(photo);
                                    session.save(user);
                                    System.out.println("> 2c -- ");
                                }
                            }
                        }
                    }
                    session.delete(photo);
                }
            }
            session.delete(album);
        }
        // delete column 2 -> InvitationAcceptedByUserId
        List<User> users = getUsersFromDatabase();

        for (User user : users) {
            for (User userFriends : user.getUsers()) {
                if (userFriends.equals(userLogged)) {
                    user.removeUser(userLogged);
                    session.save(userFriends);
                }
            }
        }
        // delete column 1 -> InvitationSentByUserId
        for (User friend : userLogged.getUsers()) {
            System.out.println("2 = " + friend.getName());
            friend.removeUser(userLogged);
            session.save(friend); // added - to remove
        }
        session.save(userLogged);

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
        System.out.println("### Albums, owner: " + user.getName());

        for (Album album : user.getAlbums()) {
            System.out.println(album);
        }
    }

    private void createNewAlbum(User user, String albumName) {
        Album album = new Album();
        album.setName(albumName);
        album.setUserId(user.getId());
        Transaction transaction = session.beginTransaction();
        session.save(album);
        transaction.commit();
    }

    private int getProcessingStatusWhileAddingAlbum(User userLogged, String albumName) {
        if (userLogged.getId() > 0) {
            if (getAlbumFromDatabase(albumName, userLogged.getId()) == null) {
                return 1; // user does not have album with that name
            } else {
                return 2; // user already has album with that name
            }
        } else {
            System.out.println("[E1] User " + userLogged.getName() + " does not exist");
            return 3; // user does not exist
        }
    }

    private boolean isAlbumBelongToUser(User userLogged, String albumName) {
        if (getAlbumFromDatabase(albumName, userLogged.getId()) != null)
            return true;
        return false;
    }

    private boolean isUserLikePhoto(User userLogged, String photoName) {
        User user1 = getUserFromDatabase(userLogged.getId());
        if (user1 != null) {
            for (Photo photo : user1.getPhotos()) {
                if (photo.getName().equals(photoName))
                    return true;
            }
        } else {
            System.out.println("[E2] User " + userLogged.getName() + " does not exist");
        }
        return false;
    }

    private void deleteAlbum(User userLogged, String albumName) {
        Album album = getAlbumFromDatabase(albumName, userLogged.getId());

        if (album != null) {
            Transaction deleteTransaction = session.beginTransaction();
            List<Photo> photos = getPhotosFromDatabase(album.getId());

            for (Photo photo : photos) {

                // from database, else from memory?
                if(!photo.getUsers().isEmpty()){
                    System.out.println("> 1 photo side");
                    System.out.println("> ?????????????????");

                    photo.getUsers().clear();
                    session.delete(photo);
                }
                //
                else{
                    System.out.println("> 2 users side");
                    List<User> users = getUsersFromDatabase();
                    for(User user : users){
                        System.out.println("> 2a " + user);
                        if(!user.getPhotos().isEmpty()){
                            for (Photo p : user.getPhotos()) {
                                System.out.println("> 2b " + p);
                                if(p.equals(photo)){
                                    System.out.println("> 2c " + user);
                                    user.removePhoto(photo);
                                    session.save(user);
                                    System.out.println("> 2c -- ");
                                }
                            }
                        }
                    }
                    session.delete(photo);
                }

                album.removePhoto(photo);
                session.save(album);
            }

            session.delete(album);
            deleteTransaction.commit();
        } else {
            System.out.println("Album " + albumName + " does not exist");
        }

    }

    //---------------------------------------  Photo
    private boolean isPictureBelongToUser(User userLogged, String albumName, String photoName) {
        Album album = getAlbumFromDatabase(albumName, userLogged.getId());
        Photo photo = getPhotoFromDatabase(photoName, album.getId());

        if (album != null && photo != null) {
            return true;
        }

        if (album != null && photo != null) {
            return true;
        }

        return false;
    }

    private int getProcessingStatusWhileAddingPhoto(User userLogged, String albumName, String photoName) {
        Album album = getAlbumFromDatabase(albumName, userLogged.getId());
        Photo photo = getPhotoFromDatabase(photoName, album.getId());

        if (album != null) {
            if (photo == null) {
                return 1; // can add picture / picture does not exist
            } else {
                return 2; // picture with that name exists
            }
        } else {
            return 3; // album does not exist
        }
    }

    private void addPhoto(String photoName, String albumName, User userLogged) {
        Album album = getAlbumFromDatabase(albumName, userLogged.getId());
        if (album != null) {
            Photo photo = new Photo();
            photo.setName(photoName);
            photo.setAlbumId(album.getId());
            photo.setDate(getLocalDate());

            Transaction transaction = session.beginTransaction();
            session.save(photo);
            transaction.commit();
        } else {
            System.out.println("[Album " + albumName + " does not exist");
        }
    }

    private void deletePhoto(String photoName, String albumName, User userLogged) {
        Album album = getAlbumFromDatabase(albumName, userLogged.getId());
        Photo photo = getPhotoFromDatabase(photoName, album.getId());

        if (album != null && photo != null) {
            Transaction deleteTransaction = session.beginTransaction();
            //deleteRelationPhotoLikesBothWays(photo);

            // if database, else from memory ?
            if(!photo.getUsers().isEmpty()){


               /* Set<User> users = photo.getUsers();
                for(User user : users){
                    System.out.println("> 1a  " + user);
                   // if(user.equals(userLogged)){
                        //System.out.println("> 1b  " + user);
                        photo.removeUser(user);
                    session.save(photo);
                    System.out.println("> 1b  " + user);
                }*/

                // keeps in longer version
                /*for(Iterator<User> iterator = users.iterator(); iterator.hasNext();){
                    User user = iterator.next();
                    photo.removeUser(user);
                    session.save(photo);
                }*/

                //session.save(photo);
               //session.delete(photo); // delete photoLike to

                photo.getUsers().clear();
                session.delete(photo);
            }
            // ---
            else{
                List<User> users = getUsersFromDatabase();
                for(User user : users){
                    if(!user.getPhotos().isEmpty()){
                        for (Photo p : user.getPhotos()) {
                            if(p.equals(photo)){
                                user.removePhoto(photo);
                                session.save(user);
                            }
                        }
                    }
                }
               session.delete(photo);
            }

            //session.delete(photo); // like added as photo is remove data
            deleteTransaction.commit();
        } else {
            System.out.println("Album does not exist or photo does not exist");
        }
    }

    private void printPhoto(User userLogged, String albumName) {
        Album album = getAlbumFromDatabase(albumName, userLogged.getId());

        for (Photo photo : album.getPhotos()) {
            System.out.println(photo);
            System.out.print(" , likes: " + countedPhotoLikes(photo));
        }
    }

    private void printPhoto(User userLogged, String albumName, String photoName) {
        System.out.println("printPhoto() -> real photo");
    }

    //---------------------------------------  Photo Likes
    private int getProcessingStatusForPhotoLike(User userLogged, String albumName, String photoName) {
        Album album = getAlbumFromDatabase(albumName);

        if (album != null) {
            User user1 = getUserFromDatabase(album.getUserId());

            if (areWeFriends(userLogged, user1.getName()) || userLogged.equals(user1)) {
                Photo photo = getPhotoFromDatabase(photoName, album.getId());

                if (photo != null) {
                    if (photo.getUsers().contains(userLogged)) {
                        return 1; //  like
                    } else if (userLogged.getPhotos().contains(photo)) {
                        return 1; //  like
                    } else {
                        return 4; // no like
                    }
                } else {
                    return 2; // photo does not exist
                }
            } else {
                return 5; // users are not friends
            }
        } else {
            return 3; // album does not exist
        }
    }

    private void insertPhotoLikesBothWays(User userLogged, Photo photo) {
        if (!photo.getUsers().contains(userLogged)) {
            photo.addUser(userLogged);
            session.save(photo);
        } else if (!userLogged.getPhotos().contains(photo)) {
            userLogged.addPhoto(photo);
            session.save(userLogged);
        } else {
            System.out.println("insertPhotoLikesBothWays() -> error");
        }
    }

    private void addPhotoLike(User userLogged, String albumName, String photoName) {
        Album album = getAlbumFromDatabase(albumName);

        if (album != null) {
            Photo photo = getPhotoFromDatabase(photoName, album.getId());

            if (photo != null) {
                // from photo side
                //photo.addUser(userLogged);
                // from user side
                userLogged.addPhoto(photo);

                Transaction transaction = session.beginTransaction();
                //insertPhotoLikesBothWays(userLogged, photo);
                //session.save(photo);
                session.save(userLogged);
                transaction.commit();
            } else {
                System.out.println("[Photo " + photoName + " does not exist");
            }
        } else {
            System.out.println("[Album " + albumName + " does not exist");
        }
    }


    private void deleteRelationPhotoLikesBothWays(Photo photo) {

        // get code form one of the method to delete
        /*System.out.println(">>> 1 ");

        if (!photo.getUsers().isEmpty()) {
            // photo side - do BBB nie wchodzi
            for (User user : photo.getUsers()) {
                System.out.println(">>> AAAA ");
                System.out.println(">>> photo " + photo);
                System.out.println(">>> user " + user);
                user.removePhoto(photo); // nic nie usuwa nic
                //photo.removeUser(user); // error
                session.save(user);
            }
        }
        else{
            for(User user : photo.getUsers()){
                System.out.println(">>> BBBB ");
                System.out.println(">>> photo " + photo);
                System.out.println(">>> user " + user);
                user.removePhoto(photo);
                session.save(photo);
            }
        }*/

    }

    private void deletePhotoLlike(User userLogged, String albumName, String photoName) {
        Album album = getAlbumFromDatabase(albumName);

        if (album != null) {
            Photo photo = getPhotoFromDatabase(photoName, album.getId());

            if (photo != null) {
                Transaction deleteTransaction = session.beginTransaction();
                //deleteRelationPhotoLikesBothWays(userLogged, photo);
                // add by user
                userLogged.removePhoto(photo);
                photo.removeUser(userLogged);

                session.save(photo);
                deleteTransaction.commit();
            }
        } else {
            System.out.println("[Album " + albumName + " does not exist");
        }
    }

    private int countedPhotoLikes(Photo photo) {
        return photo.getUsers().size();
    }

    //---------------------------------------  Friends
    private boolean areWeFriends(User userLogged, String friendName) {
        User friend = getUserFromDatabase(friendName);

        if (friend != null) {
            boolean areWeFriends = false;

            for (User u : userLogged.getUsers()) {
                if (friend.equals(u))
                    areWeFriends = true;
            }

            for (User u : friend.getUsers()) {
                if (userLogged.equals(u))
                    areWeFriends = true;
            }

            if (userLogged.equals(friend))
                areWeFriends = true; // it is good to be your own friend, but not in this case xD

            return areWeFriends;
        } else {
            System.out.println("[Friend " + friendName + " does not exist in database.");
            return false;
        }
    }

    private void addFriend(User user, String friendName) {
        User friend = getUserFromDatabase(friendName);

        if (friend != null) {
            Transaction insertTransaction = session.beginTransaction();
            user.addUser(friend);
            //friend.addUser(user); // double data, table -> Friends
            session.save(user); // added - remove
            insertTransaction.commit();
        } else {
            System.out.println("[Friend " + friendName + " does not exist");
        }
    }

    private void deleteFriend(User userLogged, String friendName) {
        User friend = getUserFromDatabase(friendName);
        if (friend != null) {
            Transaction deleteTransaction = session.beginTransaction();

            for (User u : friend.getUsers()) {
                if (userLogged.equals(u)) {
                    friend.removeUser(userLogged);
                    session.save(friend);
                }
            }

            for (User u : userLogged.getUsers()) {
                if (friend.equals(u)) {
                    userLogged.removeUser(friend);
                    session.save(userLogged);
                }
            }
            deleteTransaction.commit();
        } else {
            System.out.println("[Friend " + friendName + " does not exist");
        }
    }

    private void printMyFriends(User userLogged) {
        List<User> users = getUsersFromDatabase();

        System.out.println("### Friends ");
        int count = 0;
        // column 2 -> InvitationAcceptedByUserId
        for (User user : users) {
            for (User userFriends : user.getUsers()) {
                if (userFriends.equals(userLogged)) {
                    System.out.println(user);
                    count += 1;
                }
            }
        }
        //  column 1 -> InvitationSentByUserId
        for (User friend : userLogged.getUsers()) {
            System.out.println(friend);
            count += 1;
        }

        if (count == 0)
            System.out.println("No friends.");
    }

    //---------------------------------------  Date
    private String getLocalDate() {
        return LocalDate.now().toString();
    }

}
