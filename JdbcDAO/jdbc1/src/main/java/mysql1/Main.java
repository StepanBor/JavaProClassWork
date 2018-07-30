package mysql1;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/mydb";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "password";

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);

        ConnectionFactory factory = new ConnectionFactory(
                DB_CONNECTION, DB_USER, DB_PASSWORD
        );

        Connection conn = factory.getConnection();
        try {
            ClientDAO dao = new ClientDAOImpl(conn);
            dao.init();

            ClientDAO2 dao2 = new ClientDAO2(conn, "clients");
            //...

            while (true) {
                System.out.println("1: add client");
                System.out.println("2: view clients");
                System.out.print("-> ");

                String s = sc.nextLine();
                switch (s) {
                    case "1":
                        System.out.print("Enter client name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter client age: ");
                        String sAge = sc.nextLine();
                        int age = Integer.parseInt(sAge);

                        dao.addClient(name, age);

                        break;
                    case "2":
                        List<Client> list = dao.getAll();
                        for (Client client : list) {
                            System.out.println(client);
                        }
                        break;
                    default:
                        return;
                }
            }
        } finally {
            sc.close();
            if (conn != null) conn.close();
        }
    }
}
