/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.util;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author micharesp
 */
public class FTPFiles {

    //producción
//    private static final String REMOTE_HOST = "10.0.20.130";
//    private static final String USERNAME = "root";
//    private static final String PASSWORD = "B7GJuaxu3Y";

    //prueba
    public static String REMOTE_HOST = "10.0.26.130";
    public static String USERNAME = "root";
    public static String PASSWORD = "temporal123";
    
    private static final int REMOTE_PORT = 22;
    private static final int SESSION_TIMEOUT = 10000;
    private static final int CHANNEL_TIMEOUT = 30000;

    public boolean doCopyFromInputStreamToRemote(InputStream is, String remotePath) {

        Session jschSession = null;

        try {

            JSch jsch = new JSch();
            jsch.setKnownHosts("/home/mjyanangomez/.ssh/known_hosts");

            jschSession = jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);

            // authenticate using private key
            // jsch.addIdentity("/home/mkyong/.ssh/id_rsa");
            jschSession.setConfig("StrictHostKeyChecking", "no");

            // authenticate using password
            jschSession.setPassword(PASSWORD);

            // 10 seconds session timeout
            jschSession.connect(SESSION_TIMEOUT);

//            jschSession.setTimeout(30000);
            Channel sftp = jschSession.openChannel("sftp");

            // 5 seconds timeout
            sftp.connect(CHANNEL_TIMEOUT);

            ChannelSftp channelSftp = (ChannelSftp) sftp;

            // transfer file from local to remote server
            channelSftp.put(is, remotePath);

            // download file from remote server to local
            // channelSftp.get(remoteFile, localFile);
            channelSftp.exit();
            return true;
        } catch (JSchException | SftpException e) {
            System.err.println("Error aquí: " + e);
            e.printStackTrace();

        } finally {
            if (jschSession != null) {
                jschSession.disconnect();
            }
        }
        System.out.println("Done");
        return false;
    }

    /**
     * Sube un archivo desde InputStream a la ruta remota usando FTPConnectionPool
     * OPTIMIZADO: Reutiliza conexiones SFTP para mejor rendimiento
     * 
     * @param is InputStream del archivo a subir
     * @param remotePath Ruta completa del archivo en el servidor remoto (ej: /ruta/archivo.pdf)
     * @return true si se subió correctamente, false en caso de error
     */
    public boolean uploadFileOptimized(InputStream is, String remotePath) {
        FTPConnectionPool.ChannelSftpWrapper wrapper = null;

        try {
            // Obtener conexión del pool (reutilizable)
            FTPConnectionPool pool = FTPConnectionPool.getInstance();
            wrapper = pool.getConnection();
            ChannelSftp channelSftp = wrapper.getChannelSftp();

            // Subir archivo
            channelSftp.put(is, remotePath);
            
            System.out.println("[UPLOAD] Archivo subido exitosamente: " + remotePath);
            return true;

        } catch (JSchException | SftpException e) {
            System.err.println("[UPLOAD ERROR] Error al subir archivo a " + remotePath);
            e.printStackTrace();
            return false;

        } finally {
            // Retornar conexión al pool para reutilización
            if (wrapper != null) {
                FTPConnectionPool.getInstance().releaseConnection(wrapper);
            }
        }
    }

    /**
     * Elimina un archivo del servidor remoto
     */
    public boolean deleteFile(String remotePath) {
        FTPConnectionPool.ChannelSftpWrapper wrapper = null;
        try {
            FTPConnectionPool pool = FTPConnectionPool.getInstance();
            wrapper = pool.getConnection();
            ChannelSftp channelSftp = wrapper.getChannelSftp();
            channelSftp.rm(remotePath);
            System.out.println("[DELETE] Archivo eliminado: " + remotePath);
            return true;
        } catch (JSchException | SftpException e) {
            System.err.println("[DELETE ERROR] Error al eliminar archivo a " + remotePath);
            e.printStackTrace();
            return false;
        } finally {
            if (wrapper != null) {
                FTPConnectionPool.getInstance().releaseConnection(wrapper);
            }
        }
    }

    /**
     * Crea un directorio en el servidor remoto si no existe
     * 
     * @param remotePath Ruta del directorio a crear
     * @return true si se creó o ya existe, false en caso de error
     */
    public boolean createDirectoryIfNotExists(String remotePath) {
        FTPConnectionPool.ChannelSftpWrapper wrapper = null;

        try {
            FTPConnectionPool pool = FTPConnectionPool.getInstance();
            wrapper = pool.getConnection();
            ChannelSftp channelSftp = wrapper.getChannelSftp();

            try {
                channelSftp.stat(remotePath);
                // El directorio existe
                return true;
            } catch (SftpException e) {
                if (e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) {
                    // No existe, crear
                    channelSftp.mkdir(remotePath);
                    System.out.println("[MKDIR] Directorio creado: " + remotePath);
                    return true;
                } else {
                    throw e;
                }
            }

        } catch (JSchException | SftpException e) {
            System.err.println("[MKDIR ERROR] Error al crear directorio: " + remotePath);
            e.printStackTrace();
            return false;

        } finally {
            if (wrapper != null) {
                FTPConnectionPool.getInstance().releaseConnection(wrapper);
            }
        }
    }

    public boolean doCopyFromLocalToRemote(String localFile, String remotePath) {

        Session jschSession = null;

        try {

            JSch jsch = new JSch();
            jsch.setKnownHosts("/home/micharesp/.ssh/known_hosts");

            jschSession = jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);

            // authenticate using private key
            // jsch.addIdentity("/home/mkyong/.ssh/id_rsa");
            jschSession.setConfig("StrictHostKeyChecking", "no");

            // authenticate using password
            jschSession.setPassword(PASSWORD);

            // 10 seconds session timeout
            jschSession.connect(SESSION_TIMEOUT);

//            jschSession.setTimeout(30000);
            Channel sftp = jschSession.openChannel("sftp");

            // 5 seconds timeout
            sftp.connect(CHANNEL_TIMEOUT);

            ChannelSftp channelSftp = (ChannelSftp) sftp;

            // transfer file from local to remote server
            channelSftp.put(localFile, remotePath);

            // download file from remote server to local
            // channelSftp.get(remoteFile, localFile);
            channelSftp.exit();
            return true;
        } catch (JSchException | SftpException e) {
            System.err.println("Error aquí: " + e);
            e.printStackTrace();

        } finally {
            if (jschSession != null) {
                jschSession.disconnect();
            }
        }
        System.out.println("Done");
        return false;
    }

    /**
     * ejecuta el comando enviado por el parametro 'comando'
     *
     * @param comando
     * @return true si el comando se ejecutó correctamente
     */
    public boolean exeComando(String comando) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
            session.setPassword(PASSWORD);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(SESSION_TIMEOUT);
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
                    System.out.println("exit-status: " + channel.getExitStatus());
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
            System.out.println(e);
            return false;
        }
    }

    public List<String> listarDirectorio(String ruta) {
        
//        System.out.println("ruta en sftp: "+ruta);
        
        List<String> lista = new ArrayList<>();
        FTPConnectionPool.ChannelSftpWrapper wrapper = null;

        try {
            // Obtener conexión del pool (reutilizable)
            FTPConnectionPool pool = FTPConnectionPool.getInstance();
            wrapper = pool.getConnection();
            ChannelSftp channelSftp = wrapper.getChannelSftp();
            
            channelSftp.cd(ruta);

            @SuppressWarnings("unchecked")
            Vector<ChannelSftp.LsEntry> fileList = channelSftp.ls(".");

            for (ChannelSftp.LsEntry entry : fileList) {

                String name = entry.getFilename();

                // Ignorar . y ..
                if (name.equals(".") || name.equals("..")) {
                    continue;
                }

                if (entry.getAttrs().isDir()) {
                    if (name.equals("anexos")) {
                        List<String> anexos = listarDirectorio(ruta + "/anexos");
                        for (String anexo : anexos) {
                            lista.add("anexos/" + anexo);
                        }
                    }

                } else {
//                    System.out.println("---> name: "+name);
                    lista.add(name);
                }
            }

        } catch (JSchException | SftpException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            // Retornar conexión al pool para reutilización
            if (wrapper != null) {
                FTPConnectionPool.getInstance().releaseConnection(wrapper);
            }
        }

        return lista;
    }

