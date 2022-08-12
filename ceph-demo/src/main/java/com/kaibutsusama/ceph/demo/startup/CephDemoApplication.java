package com.kaibutsusama.ceph.demo.startup;

import com.kaibutsusama.ceph.demo.service.CephOperator;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/12 8:37
 */
@SpringBootApplication
public class CephDemoApplication {

    public static void main(String[] args) {

        System.out.println("start....");
        String username = "admin";
        String monIp = "192.168.50.131:6789;192.168.50.132:6789;192.168.50.133:6789";
        String userKey = "AQBZBypdMchvBRAAbWVnIGyYNvxWQZ2UkuiYew==";
        String mountPath = "/";
        CephOperator cephOperate = null;
        try {
            String opt = (args == null || args.length < 1)? "" : args[0];
            cephOperate = new CephOperator(username, monIp, userKey, mountPath);
            if("upload".equals(opt)) {
                cephOperate.uploadFileByPath("/temp_upload_fs", args[1]);
            }else if("download".equals(opt)) {
                cephOperate.downloadFileByPath("/temp_download_fs", args[1]);
            }else {
                System.out.println("Unrecognized Command! Usage  opt[upload|download] filename[path]!");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(null != cephOperate) {
                cephOperate.umount();
            }
        }
        System.out.println("end....");

    }

}