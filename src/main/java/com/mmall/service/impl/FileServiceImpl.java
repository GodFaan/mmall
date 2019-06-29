package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


/**
 * @program: mmall
 * @description: 文件上传相关的实现类
 * @author: GodFan
 * @create: 2019-06-21 15:01
 **/
@Service("iFileService")
public class FileServiceImpl implements IFileService {
    private final static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String upload(MultipartFile file, String path) {
        String filename = file.getOriginalFilename();
        String fileExtensionName = filename.substring(filename.lastIndexOf(".") + 1);//获取文件的扩展名（后缀名）
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;//防止两个文件的名称完全相同
        logger.info("开始上传文件，上传的文件名：{},上传的文件路径：{}，新文件名:{}", filename, path, uploadFileName);
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path, uploadFileName);
        try {
            file.transferTo(targetFile);
            //todo 将targetFile上传到ftp服务器
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //TODO 上传完后，删除upload下的文件
            targetFile.delete();
        } catch (IOException e) {
            logger.error("文件上传异常", e);
            return null;
        }
        return targetFile.getName();
    }
}
