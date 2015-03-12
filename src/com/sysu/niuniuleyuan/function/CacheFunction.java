package com.sysu.niuniuleyuan.function;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;

import org.apache.http.util.EncodingUtils;

import android.R.string;
import android.os.Environment;
import android.util.Log;
import android.view.inputmethod.InputBinding;
/*使用方法
CacheFunction cacheFunction = new CacheFunction();//初始化一个对象
cacheFunction.store("文件名","功能池",inputstream);//存储文件 缓存中有的话直接覆盖缓存

InputStream inputmusic=cacheFunction.fetch("文件名", "功能池");//从缓存获取文件 如果没有就返回null

String fetchstring=cacheFunction.Stream2String(inputmusic);//inputstream转换成string
cacheFunction.clear(0);//清除缓存
*/
public class CacheFunction {
	public CacheFunction(){}
	public InputStream store(String fileName,String owner,InputStream inputStream )
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		byte[] buffer = new byte[1024];  
		int len;  
		try {
			while ((len = inputStream.read(buffer)) > -1 ) {  
			    baos.write(buffer, 0, len);  
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		try {
			baos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}                
		  
		InputStream stream1 = new ByteArrayInputStream(baos.toByteArray());  
		  
		//TODO:显示到前台  
		  
		InputStream stream2 = new ByteArrayInputStream(baos.toByteArray());  
		  
		//TODO:本地缓存  
		GoToSDCard gotoSDCard=new GoToSDCard();
		if(gotoSDCard.isFileExist(owner+"/"+fileName))
		{
			File resultFile=gotoSDCard.write2SDFromInput(owner, fileName,stream2);
			
			if(resultFile==null)
			{
			return null;
			}	
			
			return stream1;
		}
		else{
			File resultFile=gotoSDCard.write2SDFromInput(owner, fileName,stream2);//将数据流保存到SD卡当中
			if(resultFile==null)
			{
			return null;
			}		
			//inputStream=fetch(fileName, owner);
			
			return stream1;
			//return 1;
	   }
		
	}

	public InputStream fetch(String fileName,String owner)
	{
		GoToSDCard gotoSDCard=new GoToSDCard();
		if(gotoSDCard.isFileExist(owner+"/"+fileName))
		{
			Log.v("hao2", "already exists");
			String SDPATH=Environment.getExternalStorageDirectory()+"/NiuNiu/";
			String filepath=SDPATH+owner+"/"+fileName;

			try {
				File file=new File(filepath);
				FileInputStream istream = new FileInputStream(file);
				return istream;
			} 
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

		}
		else 
		{//如果没有存在sd的文件 返回null
			return null;
		}
	}
	public int clear(int owner)
	{
		//InputStream
        delete(new File(Environment.getExternalStorageDirectory()+"/NiuNiu"));
		return 1;
	}
	public class GoToSDCard {
		private String SDPATH=null;
		public String getSDPATH(){
		return SDPATH;
		}
		public GoToSDCard(){
		//得到当前外部存储设备的目录
		// SDCARD
		SDPATH=Environment.getExternalStorageDirectory()+"/NiuNiu/";
		System.out.println("SDPATH="+SDPATH);
		}
		/*
		*在SD卡上创建文件 
		*/
		public File CreatSDFile(String fileName){
		File file =new File(SDPATH+fileName);
		try {
		file.createNewFile();
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		return file;
		}
		/*
		* 在SD卡上创建目录
		*/
		public File creatSDDir(String dirName){
		File dir=new File(SDPATH+dirName);
		if(!dir.exists())
		{
		dir.mkdirs();
		}
		return dir;
		}
		/*
		*判断SD卡上的文件夹是否存在
		*/
		public boolean isFileExist(String fileName){
		File file =new File(SDPATH+fileName);
		return file.exists();
		}
		/*
		*将一个InputSteam里面的数据写入到SD卡中 
		*/
		public File write2SDFromInput(String path,String fileName,InputStream input){
		System.out.println("path="+path+";fileName="+fileName+";");
		File file =null;
		File folder=null;
		OutputStream output=null;
		try {
		folder=creatSDDir(path);
		System.out.println("folder="+folder);
		file=CreatSDFile(path+"/"+fileName);
		System.out.println("file="+file);
		output=new FileOutputStream(file);
		//byte buffer[]=new byte[4*1024];
		int ch;
		while((ch=input.read())!=-1){
		output.write(ch);
		}
		output.flush();
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}finally{
		try{
		output.close();
		input.close();
		}catch(Exception e){
		e.printStackTrace();
		}
		}
		return file;
		}
		}
	
	public String Stream2String(InputStream is) {   

		   BufferedReader reader = new BufferedReader(new InputStreamReader(is));   

		        StringBuilder sb = new StringBuilder();   

		    

		        String line = null;   

		        try {   

		            while ((line = reader.readLine()) != null) {   

		                sb.append(line );   

		            }   

		        } catch (IOException e) {   

		            e.printStackTrace();   

		        } finally {   

		            try {   

		                is.close();   

		            } catch (IOException e) {   

		                e.printStackTrace();   

		            }   

		        }   

		    

		        return sb.toString();   

		    }   

	public static void delete(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}

		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if (childFiles == null || childFiles.length == 0) {
				file.delete();
				return;
			}

			for (int i = 0; i < childFiles.length; i++) {
				delete(childFiles[i]);
			}
			file.delete();
		}
	}
		 
}
