package com.fzb.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class IOUtil {

	public static final byte[] getByteByInputStream(InputStream in) {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		byte[] tempByte = new byte[1024];
		try {
			int length = 0;
			while ((length = in.read(tempByte)) != -1) {
				bout.write(tempByte, 0, length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bout.toByteArray();
	}

	public static final String getStringInputStream(InputStream in) {
		return new String(getByteByInputStream(in));
	}

	public static final void getAllFiles(String path, List<File> files) {
		getAllFilesByProfix(path, null, files);
	}

	public static final void getAllFilesByProfix(String path, String suffix,
			List<File> files) {
		File file = new File(path);
		if (file.isDirectory()) {
			File[] fs = file.listFiles();
			if (fs != null) {
				for (File file2 : fs) {
					if (file2.isDirectory()) {
						getAllFilesByProfix(file2.getPath(), suffix, files);
					} else {
						if (suffix != null) {
							if (file2.toString().endsWith(suffix)) {
								files.add(file2);
							}
						} else {
							files.add(file2);
						}
					}
				}
			}

		} else {
			if (suffix != null) {
				if (file.toString().endsWith(suffix)) {
					files.add(file);
				}
			} else {
				files.add(file);
			}
		}
	}

	public static void moveOrCopy(String filer,String tag,boolean isMove)
	{
		File f=new File(filer);
		if(f.isDirectory())
		{
			File fs[]=new File(filer).listFiles();
			tag=tag+"/"+f.getName();
			new File(tag).mkdirs();
			for(File fl:fs)
			{
				if(fl.isDirectory())
				{
					moveOrCopy(fl.toString(),tag,isMove);
				}
				else
				{
					moveOrCopyFile(fl.toString(),tag+"/"+fl.getName(),isMove);
				}
			}
		}
	}

	public static void moveOrCopyFile(String src, String tag, boolean isMove) {
		try {
			long s = System.currentTimeMillis();
			File f = new File(src);
			FileInputStream in = new FileInputStream(src);
			new File(tag).createNewFile();
			FileOutputStream out = new FileOutputStream(tag);
			// 小于1M(大小根据自己的情况而定)的文件直接一次性写入。
			byte[] b = new byte[1024];
			int length = 0; // 出来cnt次后 文件 跳出循环
			while ((length = in.read(b)) != -1) {
				out.write(b, 0, length);
			}
			in.read(b);
			out.write(b);
			// 一定要记得关闭流额。 不然其他程序那个文件无法进行操作
			in.close();
			out.close();
			System.out.println(System.currentTimeMillis() - s);
			if (isMove) {
				f.delete();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}