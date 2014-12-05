package com.tlnguyen.homework_catalog.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.tlnguyen.homework_catalog.R;
import com.tlnguyen.homework_catalog.models.Book;

import java.sql.SQLException;

/**
 * Created by TL on 12/5/2014.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "bookCatalog.db";
    private static final int DATABASE_VERSION = 4;

    private Dao<Book, Integer> dao = null;
    private RuntimeExceptionDao<Book, Integer> runtimeDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Book.class);
            seed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Book.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        super.close();
        dao = null;
        runtimeDao = null;
    }

    public Dao<Book, Integer> getDao() throws SQLException {
        if (dao == null) {
            dao = getDao(Book.class);
        }

        return dao;
    }

    public RuntimeExceptionDao<Book, Integer> getRuntimeDao() {
        if (runtimeDao == null) {
            runtimeDao = getRuntimeExceptionDao(Book.class);
        }

        return runtimeDao;
    }

    private void seed() {
        Book book1 = new Book("Expert Oracle and Java Security",
                "Expert Oracle and Java Security: Programming Secure Oracle Database Applications with Java " +
                        "provides resources that every Java and Oracle database application programmer needs " +
                        "to ensure that they have guarded the security of the data and identities entrusted " +
                        "to them. You'll learn to consider potential vulnerabilities, and to apply best " +
                        "practices in secure Java and PL/SQL coding. Author David Coffin shows how to develop " +
                        "code to encrypt data in transit and at rest, to accomplish single sign-on with Oracle " +
                        "proxy connections, to generate and distribute two-factor authentication tokens from the " +
                        "Oracle server using pagers, cell phones (SMS), and e-mail, and to securely store and " +
                        "distribute Oracle application passwords.",
                "978-1-4302-3831-7",
                "http://it-ebooks.info/images/ebooks/6/expert_oracle_and_java_security.jpg");

        Book book2 = new Book("The Well-Grounded Java Developer",
                "The Well-Grounded Java Developer starts with thorough coverage of Java 7 features like " +
                        "try-with-resources and NIO.2. You'll then explore a cross-section of emerging " +
                        "JVM-based languages, including Groovy, Scala, and Clojure. You will find clear " +
                        "examples that are practical and that help you dig into dozens of valuable development " +
                        "techniques showcasing modern approaches to the dev process, concurrency, performance, " +
                        "and much more.",
                "978-1-6172-9006-0",
                "http://it-ebooks.info/images/ebooks/5/the_well-grounded_java_developer.jpg");

        Book book3 = new Book("Java Cookbook, 2nd Edition",
                "You have a choice: you can wade your way through lengthy Java tutorials and figure things out by " +
                        "trial and error, or you can pick up Java Cookbook, 2nd Edition and get to the heart of what " +
                        "you need to know when you need to know it.",
                "978-0-59600-701-0",
                "http://it-ebooks.info/images/ebooks/3/java_cookbook_2nd_edition.jpg");

        Book book4 = new Book("Learning Android",
                "Want to build apps for Android devices? This book is the perfect way to master the fundamentals. Written " +
                        "by an expert who's taught this mobile platform to hundreds of developers in large organizations, " +
                        "this gentle introduction shows experienced object-oriented programmers how to use Android’s " +
                        "basic building blocks to create user interfaces, store data, connect to the network, and more.",
                "978-1-4493-9050-1",
                "http://it-ebooks.info/images/ebooks/3/learning_android.jpg");

        Book book5 = new Book("Spring for Android Starter",
                "Learn something new in an Instant! A short, fast, focused guide delivering immediate results. Learn what " +
                        "Spring for Android adds to the Android developer toolkit. Learn how to debug your Android " +
                        "communication layer observing HTTP requests and responses. Use OAuth to authenticate your users " +
                        "and your app against popular service providers (Google, Facebook, Twitter, and so on).",
                "978-1-78216-190-5",
                "http://it-ebooks.info/images/ebooks/14/instant_spring_for_android_starter.jpg");

        Book book6 = new Book("Clean Code",
                "Even bad code can function. But if code isn't clean, it can bring a development organization to its " +
                        "knees. Every year, countless hours and significant resources are lost because of poorly written " +
                        "code. But it doesn't have to be that way.\n" +
                        "\n" +
                        "What kind of work will you be doing? You'll be reading code - lots of code. And you will be challenged " +
                        "to think about what's right about that code, and what's wrong with it. More importantly, you will be " +
                        "challenged to reassess your professional values and your commitment to your craft.",
                "978-0-13-235088-4",
                "http://it-ebooks.info/images/ebooks/4/clean_code.jpg");

        Book book7 = new Book("The Art of Readable Code",
                "As programmers, we've all seen source code that's so ugly and buggy it makes our brain ache. Over the past " +
                        "five years, authors Dustin Boswell and Trevor Foucher have analyzed hundreds of examples of bad code " +
                        "(much of it their own) to determine why they're bad and how they could be improved. Their conclusion? " +
                        "You need to write code that minimizes the time it would take someone else to understand it-even if that" +
                        " someone else is you.\n" +
                        "\n" +
                        "This book focuses on basic principles and practical techniques you can apply every time you write code. " +
                        "Using easy-to-digest code examples from different languages, each chapter dives into a different aspect of " +
                        "coding, and demonstrates how you can make your code easy to understand.",
                "978-0-596-80229-5",
                "http://it-ebooks.info/images/ebooks/3/the_art_of_readable_code.jpg");

        Book book8 = new Book("Ruby Best Practices",
                "Ruby Best Practices is for programmers who want to use Ruby the way Rubyists do. Written by the developer of the Ruby " +
                        "project Prawn (prawn.majesticseacreature.com), this concise book explains how to design beautiful APIs and " +
                        "domain-specific languages, work with functional programming ideas and techniques that can simplify your code " +
                        "and make you more productive, write code that's readable and expressive, and much more. It's the perfect companion " +
                        "to The Ruby Programming Language.",
                "978-0-596-52300-8",
                "http://it-ebooks.info/images/ebooks/3/ruby_best_practices.jpg");

        Book book9 = new Book("C# 3.0 Design Patterns",
                "Want to speed up the development of your .NET applications? Tackle common programming problems with C# design patterns. " +
                        "This guide explains what design patterns are and why they're used, with tables and guidelines to help you choose " +
                        "one pattern over another, and plenty of case studies to illustrate how each pattern is used in practice. C# 3.0 " +
                        "features are introduced by example and summarized for easy reference.",
                "978-0-596-52773-0",
                "http://it-ebooks.info/images/ebooks/3/c_3.0_design_patterns.jpg");

        Book book10 = new Book("Pro JavaScript Design Patterns",
                "As a web developer, you'll already know that JavaScript is a powerful language, allowing you to add an impressive array " +
                        "of dynamic functionality to otherwise static web sites. But there is more power waiting to be unlocked - JavaScript " +
                        "is capable of full object - oriented capabilities, and by applying object-oriented principles, best practices, and " +
                        "design patterns to your code, you can make it more powerful, more efficient, and easier to work with alone or as part " +
                        "of a team.",
                "978-1-59059-908-2",
                "http://it-ebooks.info/images/ebooks/6/pro_javascript_design_patterns.jpg");

        Book book11 = new Book("Java 8 in Action",
                "Java 8 in Action is a clearly written guide to the new features of Java 8. The book covers lambdas, streams, and functional-style " +
                        "programming. With Java 8's functional features you can now write more concise code in less time, and also automatically " +
                        "benefit from multicore architectures. It's time to dig in!",
                "978-1-59059-908-2",
                "http://it-ebooks.info/images/ebooks/5/java_8_in_action.jpg");

        this.getRuntimeDao().create(book1);
        this.getRuntimeDao().create(book2);
        this.getRuntimeDao().create(book3);
        this.getRuntimeDao().create(book4);
        this.getRuntimeDao().create(book5);
        this.getRuntimeDao().create(book6);
        this.getRuntimeDao().create(book7);
        this.getRuntimeDao().create(book8);
        this.getRuntimeDao().create(book9);
        this.getRuntimeDao().create(book10);
        this.getRuntimeDao().create(book11);
    }

}
