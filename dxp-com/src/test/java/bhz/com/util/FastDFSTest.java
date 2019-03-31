package dxp.com.util;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;


public class FastDFSTest {
	
	/**
	 * 上传
	 */
	public static void upload() throws Exception {
		// id:	group1/M00/00/00/wKgBr1crVnSAbI4sAAAeV2aU4jU126.jpg
		// fastdfsBasePath = http://192.168.1.170/fastdfs
		// url:	http://192.168.1.170/fastdfs/group1/M00/00/00/wKgBr1crVnSAbI4sAAAeV2aU4jU126.jpg
    	String path = System.getProperty("user.dir") + File.separatorChar + "source" +  File.separatorChar + "001.jpg";
		File file = new File(path);
		String fileId = FastDFSClientUtils.upload(file, path);
		System.out.println("本地文件：" + path + "，上传成功！ 文件ID为：" + fileId);
	}
	
	/**
	 * 下载
	 */
	public static void download() throws Exception {
		// id:	group1/M00/00/00/wKgBr1crVnSAbI4sAAAeV2aU4jU126.jpg
		// url:	http://192.168.1.170/fastdfs/group1/M00/00/00/wKgBr1crVnSAbI4sAAAeV2aU4jU126.jpg
		String fileId = "group1/M00/00/00/wKgBr1crUIuAJ22iAADHTIxNXeI872.jpg";
		InputStream inputStream = FastDFSClientUtils.download("group1", fileId);
		System.out.println(inputStream.available());
		String path = System.getProperty("user.dir") + File.separatorChar + "receive" +  File.separatorChar + "001.jpg";
		System.out.println(path);
		//FileUtils.copyInputStreamToFile(inputStream,  new File(path));
	}

	/**
	 * 删除
	 */
	public static void delete() throws Exception {
		String fileId = "group1/M00/00/00/wKgBr1crVnSAbI4sAAAeV2aU4jU126.jpg";
		int result = FastDFSClientUtils.delete("group1", fileId);
		System.out.println(result == 0 ? "删除成功" : "删除失败:" + result);
	}


	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
	

		//upload();
		download();
		Thread.sleep(10000);
		download();
		Thread.sleep(10000);
		download();
		//delete();

	}

}
