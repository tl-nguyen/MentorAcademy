package com.tlnguyen.mentormatetest.data;

import com.tlnguyen.mentormatetest.models.Idea;
import com.tlnguyen.mentormatetest.models.User;

import java.util.ArrayList;

/**
 * Created by TL on 12/15/2014.
 */
public class DbManager {
    private static DbManager instance;

    private static int currentId;

    private User currentUser;

    private ArrayList<User> users;

    private ArrayList<Idea> ideas;

    private DbManager() {
        currentUser = null;
        users = new ArrayList<User>();
        ideas = new ArrayList<Idea>();
        currentId = 0;
    }

    public static DbManager getInstance() {
        if (instance == null) {
            instance = new DbManager();
        }
        return instance;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public int getCurrentId() {
        return ++currentId;
    }

    public ArrayList<Idea> getIdeas() {
        return ideas;
    }

    public void setIdeas(ArrayList<Idea> ideas) {
        this.ideas = ideas;
    }
}
