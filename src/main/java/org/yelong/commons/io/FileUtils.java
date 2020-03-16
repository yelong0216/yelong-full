/**
 * 
 */
package org.yelong.commons.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author 彭飞
 * @date 2019年5月10日下午3:59:39
 */
public class FileUtils {
	
	public static final long ONE_KB = 1024;
	
	public static final long ONE_MB = ONE_KB * ONE_KB;
	
	public static final long ONE_GB = ONE_MB * ONE_KB;
	
	public static final long ONE_TB = ONE_GB * ONE_KB;
	
	/**
	 * 将 base64码转换为文件
	 * @param base64
	 * @param filename names the name elements
	 * @return the file
	 * @throws IOException
	 */
	public static File base64ConvertFile (String base64 , String ... filename) throws IOException{
		if(StringUtils.isBlank(base64)) {
			throw new NullPointerException();
		}
		File file = createNewFile(filename);
		byte[] data = java.util.Base64.getDecoder().decode(base64);
		org.apache.commons.io.FileUtils.writeByteArrayToFile(file, data);
		return file;
	}

	/**
	 * 获取文件的创建时间
	 * @author 彭飞
	 * @date 2019年9月6日下午5:43:27
	 * @version 1.2
	 * @param fileAbsolutePath
	 * @return
	 */
	public static Long getFileCreateTime(String fileAbsolutePath){
		File file = new File(fileAbsolutePath);
		try {
			Path path= Paths.get(fileAbsolutePath);
			BasicFileAttributeView basicview= Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS );
			BasicFileAttributes attr = basicview.readAttributes();
			return attr.creationTime().toMillis();
		} catch (Exception e) {
			e.printStackTrace();
			return file.lastModified();
		}
	}

	/**
	 * 创建文件，如果文件所在的目录不存在则一同创建
	 * @param names the name elements
	 * @return the file
	 * @throws IOException 如果这个文件已经存在
	 */
	public static File createNewFile(String ... names) throws IOException {
		File file = getFile(names);
		if( file.exists() ) {
			throw new FileExistsException(file);
		} else {
			createDirectory(FilenameUtils.getFullPath(file.getAbsolutePath()));
			file.createNewFile();
		}
		return file;
	}

	/**
	 * 创建文件，如果文件所在目录不存在则一同创建。
	 * 如果这个文件已经存在则删除原文件并创建新文件
	 * @param names the name elements
	 * @return the file
	 */
	public static final File createNewFileOverride(String ... names) throws IOException {
		File file = getFile(names);
		if( file.exists() ) {
			org.apache.commons.io.FileUtils.deleteQuietly(file);
		} 
		createDirectory(FilenameUtils.getFullPath(file.getAbsolutePath()));
		file.createNewFile();
		return file;
	}

	/**
	 * 创建目录。
	 * 如果目录存在则返回它本身
	 * 如果目录的抽象路径中的目录不存在则创建。
	 * 注意：如果目录存在这并不会抛出异常
	 * @param names the name elements
	 * @return the file
	 * @throws IOException
	 */
	public static File createDirectory(String ... names) throws IOException {
		File file = getFile(names);
		if( file.exists() ) {
			return file;
		} else {
			file.mkdirs();
		}
		return file;
	}

	/**
	 * filePath的文件是否存在
	 * @date 2019年11月18日上午9:52:07
	 * @version 1.3
	 * @param filePath 文件路径
	 * @return <tt>true</tt>filePath的文件存在
	 * @throws IOException
	 */
	public static boolean exists(String ... names){
		return getFile(names).exists();
	}

	private static File getFile(String ... names) {
		return org.apache.commons.io.FileUtils.getFile(names);
	}

	public static String requireNonExist(String filePath) throws FileNotFoundException{
		if( !exists(filePath) ) {
			throw new FileNotFoundException();
		}
		return filePath;
	}

	public static String requireNonExist(String filePath,String message) throws FileNotFoundException{
		if( !exists(filePath) ) {
			throw new FileNotFoundException(message);
		}
		return filePath;
	}

	/**
	 * 字节记录显示大小
	 * @param size 大小
	 * @param decimalDigits 小数位数
	 * @return a human-readable display value (includes units - TB, GB, MB, KB or bytes)
	 */
	public static String byteCountToDisplaySize(long size , int decimalDigits) {
		double s = size;
		String unit;
		if( s < ONE_KB ) {
			unit = "bytes";
		} else if( s < ONE_MB) {
			unit = "kb";
			s = s / ONE_KB;
		} else if( s < ONE_GB) {
			unit = "mb";
			s = s / ONE_MB;
		} else if( s < ONE_TB) {
			unit = "gb";
			s = s / ONE_GB;
		} else {
			unit = "tb";
			s = s / ONE_TB;
		}
		return String.format("%.2f", s)+unit;
	}

}
