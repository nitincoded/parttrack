package com.katkam;

import com.katkam.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Developer on 1/7/17.
 */
@Component
public class GrizzlyHelper {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    Session instance = null;

    public SessionFactory getConcreteSessionFactory() throws Exception {
        SessionFactory concreteSessionFactory;

        File f;

        f = new File("db.properties"); //try current working directory
        if (!f.exists()) f = new File(this.getClass().getResource("/").getPath() + "/db.properties"); //try root of the classpath

        String dbDriver = null; //"com.mysql.jdbc.Driver";
        String jdbcUrl = null; //"jdbc:mysql://localhost:3306/parttrack?serverTimezone=Asia/Dubai&user=root&password=pepsi";
        String dbDialect = "org.hibernate.dialect.MySQLDialect";

        if (f.exists() && !f.isDirectory()) {
            InputStream is = new FileInputStream(f);
            Properties dbProp = new Properties();

            try {
                dbProp.load(is);
            }
            finally {
                is.close();
            }

            if (dbProp.containsKey("dbdriver")) {
                dbDriver = dbProp.getProperty("dbdriver");
                log.trace(String.format("DB Driver is: %s", dbDriver));
            }

            if (dbProp.containsKey("jdbcurl")) {
                jdbcUrl = dbProp.getProperty("jdbcurl");
                log.trace(String.format("JDBC URL is: %s", jdbcUrl));
            }

            if (dbProp.containsKey("dbdialect")) {
                dbDialect = dbProp.getProperty("dbdialect");
                log.trace(String.format("DB dialect is: %s", dbDialect));
            } else {
                log.trace(String.format("Reverting to default DB dialect: %s", dbDialect));
            }
        } else {
            log.trace(f.getAbsolutePath() + " doesn't exist");
        }

        if (dbDriver != null) Class.forName(dbDriver);
//            DriverManager.registerDriver(new com.mysql.jdbc.Driver());


        if (jdbcUrl == null) {
            throw new Exception("JDBC URL not set");
        }


        Properties prop = new Properties();
        prop.setProperty("hibernate.connection.url", jdbcUrl);
//        prop.setProperty("hibernate.connection.username", "root");
//        prop.setProperty("hibernate.connection.password", "pepsi");
        prop.setProperty("dialect", dbDialect);

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
                .addAnnotatedClass(PurchaseOrderHeader.class)
                .addAnnotatedClass(PurchaseOrderLine.class)
                .addAnnotatedClass(Supplier.class)
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Customer.class)
                .buildSessionFactory()
        ;

        return concreteSessionFactory;
    }

    public Session getSession() {
        try {
            if (instance == null) {
                instance = getConcreteSessionFactory().openSession();
            }

            return instance;
        }
        catch (Exception ex) {
            return null;
        }
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