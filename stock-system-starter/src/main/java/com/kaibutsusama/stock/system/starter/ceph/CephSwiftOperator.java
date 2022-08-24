package com.kaibutsusama.stock.system.starter.ceph;

import lombok.extern.log4j.Log4j2;
import org.javaswift.joss.client.factory.AccountConfig;
import org.javaswift.joss.client.factory.AccountFactory;
import org.javaswift.joss.client.factory.AuthenticationMethod;
import org.javaswift.joss.model.Account;
import org.javaswift.joss.model.Container;
import org.javaswift.joss.model.StoredObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/24 15:36
 */
@Log4j2
public class CephSwiftOperator {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 认证接入地址
     */
    private String authUrl;

    /**
     * 默认容器名称
     */
    private String defaultContainerName;

    /**
     * Ceph账户对象
     */
    private Account account;

    /**
     * Ceph容器对象
     */
    private Container container;


    public CephSwiftOperator(String username, String password, String authUrl, String defaultContainerName) {
        // 初始化配置信息
        this.username = username;
        this.password = password;
        this.authUrl = authUrl;
        this.defaultContainerName = defaultContainerName;
        init();

    }

    /**
     * 初始化建立连接
     */
    public void init() {
        try {
            // Ceph用户认证配置
            AccountConfig config = new AccountConfig();
            config.setUsername(username);
            config.setPassword(password);
            config.setAuthUrl(authUrl);
            config.setAuthenticationMethod(AuthenticationMethod.BASIC);
            account = new AccountFactory(config).createAccount();
            // 获取容器
            Container newContainer = account.getContainer(defaultContainerName);
            if (!newContainer.exists()) {
                container = newContainer.create();
                log.info("account container create ==> " + defaultContainerName);
            } else {
                container = newContainer;
                log.info("account container exists!  ==> " + defaultContainerName);
            }
        }catch(Exception e) {
            // 做异常捕获, 避免服务不能正常启动
            log.error("Ceph连接初始化异常： " + e.getMessage());
        }
    }


    /**
     * 上传对象
     * @param remoteName
     * @param filepath
     */
    public void createObject(String remoteName, String filepath) {
        StoredObject object = container.getObject(remoteName);
        object.uploadObject(new File(filepath));
    }

    /**
     * 上传文件对象（字节数组形式）
     * @param remoteName
     * @param inputStream
     */
    public void createObject(String remoteName, byte[] inputStream) {
        StoredObject object = container.getObject(remoteName);
        object.uploadObject(inputStream);
    }

    /**
     * 获取指定对象
     * @param containerName
     * @param objectName
     * @param outpath
     */
    public void  retrieveObject(String objectName,String outpath){
        StoredObject object = container.getObject(objectName);
        object.downloadObject(new File(outpath));
    }

    /**
     * 下载文件， 转为文件流形式
     * @param objectName
     * @return
     */
    public InputStream retrieveObject(String objectName){
        StoredObject object = container.getObject(objectName);
        return object.downloadObjectAsInputStream();
    }


    /**
     * 删除指定文件对象
     * @param containerName
     * @param objectName
     * @return
     */
    public boolean deleteObject(String objectName){
        try {
            StoredObject object = container.getObject(objectName);
            object.delete();
            return !object.exists();
        }catch(Exception e) {
            log.error("Ceph删除文件失败: " + e.getMessage());
        }
        return false;
    }

    /**
     * 获取所有容器
     * @return
     */
    public List listContainer() {
        List list = new ArrayList();
        Collection<Container> containers = account.list();
        for (Container currentContainer : containers) {
            list.add(currentContainer.getName());
            System.out.println(currentContainer.getName());

        }
        return list;
    }

}