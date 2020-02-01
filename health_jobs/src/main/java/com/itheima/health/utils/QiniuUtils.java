package com.itheima.health.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.FileInputStream;
import java.util.UUID;

/**
 * 七牛云工具类
 */
public class QiniuUtils {
	public  static String qiniu_img_url_pre = "http://q4prrol5s.bkt.clouddn.com/";
	public  static String accessKey = "PqIoeRbFbVlgPvvgNcOlQvFG4k-ucL0kG-SK4392";
	public  static String secretKey = "cVcYRWUsVDM3X6sviVzWMX3DmaWrA-7wAFT72Ptd";
	public  static String bucket = "wzyzero";

	/**
	 * 删除文件
	 * @param fileName 服务端文件名
	 */
	public static void deleteFileFromQiniu(String fileName){
		//构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone0());
		String key = fileName;
		Auth auth = Auth.create(accessKey, secretKey);
		BucketManager bucketManager = new BucketManager(auth, cfg);
		try {
			bucketManager.delete(bucket, key);
		} catch (QiniuException ex) {
			//如果遇到异常，说明删除失败
			System.err.println(ex.code());
			System.err.println(ex.response.toString());
		}
	}
	// 测试上传与删除
	public static void main(String args[]) throws Exception{
		 //测试删除
		deleteFileFromQiniu("f873febd302e4a97b220fc1168ff50f6_FLAMING MOUNTAIN.JPG");
	}
}