package com.katkam;

import com.katkam.entity.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.DriverManager;
import java.util.Properties;

/**
 * Created by Developer on 1/7/17.
 */
public class GrizzlyHelper {
    private static final SessionFactory concreteSessionFactory;
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
//            DriverManager.registerDriver(new com.mysql.jdbc.Driver());

            Properties prop = new Properties();
            prop.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/parttrack?serverTimezone=Asia/Dubai");
            prop.setProperty("hibernate.connection.username", "root");
            prop.setProperty("hibernate.connection.password", "pepsi");
            prop.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");

            prop.setProperty("hibernate.hbm2ddl.auto", "update");
//            prop.setProperty("hibernate.hbm2ddl.auto", "create");

            concreteSessionFactory = new org.hibernate.cfg.Configuration()
                .addProperties(prop)
                .addPackage("com.katkam.entity")
                .addAnnotatedClass(Manufacturer.class)
                .addAnnotatedClass(Uom.class)
                .addAnnotatedClass(Part.class)
                .addAnnotatedClass(Store.class)
                .addAnnotatedClass(Stock.class)
                .addAnnotatedClass(Xact.class)
                .addAnnotatedClass(RequisitionHeader.class)
                .addAnnotatedClass(RequisitionLine.class)
                .addAnnotatedClass(PickticketHeader.class)
                .addAnnotatedClass(PickticketLine.class)
                .buildSessionFactory()
                ;
        }
        catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static Session getSession() throws HibernateException {
        return concreteSessionFactory.openSession();
    }

}



/*
import javax.persistence.*;

@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue
    private int id;

    @Column(name="plateno")
    private String plate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }
}
*/


/*
import org.hibernate.Session;
import java.util.List;

public class Grizzly {
    public static void main(String[] args) {
        Session sess = GrizzlyHelper.getSession();
        sess.beginTransaction();
        List<Vehicle> lstVeh = sess.createCriteria(Vehicle.class).list();
        System.out.println(lstVeh.size());
        sess.close();
    }
}
*/


/*
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>5.2.5.Final</version>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>6.0.5</version>
    </dependency>
*/