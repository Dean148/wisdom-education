package com.education.common.utils;import cn.hutool.core.util.StrUtil;import cn.hutool.extra.spring.SpringUtil;import com.education.common.config.OssProperties;import com.education.common.constants.SystemConstants;import com.education.common.enums.OssPlatformEnum;import com.sun.javafx.PlatformUtil;import org.springframework.web.multipart.MultipartFile;import javax.imageio.ImageIO;import java.awt.image.BufferedImage;import java.io.*;import java.util.zip.ZipEntry;import java.util.zip.ZipOutputStream;/** * 文件操作工具类 * @author zengjintao * @create 2019/3/25 13:26 * @since 1.0 **/public class FileUtils {    public static String getUploadPath() {        OssProperties ossProperties = SpringUtil.getBean(OssProperties.class);        String localTmpFileUploadPath = ossProperties.getLocalTmpFileUploadPath();        // 如果设置启用本地存储，则直接去默认文件上传路径        if (OssPlatformEnum.LOCAL.getValue().equals(ossProperties.getPlatform())) {            if (StrUtil.isBlank(localTmpFileUploadPath)) {                return localTmpFileUploadPath;            }            return ossProperties.getBucketName();        } else {            // 如果设置启用oss存储，判断是否有配置本地文件上传临时路径            boolean windows = PlatformUtil.isWindows();            boolean linux = PlatformUtil.isLinux();            if (StrUtil.isBlank(localTmpFileUploadPath)) {                File file = null;                // 取默认文件上传路径                if (windows) {                    file = new File(SystemConstants.DEFAULT_LOCAL_TMP_FILE_UPLOAD_PATH);                } else if (linux) {                    file = new File(SystemConstants.DEFAULT_LINUX_TMP_FILE_UPLOAD_PATH);                }                if (!file.exists()) {                    file.mkdirs();                }                return file.getPath();            }            return localTmpFileUploadPath;        }    }    public static boolean isOpenOssUpload() {        String platform = SpringUtil.getProperty("oss.upload.platform");        return !OssPlatformEnum.LOCAL.getValue().equals(platform);    }    public static String getFileHeader( MultipartFile file) {        InputStream is = null;        String value = null;        try {            is = file.getInputStream();            byte[] b = new byte[4];            is.read(b, 0, b.length);            value = bytesToHexString(b);        } catch (Exception e) {            e.printStackTrace();        } finally {            if (null != is) {                try {                    is.close();                } catch (IOException e) {                }            }        }        return value;    }    /**     * @param bytes     * @return String     * @description 字节数组转为16进制     * @author xyz     */    public static String bytesToHexString(byte[] bytes) {        StringBuilder stringBuilder = new StringBuilder();        for (int i = 0; i < bytes.length; i++) {            int v = bytes[i] & 0xFF;            String hv = Integer.toHexString(v);            if (hv.length() < 2) {                stringBuilder.append(0);            }            stringBuilder.append(hv);        }        return stringBuilder.toString();    }    /**     * 将文件压缩成zip     * @param targetFilePath     * @param savePath     * @throws Exception     */    public static void fileDirectoryToZip(String targetFilePath, String savePath) throws Exception {        File file = new File(targetFilePath);        if (!file.exists())            throw new RuntimeException(targetFilePath + "does not exist");        File fileList[] = file.listFiles();        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(savePath));        byte[] buffer = new byte[1024];        if (ObjectUtils.isNotEmpty(fileList)) {            for (File fileItem : fileList) {                FileInputStream fis = new FileInputStream(fileItem);                out.putNextEntry(new ZipEntry(fileItem.getName()));                int len = 0;                while ((len = fis.read(buffer)) > 0) { // 读入需要下载的文件的内容，打包到zip文件                    out.write(buffer, 0, len);                }                out.closeEntry();                fis.close();            }        }        out.close();    }    public static byte[] getByteFromInputStream(InputStream inputStream) {        try {            BufferedImage bufferedImage = ImageIO.read(inputStream);            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();            ImageIO.write(bufferedImage, "jpg", byteArrayOut);            return byteArrayOut.toByteArray();        } catch (Exception e) {            e.printStackTrace();            return null;        }    }}