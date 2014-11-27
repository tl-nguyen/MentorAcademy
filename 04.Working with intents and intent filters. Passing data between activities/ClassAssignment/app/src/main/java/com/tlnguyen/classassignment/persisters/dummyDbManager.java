package com.tlnguyen.classassignment.persisters;

import com.tlnguyen.classassignment.models.User;

import java.util.ArrayList;

/**
 * Created by TL on 11/27/2014.
 */
public class DummyDbManager {

    private static DummyDbManager instance;

    private static int currentId;

    private User currentUser;

    private ArrayList<User> users;

    private DummyDbManager() {
        currentUser = null;
        users = new ArrayList<User>();
        currentId = 0;
    }

    public static DummyDbManager getInstance() {
        if (instance == null) {
            instance = new DummyDbManager();
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
}
