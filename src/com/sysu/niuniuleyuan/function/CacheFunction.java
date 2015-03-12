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
/*ʹ�÷���
CacheFunction cacheFunction = new CacheFunction();//��ʼ��һ������
cacheFunction.store("�ļ���","���ܳ�",inputstream);//�洢�ļ� �������еĻ�ֱ�Ӹ��ǻ���

InputStream inputmusic=cacheFunction.fetch("�ļ���", "���ܳ�");//�ӻ����ȡ�ļ� ���û�оͷ���null

String fetchstring=cacheFunction.Stream2String(inputmusic);//inputstreamת����string
cacheFunction.clear(0);//�������
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
		  
		//TODO:��ʾ��ǰ̨  
		  
		InputStream stream2 = new ByteArrayInputStream(baos.toByteArray());  
		  
		//TODO:���ػ���  
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
			File resultFile=gotoSDCard.write2SDFromInput(owner, fileName,stream2);//�����������浽SD������
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
		{//���û�д���sd���ļ� ����null
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
		//�õ���ǰ�ⲿ�洢�豸��Ŀ¼
		// SDCARD
		SDPATH=Environment.getExternalStorageDirectory()+"/NiuNiu/";
		System.out.println("SDPATH="+SDPATH);
		}
		/*
		*��SD���ϴ����ļ� 
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
		* ��SD���ϴ���Ŀ¼
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
		*�ж�SD���ϵ��ļ����Ƿ����
		*/
		public boolean isFileExist(String fileName){
		File file =new File(SDPATH+fileName);
		return file.exists();
		}
		/*
		*��һ��InputSteam���������д�뵽SD���� 
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
