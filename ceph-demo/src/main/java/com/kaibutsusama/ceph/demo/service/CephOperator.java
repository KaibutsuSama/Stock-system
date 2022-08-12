package com.kaibutsusama.ceph.demo.service;

import com.ceph.fs.CephMount;
import com.ceph.fs.CephStat;

import java.io.*;
import java.util.Arrays;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/12 8:34
 */
public class CephOperator {

    private CephMount mount;
    private String username;
    private String monIp;
    private String userKey;

    public CephOperator(String username, String monIp, String userKey, String mountPath) {
        this.username = username;
        this.monIp = monIp;
        this.userKey = userKey;
        this.mount = new CephMount(username);
        this.mount.conf_set("mon_host", monIp);
        mount.conf_set("key", userKey);
        mount.mount(mountPath);
    }

    //查看目录列表
    public void listDir(String path) throws IOException {
        String[] dirs = mount.listdir(path);
        System.out.println("contents of the dir: " + Arrays.asList(dirs));
    }

    //新建目录
    public void mkDir(String path) throws IOException {
        mount.mkdirs(path, 0755);//0表示十进制
    }

    //删除目录
    public void delDir(String path) throws IOException {
        mount.rmdir(path);
    }

    //重命名目录or文件
    public void renameDir(String oldName, String newName) throws IOException {
        mount.rename(oldName, newName);
    }

    //删除文件
    public void delFile(String path) throws IOException {
        mount.unlink(path);
    }


