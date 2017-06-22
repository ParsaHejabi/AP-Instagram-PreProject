package Client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by parsahejabi on 6/21/17.
 */
public class Server {

    static final int SERVER_PORT = 8080;
    static ServerSocket serverSocket;
    static Socket clientSocket;
    static ArrayList<Thread> clients;
    static ArrayList<Profile> profiles;
    static File profilesDir = new File("Profiles/");

    public static void main(String[] args) throws IOException {
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            profiles = new ArrayList<>();
            clients = new ArrayList<>();
            initializeProfiles();

            while (true){
                clientSocket = serverSocket.accept();
                Thread clientsThread = new Thread(new ClientHandler(clientSocket));
                clients.add(clientsThread);
                clientsThread.start();
                System.out.println("someone connected!");
                removeClientThread();
                System.out.println(clients.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            serverSocket.close();
        }
    }

    public static void removeClientThread(){
        ArrayList<Thread> removable = new ArrayList<>();
        for (Thread th:clients){
            if (th.getThreadGroup() == null){
                removable.add(th);
            }
        }
        System.out.println("this threads are null: " + removable);
        clients.removeAll(removable);
    }

    public static void initializeProfiles() throws IOException, ClassNotFoundException {
        File[] serializedProfiles = profilesDir.listFiles();
        if (serializedProfiles!=null){
            for (File f:serializedProfiles){
                profiles.add(deserialize(f));
            }
        }
    }
    public static Profile deserialize(File profile) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(profile);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Profile p = (Profile) objectInputStream.readObject();
        objectInputStream.close();
        fileInputStream.close();
        return p;
    }
    public static void serialize(Profile profile) throws IOException {
        File f = new File(profilesDir,profile.username);
        System.out.println("Is file " + f.getName() + " created? " + f.createNewFile());
        FileOutputStream fileOutputStream = new FileOutputStream(f);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(profile);
        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.close();
    }
}