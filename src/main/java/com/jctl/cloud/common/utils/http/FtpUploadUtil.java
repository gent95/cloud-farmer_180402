package com.jctl.cloud.common.utils.http;

import com.jctl.cloud.common.config.JCTLConfig;
import com.jctl.cloud.common.utils.IdGen;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * FTP上传工具
 * Created by kay on 2017/4/18.
 */
public class FtpUploadUtil implements JCTLConfig {
    static {
    }

    private static FTPClient ftpClient;

    static {
        ftpClient = new FTPClient();
    }

    /**
     * 返回值默认在map  key=flag  0上传失败  1上传成功   成功返回url
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static Map<String, String> upload(InputStream stream, MultipartFile file) throws IOException {

        try {
            return upload(file, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(stream);
        }
        return new HashMap<>();
    }

    /**
     * 给定流文件  以及文件类型
     *
     * @param stream
     * @param ext
     * @return
     * @throws IOException
     */
    public static Map<String, String> upload(InputStream stream, String ext) throws IOException {
        Map<String, String> result = new HashMap<>();
        try {
            connect();
            String url = uploadAndChange();
            String name = getUUID() + "." + ext;
            ftpClient.storeFile(name, stream);
            result.put("flag", "1");
            result.put("url", url + name);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FTP客户端出错！", e);
        } finally {
            close(stream);
        }
    }

    /**
     * 给定MultipartFile 获取的文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static Map upload(MultipartFile file) throws IOException {
        InputStream stream = null;
        try {
            return upload(file, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FTP客户端出错！", e);
        } finally {
            close(stream);
        }
    }


    private static Map<String, String> upload(MultipartFile file, InputStream stream) throws IOException {
        Map<String, String> result = new HashMap();
        connect();
        String url = uploadAndChange();
        String name = getUUID(file);
        ftpClient.storeFile(name, stream);
        result.put("flag", "1");
        result.put("url", url + name);
        return result;
    }

    private static void connect() throws IOException {
        ftpClient.connect(ADDRESS,7221);
        ftpClient.login(USERNAME, PASSWORD);
        ftpClient.enterLocalPassiveMode();
        int reply=ftpClient.getReplyCode();
        if(!FTPReply.isPositiveCompletion(reply)){
            System.out.println("FTP服务器拒绝连接");
        }
    }

    private static String uploadAndChange() throws IOException {
        String dir = new SimpleDateFormat("yyyyMMdd").format(new Date());
        ftpClient.changeWorkingDirectory("upload");
        ftpClient.makeDirectory(dir);
        ftpClient.changeWorkingDirectory(dir);
        ftpClient.setBufferSize(1024);
        ftpClient.setControlEncoding("utf-8");
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        return URL + dir + "/";
    }

    private static void close(InputStream stream) {
        IOUtils.closeQuietly(stream);
        try {
            ftpClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("关闭FTP连接发生异常！", e);
        }
    }

    /**
     * 检查目录在服务器上是否存在 true：存在  false：不存在
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static boolean existDirectory(String path, FTPClient ftpClient) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        for (FTPFile ftpFile : ftpFileArr) {
            if (ftpFile.isDirectory()
                    && ftpFile.getName().equalsIgnoreCase(path)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 转移到FTP服务器工作目录
     *
     * @param path
     * @return
     * @throws IOException
     */
    public boolean changeDirectory(String path, FTPClient ftpClient) throws IOException {
        return ftpClient.changeWorkingDirectory(path);
    }

    public static String getUUID(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String name = IdGen.uuid() + filename.substring(filename.lastIndexOf("."));
        return name;
    }

    public static String getUUID() {
        return IdGen.uuid();
    }


    public static void testUpload() {
        FTPClient ftpClient = new FTPClient();
        FileInputStream fis = null;

        try {
            ftpClient.connect("61.149.204.178");
            ftpClient.login("upload", "123456");

            File srcFile = new File("C:/1111.png");
            fis = new FileInputStream(srcFile);
            //日期文件夹
            String dir = new SimpleDateFormat("yyyyMMdd").format(new Date());
            //更改活动空间
            ftpClient.makeDirectory(dir);
            ftpClient.changeWorkingDirectory(dir);
            ftpClient.setBufferSize(1024);

            ftpClient.setControlEncoding("utf-8");
            //设置文件类型（二进制）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            //保存文件名
            String filename = srcFile.getName();
            String saveName = new Date().getTime() + filename.substring(filename.lastIndexOf("."));
            ftpClient.storeFile(saveName, fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FTP客户端出错！", e);
        } finally {
            IOUtils.closeQuietly(fis);
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            }
        }
    }


    public static void testDownload() {
        FTPClient ftpClient = new FTPClient();
        FileOutputStream fos = null;

        try {
            ftpClient.connect("61.149.204.178");
            ftpClient.login("upload", "123456");

            String remoteFileName = "/20170424/8b9318e5554540a3b873330df1d80ba2.png";
            fos = new FileOutputStream("c:/4596fc013a2e44a69eba25dff72c0679.png");

            ftpClient.setBufferSize(1024);
            //设置文件类型（二进制）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.retrieveFile(remoteFileName, fos);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FTP客户端出错！", e);
        } finally {
            IOUtils.closeQuietly(fos);
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            }
        }
    }


//    private static FTPClient getFtpClient() {
//
//    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
//        testUpload();
        testDownload();
    }

    private static class Client extends FTPClient {

        private static Client instance = new Client();

        private Client() {
        }

        public static Client getInstance() {
            return instance;
        }


    }

}
