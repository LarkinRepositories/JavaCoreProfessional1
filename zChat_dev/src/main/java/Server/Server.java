package Server;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;

public class Server {

    private Vector<ClientHandler> clients;

    public Server() throws SQLException, ClassNotFoundException {
        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;

        try {
            AuthService.connect();
            String test = AuthService.getNickNameByLoginAndPass("login1", "password4");
            System.out.println(test);
            server = new ServerSocket(8189);
            System.out.println("Server is running!");
            while (true) {
                socket = server.accept();
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
            AuthService.disconnect();
        }
    }

    public boolean isNicknameBusy(String nickname) {
        for (ClientHandler client : clients) {
            if (client.getNickname().equalsIgnoreCase(nickname)) return true;
        }
        return false;
    }
    public boolean isBlacklisted(ClientHandler client, String nickname) throws SQLException {
        return AuthService.getBlacklistedUsers(client.getUserID()).contains(nickname);
    }

    public boolean isBlacklisted(String nickname) throws SQLException {
        for (ClientHandler client:clients) {
            if (AuthService.getBlacklistedUsers(client.getUserID()).contains(nickname)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void broadcastMessage(String message) {
        for (ClientHandler client: clients) {
            client.sendMessage(message);
        }
    }

    public synchronized void whisper(ClientHandler donor, String acceptor, String message) throws SQLException {
        if (!isBlacklisted(donor, acceptor)) {
            for (ClientHandler client : clients) {
                if (client.getNickname().equalsIgnoreCase(acceptor)) {
                    client.sendMessage(String.format("%s whispers: %s", donor.getNickname(), message));
                    donor.sendMessage(String.format("You whispered to %s: %s", acceptor, message));
                    return;
                }
            }
            donor.sendMessage(String.format("There're no user with %s nickname in chat", acceptor));
        } else {
            donor.sendMessage(String.format("%s is blacklisted!", acceptor));
        }
    }


    public void subscribe(ClientHandler client) {
       clients.add(client);
    }

    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }
}
