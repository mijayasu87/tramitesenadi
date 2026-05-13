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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
//    public static String REMOTE_HOST = "10.0.26.130";
//    public static String USERNAME = "root";
//    public static String PASSWORD = "temporal123";
    
    private static final int REMOTE_PORT = 22;
    private static final int SESSION_TIMEOUT = 10000;
    private static final int CHANNEL_TIMEOUT = 30000;

    public boolean doCopyFromInputStreamToRemote(InputStream is, String remotePath) {

        Session jschSession = null;

        try {

            JSch jsch = new JSch();
            jsch.setKnownHosts("/home/mjyanangomez/.ssh/known_hosts");

            jschSession = jsch.getSession(ParametrosBD.USERNAMEFTP, ParametrosBD.REMOTE_HOSTFTP, REMOTE_PORT);

            // authenticate using private key
            // jsch.addIdentity("/home/mkyong/.ssh/id_rsa");
            jschSession.setConfig("StrictHostKeyChecking", "no");

            // authenticate using password
            jschSession.setPassword(ParametrosBD.PASSWORDFTP);

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
            System.out.println("[DELETED] Archivo eliminado: " + remotePath);
            return true;
        } catch (JSchException | SftpException e) {
            System.err.println("[DELETED ERROR] Error al eliminar archivo a " + remotePath);
            e.printStackTrace();
            return false;
        } finally {
            if (wrapper != null) {
                FTPConnectionPool.getInstance().releaseConnection(wrapper);
            }
        }
    }

    public boolean renameFile(String originalPath, String newPath) {
        FTPConnectionPool.ChannelSftpWrapper wrapper = null;
        try {
            FTPConnectionPool pool = FTPConnectionPool.getInstance();
            wrapper = pool.getConnection();
            ChannelSftp channelSftp = wrapper.getChannelSftp();
            channelSftp.rename(originalPath, newPath);
            System.out.println("[RENAME] " + originalPath + " -> " + newPath);
            return true;
        } catch (JSchException | SftpException e) {
            System.err.println("[RENAME ERROR] Error al renombrar archivo " + originalPath + " a " + newPath);
            e.printStackTrace();
            return false;
        } finally {
            if (wrapper != null) {
                FTPConnectionPool.getInstance().releaseConnection(wrapper);
            }
        }
    }

    /**
     * Verifica si un directorio existe en el servidor remoto.
     */
    public boolean directoryExists(String remotePath) {
        FTPConnectionPool.ChannelSftpWrapper wrapper = null;
        try {
            FTPConnectionPool pool = FTPConnectionPool.getInstance();
            wrapper = pool.getConnection();
            ChannelSftp channelSftp = wrapper.getChannelSftp();
            channelSftp.stat(remotePath);
            return true;
        } catch (SftpException e) {
            return false;
        } catch (JSchException e) {
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

            jschSession = jsch.getSession(ParametrosBD.USERNAMEFTP, ParametrosBD.REMOTE_HOSTFTP, REMOTE_PORT);

            // authenticate using private key
            // jsch.addIdentity("/home/mkyong/.ssh/id_rsa");
            jschSession.setConfig("StrictHostKeyChecking", "no");

            // authenticate using password
            jschSession.setPassword(ParametrosBD.PASSWORDFTP);

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
            Session session = jsch.getSession(ParametrosBD.USERNAMEFTP, ParametrosBD.REMOTE_HOSTFTP, REMOTE_PORT);
            session.setPassword(ParametrosBD.PASSWORDFTP);
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
                    //System.out.println("----------> name: "+name);
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

        return filtrarPreviews(lista);
    }

    // Oculta archivos de vista previa: los _ALC_PREV_<id> y, si existen, los
    // pdf_scope_renewalfrm_<id> / pdf_scope_breederfrm_<id> emparejados por ID.
    private List<String> filtrarPreviews(List<String> lista) {
        Pattern alcPrev = Pattern.compile("_ALC_PREV_(\\d+)");
        Set<String> idsPreview = new HashSet<>();
        for (String name : lista) {
            Matcher m = alcPrev.matcher(name);
            if (m.find()) {
                idsPreview.add(m.group(1));
            }
        }
        if (idsPreview.isEmpty()) {
            return lista;
        }
        Pattern scope = Pattern.compile("pdf_scope_(?:renewalfrm|breederfrm)_(\\d+)\\.");
        List<String> filtrada = new ArrayList<>(lista.size());
        for (String name : lista) {
            if (alcPrev.matcher(name).find()) {
                continue;
            }
            Matcher m = scope.matcher(name);
            if (m.find() && idsPreview.contains(m.group(1))) {
                continue;
            }
            filtrada.add(name);
        }
        return filtrada;
    }

}
