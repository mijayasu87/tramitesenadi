/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.util;

/**
 *
 * @author micharesp
 */
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.InputStream;

public class Codekru {

    Session session;

    String remoteHostPort = "10.0.20.130"; // Your remote host ip
    String username = "mjyanangomez"; // Your username
    String password = "S31tNW4$"; // Your password
    int port = 22; // The port (Default port is 22 )

    public Codekru(int servidor) {
        if (servidor == 130) {
            connect(username, password, remoteHostPort, port, null);
        } else if (servidor == 131) {
            connect("root", "Uio2016gdt&Iepi", "10.0.20.131", 22, null);
        }
    }
    
    public Codekru(){}
    
    /**
     * ejecuta el comando enviado por el parametro 'comando'
     *
     * @param comando
     * @return true si el comando se ejecutó correctamente
     */
    public boolean exeComando(String comando) {
        try {
            JSch jsch = new JSch();
            //Session session = jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
            Session session = jsch.getSession(username, remoteHostPort, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(10000);
            Channel channel = session.openChannel("exec");

            ((ChannelExec) channel).setCommand(comando);//"cp " + rutar + " " + rutac + " && echo 'movido'"
            channel.setInputStream(null);

            ((ChannelExec) channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();

            channel.connect();

            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                }
                if (channel.isClosed()) {
                    System.out.println("exit-status exe command: " + channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                }
            }
            channel.disconnect();
            session.disconnect();
            return true;
        } catch (Exception e) {
            System.out.println("Error al ejecutar comando: " + e);
            return false;
        }
    }
    
    

    public void connect(String username, String password, String host, int port, String FILE_KEY_NAME) {
        try {

            JSch jsch = new JSch();

            // if there is a key, then connect using that key
            // otherwise, we will connect using a password
            if (FILE_KEY_NAME != null && !FILE_KEY_NAME.equals("")) {
                jsch.addIdentity(FILE_KEY_NAME);
                session = jsch.getSession(username, host, port);
            } else {
                session = jsch.getSession(username, host, port);
                session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
                session.setPassword(password);
            }

            session.setConfig("StrictHostKeyChecking", "no");
            session.setTimeout(15000);
            session.connect();

            System.out.println("Connected to: " + host);

        } catch (JSchException e) {
            e.printStackTrace();
            session.disconnect();
        }
    }

    public void copyAInputStreamToRemoteMachine(InputStream localFilePath, String remoteFilePath) {

        try {
            Channel sftp = session.openChannel("sftp");

            sftp.connect(15000);

            ChannelSftp channelSftp = (ChannelSftp) sftp;

            channelSftp.put(localFilePath, remoteFilePath);

            System.out.println("File has been transferred...");

            channelSftp.exit();
            session.disconnect();

        } catch (JSchException | SftpException e) {

            e.printStackTrace();
            session.disconnect();
        }
    }

    public void copyAFileToRemoteMachine(String localFilePath, String remoteFilePath) {

        try {
            Channel sftp = session.openChannel("sftp");

            sftp.connect(15000);

            ChannelSftp channelSftp = (ChannelSftp) sftp;

            channelSftp.put(localFilePath, remoteFilePath);

            System.out.println("File has been transferred...");

            channelSftp.exit();
            session.disconnect();

        } catch (JSchException | SftpException e) {

            e.printStackTrace();
            session.disconnect();
        }

    }

}