    /**
     * 上传指定路径文件
     * @param filePath
     * @param fileName
     * @return
     */
    public Boolean uploadFileByPath(String filePath, String fileName) {

        // exit with null if not mount
        if (this.mount == null) {
            return null;
        }
        // file definition
        char pathChar = File.separatorChar;
        String fileFullName = "";
        Long fileLength = 0l;
        Long uploadedLength = 0l;
        File file = null;

        // Io
        FileInputStream fis = null;

        // get local file info
        fileFullName = filePath + pathChar + fileName;
        file = new File(fileFullName);
        if (!file.exists()) {
            return false;
        }
        fileLength = file.length();

        // get io from local file
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // if file exists or not
        String[] dirList = null;
        Boolean fileExist = false;
        try {
            dirList = this.mount.listdir("/");
            for (String fileInfo : dirList) {
                if (fileInfo.equals(fileName)) {
                    fileExist = true;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // transfer file by diff pattern
        if (!fileExist) {
            try {
                // create file and set mode WRITE
                this.mount.open(fileName, CephMount.O_CREAT, 0);
                int fd = this.mount.open(fileName, CephMount.O_RDWR, 0);

                // start transfer
                int length = 0;
                byte[] bytes = new byte[1024];
                while ((length = fis.read(bytes, 0, bytes.length)) != -1) {
                    // write
                    this.mount.write(fd, bytes, length, uploadedLength);

                    // update length
                    uploadedLength += length;

                    // output transfer rate
                    float rate = (float) uploadedLength * 100 / (float) fileLength;
                    String rateValue = (int) rate + "%";
                    System.out.println(rateValue);

                    // complete flag
                    if (uploadedLength == fileLength) {
                        break;
                    }
                }
                System.out.println("文件传输成功！");

                // chmod
                this.mount.fchmod(fd, 0666);

                // close
                this.mount.close(fd);
                if (fis != null) {
                    fis.close();
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (fileExist) {
            try {
                // get file length
                CephStat stat = new CephStat();
                this.mount.stat(fileName, stat);
                long lastLen= stat.size;
                int fd = this.mount.open(fileName, CephMount.O_RDWR, 0);

                // start transfer
                int length = 0;
                byte[] bytes = new byte[1024];
//                fis.skip(uploadedLength);
                long uploadActLen= 0;
                while ((length = fis.read(bytes, 0, bytes.length)) != -1) {
                    // write
                    this.mount.write(fd, bytes, length, lastLen);

                    // update length
                    uploadActLen += length;
                    // output transfer rate
                    float rate = (float) uploadActLen * 100 / (float) fileLength;
                    String rateValue = (int) rate + "%";
                    System.out.println(rateValue);

                    // complete flag
                    if (uploadActLen == fileLength) {
                        break;
                    }
                }
                // 多次上传会进行追加
                System.out.println("追加文件传输成功！");

                // chmod
                this.mount.fchmod(fd, 0666);

                // close
                this.mount.close(fd);
                if (fis != null) {
                    fis.close();
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        return false;
    }

    //set current dir (work dir)
    public void setWorkDir(String path) throws IOException {
        mount.chdir(path);
    }


    //外部获取mount
    public CephMount getMount() {
        return this.mount;
    }

    //umount
    public void umount() {
        mount.unmount();
    }


    /**
     * 下载文件到指定路径
     * @param filePath
     * @param fileName
     * @return
     */
    public Boolean downloadFileByPath(String filePath, String fileName) {

        // exit with null if not mount
        if (this.mount == null) {
            return null;
        }

        // file definition
        char pathChar = File.separatorChar;
        String fileFullName = "";
        Long fileLength = 0l;
        Long downloadedLength = 0l;
        File file = null;

        // IO
        FileOutputStream fos = null;
        RandomAccessFile raf = null;

        // new file object
        fileFullName = filePath + pathChar + fileName;
        file = new File(fileFullName);

        // get cephfs file size
        try {
            CephStat stat = new CephStat();
            this.mount.stat(fileName, stat);
            fileLength = stat.size;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (fileLength != 0) {
            if (!file.exists()) {
                // download file
                int length = 10240;
                byte[] bytes = new byte[length];
                try {
                    int fd = this.mount.open(fileName, CephMount.O_RDONLY, 0);
                    fos = new FileOutputStream(file);
                    float rate = 0;
                    String rateValue = "";
                    while ((fileLength - downloadedLength) >= length && (this.mount.read(fd, bytes, (long) length, downloadedLength)) != -1) {
                        fos.write(bytes, 0, length);
                        fos.flush();
                        downloadedLength += (long) length;

                        // output transfer rate
                        rate = (float) downloadedLength * 100 / (float) fileLength;
                        rateValue = (int) rate + "%";
                        System.out.println(rateValue);

                        if (downloadedLength == fileLength) {
                            break;
                        }
                    }
                    if (downloadedLength != fileLength) {
                        this.mount.read(fd, bytes, fileLength - downloadedLength, downloadedLength);
                        fos.write(bytes, 0, (int) (fileLength - downloadedLength));
                        fos.flush();
                        downloadedLength = fileLength;

                        // output transfer rate
                        rate = (float) downloadedLength * 100 / (float) fileLength;
                        rateValue = (int) rate + "%";
                        System.out.println(rateValue);
                    }

                    System.out.println("Download Success!");
                    fos.close();
                    this.mount.close(fd);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (file.exists()) {
                // download file
                int length = 10240;
                byte[] bytes = new byte[length];
                Long filePoint = file.length();
                try {
                    int fd = this.mount.open(fileName, CephMount.O_RDONLY, 0);
                    raf = new RandomAccessFile(file, "rw");
                    raf.seek(filePoint);
                    downloadedLength = filePoint;
                    float rate = 0;
                    String rateValue = "";
                    while ((fileLength - downloadedLength) >= length && (this.mount.read(fd, bytes, (long) length, downloadedLength)) != -1) {
                        raf.write(bytes, 0, length);
                        downloadedLength += (long) length;

                        // output transfer rate
                        rate = (float) downloadedLength * 100 / (float) fileLength;
                        rateValue = (int) rate + "%";
                        System.out.println(rateValue);

                        if (downloadedLength == fileLength) {
                            break;
                        }
                    }
                    if (downloadedLength != fileLength) {
                        this.mount.read(fd, bytes, fileLength - downloadedLength, downloadedLength);
                        raf.write(bytes, 0, (int) (fileLength - downloadedLength));
                        downloadedLength = fileLength;

                        // output transfer rate
                        rate = (float) downloadedLength * 100 / (float) fileLength;
                        rateValue = (int) rate + "%";
                        System.out.println(rateValue);
                    }
                    // 如果下载中断, 会从上一次下载结束位置进行上传
                    System.out.println("Cut Point Download Success!");
                    raf.close();
                    this.mount.close(fd);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                return false;
            }
        }else {
            System.out.println(" the file is empty!");
        }

        return true;

    }

}