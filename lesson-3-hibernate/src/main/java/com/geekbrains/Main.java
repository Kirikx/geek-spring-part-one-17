package com.geekbrains;

import com.geekbrains.persistence.Order;
import com.geekbrains.persistence.Product;
import com.geekbrains.persistence.User;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


public class Main {
    private static EntityManager em;

    public static void main(String[] args) throws IOException {
        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        em = emFactory.createEntityManager();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Main.main");
        String cmd;
        Main.testCase();
        Main.printHelp();
        while (!(cmd = reader.readLine()).equals("exit")) {
            try {
                System.out.println("cmd = " + cmd);
                String[] cmds = cmd.split("[()]");
                String cmdName = cmds[0];
                switch (cmdName) {
                    case "user.find":
                        System.out.println("user.find(" + cmds[1] + ")");
                        System.out.println(em.find(User.class, Integer.valueOf(cmds[1])));
                        break;
                    case "user.findAll":
                        System.out.println("user.findAll()");
                        System.out.println(em.createQuery("from User", User.class).getResultList());
                        break;
                    case "user.delete":
                        System.out.println("user.delete(" + cmds[1] + ")");
                        em.getTransaction().begin();
                        em.remove(em.find(User.class, Integer.valueOf(cmds[1])));
                        em.getTransaction().commit();
                        break;
                    case "product.find":
                        System.out.println("product.find(" + cmds[1] + ")");
                        System.out.println(em.find(Product.class, Integer.valueOf(cmds[1])));
                        break;
                    case "product.findAll":
                        System.out.println("product.findAll()");
                        System.out.println(em.createQuery("from Product", Product.class).getResultList());
                        break;
                    case "product.delete":
                        System.out.println("product.delete(" + cmds[1] + ")");
                        em.getTransaction().begin();
                        em.remove(em.find(Product.class, Integer.valueOf(cmds[1])));
                        em.getTransaction().commit();
                        break;
                    case "order.find":
                        System.out.println("order.find(" + cmds[1] + ")");
                        System.out.println(em.find(Order.class, Integer.valueOf(cmds[1])));
                        break;
                    case "order.findAll":
                        System.out.println("order.findAll()");
                        System.out.println(em.createQuery("from Order", Order.class).getResultList());
                        break;
                    case "order.delete":
                        System.out.println("product.delete(" + cmds[1] + ")");
                        em.getTransaction().begin();
                        em.remove(em.find(Order.class, Integer.valueOf(cmds[1])));
                        em.getTransaction().commit();
                        break;
                    case "help":
                        Main.printHelp();
                        break;
                    default:
                        System.out.println("cmd not found");
                        Main.printHelp();
                        break;
                }
            } catch (Exception e) {
                System.out.println("ERROR");
                e.printStackTrace();
            }
        }
        em.close();
    }

    private static void printHelp() {
        System.out.println("########### LIST COMMANDS ###########");
        System.out.println("user.find(${id})");
        System.out.println("user.findAll()");
        System.out.println("user.delete(${id})");
        System.out.println("product.find(${id})");
        System.out.println("product.findAll()");
        System.out.println("product.delete(${id})");
        System.out.println("order.find(${id})");
        System.out.println("order.findAll()");
        System.out.println("order.delete(${id})");
        System.out.println("exit");
        System.out.println("help");
        System.out.println("Enter command ->");
    }

    private static void testCase() {
        em.getTransaction().begin();
        //################### User #############################
        System.out.println("USER_1");
        User user = em.find(User.class, 1);
        System.out.println(user);

        System.out.println("USERS");
        List<User> from_user = em.createQuery("from User", User.class).getResultList();
        System.out.println(from_user);
        System.out.println(" ");
        //################### Product #############################
        System.out.println("PRODUCT_1");
        Product product = em.find(Product.class, 1);
        System.out.println(product);

        System.out.println("PRODUCTS");
        List<Product> products = em.createQuery("from Product", Product.class).getResultList();
        System.out.println(products);
        System.out.println(" ");
        //################### Order #############################
        System.out.println("ORDER_1");
        Order order = em.find(Order.class, 1);
        System.out.println(order);

        System.out.println("ORDERS");
        List<Order> orderItems = em.createQuery("from Order", Order.class).getResultList();
        System.out.println(orderItems);

        em.getTransaction().commit();

        System.out.println(" ");
    }
}