//    public List<String> listarDirectorio(String ruta) {
//        String SFTPWORKINGDIR = ruta;
//        Session session = null;
//        Channel channel = null;
//        ChannelSftp channelSftp = null;
//
//        try {
//            JSch jsch = new JSch();
//            session = jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
//            session.setPassword(PASSWORD);
//            java.util.Properties config = new java.util.Properties();
//            config.put("StrictHostKeyChecking", "no");
//            session.setConfig(config);
//            session.connect();
//            channel = session.openChannel("sftp");
//            channel.connect();
//            channelSftp = (ChannelSftp) channel;
//            channelSftp.cd(SFTPWORKINGDIR);

////            System.out.println(SFTPWORKINGDIR);
//            Vector filelist = channelSftp.ls(SFTPWORKINGDIR);
//            List<String> lista = new ArrayList<>();
//            for (int i = 0; i < filelist.size(); i++) {
////                System.out.println("entrooooooo "+(i+1));
//                String[] aux = filelist.get(i).toString().trim().split(" ");
//                List<String> archivo = new ArrayList<>();
//                for (int j = 0; j < aux.length; j++) {
//                    if (!aux[j].trim().isEmpty()) {
//                        archivo.add(aux[j].trim());
//                        System.out.println("---> "+aux[j].trim());
//                    }
//                }
//
//                int limite = 9;
//                String name = "";
//                if (archivo.size() > limite) {
//                    int espacios = archivo.size() - limite;
//                    for (int j = 0; j < espacios + 1; j++) {
//                        name += archivo.get(j + limite - 1) + " ";
//                    }
//                } else {
//                    name = archivo.get(limite - 1);
//                }
//
//                if (name.trim().charAt(0) != '.') {
//                    name = name.trim();
//                    if (name.equals("anexos")) {
//                        List<String> anexos = listarDirectorio(ruta + "/anexos");
//                        for (int j = 0; j < anexos.size(); j++) {
//                            lista.add("anexos/" + anexos.get(j));
//                        }
//                    } else {
//                        lista.add(name);
//                    }
//
////                    System.out.println("---------------> " + name.trim());
//                }
//
//            }
//            channelSftp.exit();
//            channelSftp.disconnect();
//            session.disconnect();
//            return lista;
//        } catch (JSchException | SftpException ex) {
//            return new ArrayList<>();
//        }
//    }

}
