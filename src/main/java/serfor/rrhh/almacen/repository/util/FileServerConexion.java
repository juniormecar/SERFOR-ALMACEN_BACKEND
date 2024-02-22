package serfor.rrhh.almacen.repository.util;

import jcifs.smb.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Repository
public class FileServerConexion {

    private static final Logger log = LogManager.getLogger(FileServerConexion.class);

    @Value("${smb.file.server.username}")
    private String fileServerUserName;

    @Value("${smb.file.server.password}")
    private String fileServerPassword;

    @Value("${smb.file.server.host}")
    private String fileServerHost;

    @Value("${smb.file.server.path}")
    private String fileServerPath;

    private static final String SEPARADOR_ARCHIVO = ".";
    private static final String FORMATO_FECHA_HORA = "_dd-MM-yyyy_HHmmss";
    private static final String STRING_VACIO = "";

    public String uploadFile(MultipartFile multipartFile, String path) {
        log.info("FileServer - uploadFile : \n" + multipartFile.getOriginalFilename());
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String nombreNuevo = getFileName(multipartFile.getOriginalFilename());
            NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(fileServerHost, fileServerUserName,
                    fileServerPassword);
            SmbFile smbfile = new SmbFile(fileServerPath+path, nombreNuevo, auth);
            inputStream = new BufferedInputStream(multipartFile.getInputStream());
            outputStream = new BufferedOutputStream(new SmbFileOutputStream(smbfile));
            FileCopyUtils.copy(inputStream, outputStream);
            log.info("FileServerConexion - insertarArchivo \n" + "Proceso realizado correctamente");
            return nombreNuevo;
        } catch (SmbException e) {
            log.error("FileServerConexion - insertarArchivo \n" + "Ocurrió un error de conexion en: " + e.getMessage());
        } catch (MalformedURLException e) {
            log.error("FileServerConexion - insertarArchivo \n" + "Ocurrió un error de ruta del archivo en: " + e.getMessage());
        } catch (UnknownHostException e) {
            log.error("FileServerConexion - insertarArchivo \n" + "Ocurrió un error al conectarse al servidor en: "
                    + e.getMessage());
        } catch (IOException e) {
            log.error(
                    "FileServerConexion - insertarArchivo \n" + "Ocurrió un error al transferir archivo en: " + e.getMessage());
        } finally {
            closeStreanm(inputStream, outputStream);
        }
        return STRING_VACIO;
    }

    private String getFileName(String fullFileName) {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMATO_FECHA_HORA);
        Date date = new Date();
        if (Objects.nonNull(fullFileName)) {
            String newFileName = FilenameUtils.getBaseName(fullFileName)
                    .concat(formatter.format(date))
                    .concat(SEPARADOR_ARCHIVO)
                    .concat(FilenameUtils.getExtension(fullFileName));
            return newFileName;
        }
        return STRING_VACIO;
    }

    private void closeStreanm(InputStream in, OutputStream out) {
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("uploadFileDBSqlite - closeStreanm \n" + "Ocurrió un error: "+ e.getMessage());
        }
    }

    public byte[] loadFileAsResource(String fileName, String path) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bytes = null;
        try {
            NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(fileServerHost, fileServerUserName,
                    fileServerPassword);
            SmbFile smbfile = new SmbFile(path, fileName, auth);
            inputStream = new BufferedInputStream(new SmbFileInputStream(smbfile));
            outputStream = new BufferedOutputStream(byteArrayOutputStream);
            bytes = IOUtils.toByteArray(inputStream);
            return bytes;
        } catch (SmbException e) {
            log.error("uploadFileDBSqlite - loadFileAsResource \n" + "Ocurrió un error de conexion en: "+ e.getMessage());
        } catch (MalformedURLException e) {
            log.error("uploadFileDBSqlite - loadFileAsResource \n" + "Ocurrió un error de ruta del archivo en: ",
                    e.getMessage());
        } catch (UnknownHostException e) {
            log.error("uploadFileDBSqlite - loadFileAsResource \n" + "Ocurrió un error al conectarse al servidor en: ",
                    e.getMessage());
        } catch (IOException e) {
            log.error("uploadFileDBSqlite - loadFileAsResource \n" + "Ocurrió un error al transferir archivo en: ",
                    e.getMessage());
        } finally {
            closeStreanm(inputStream, outputStream);
        }
        return bytes;
    }
}
