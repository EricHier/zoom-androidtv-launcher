package tech.erichier.zoomlauncher;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkOpener {

    NetworkOpener(final Context context) {

        try (ServerSocket serverSocket = new ServerSocket(7362)) {

            System.out.println("Server is listening on port 7362");

            while (true) {
                Socket socket = serverSocket.accept();

                System.out.println("New client connected");

                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String line = reader.readLine();

                System.out.println(line);

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(line));
                context.startActivity(browserIntent);
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }

    }

}
