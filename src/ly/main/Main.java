package ly.main;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import ly.entity.QQ;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 */
public class Main {

	public static void main(String[] args) throws HttpException, IOException, InterruptedException {
//		//qq控件登录
//		QQ qq = new QQ("1098622525","");
//		qq.login();                            //登录
		
		Map<String, String> hm=new HashMap<String, String>();
		hm.put("451063317", "");
		//QQ列表
		for (Entry<String,String> entry:hm.entrySet()) {
			QQ qq=new QQ(entry.getKey(),entry.getValue());
			int login = qq.login();
			if(login!=0){
				System.out.println("登录失败....");
				continue;
			}
			//留言
			qq.writeQQ("787578115","nihao");
		}
//			String indexParseQQ=qq.indexParseQQ("1138433072");
//			if(indexParseQQ.contains("\"succ\"")){
//				//留言
//				System.out.println("qq首页赞成功.....");
//			}else{
//				System.out.println(indexParseQQ);
//			}
//		String vistorStr=qq.vistorPhoto("714312715");
//		System.out.println(vistorStr);
//		if(vistorStr.contains("code\":0")){
//			System.out.println("访客成功....");
//		}
//		String shuoshuo=qq.send("自动化。。。。。。");
//		System.out.println(shuoshuo);
//		if(shuoshuo.contains("code\":0")){
//			System.out.println("说说发表成功....");
//		}
//		String dianzan=qq.parseQQ("1138433072","");
//		if(dianzan.contains("\"succ\"")){
//			//点赞
//			System.out.println("点赞成功....");
//		}
		//添加好友
//		String addFread=qq.addFread("342928885");
//		if(addFread.contains("请求发送成功")){
//			//点赞
//			System.out.println("添加好友请求发送成功，等待对方上线....");
//		}
		//访问列表
//		String vistor=qq.vistor("1098622525");
//		System.out.println(vistor);
//		//QQ首页赞成功
//		String indexParseQQ=qq.indexParseQQ("1138433072");
//		if(indexParseQQ.contains("\"succ\"")){
//			//留言
//			System.out.println("qq首页赞成功.....");
//		}
		
		
		//email控件登录
//		Email qq = new Email("1098622525","");
//		qq.login();                            //登录
		
		
	}
	/***
	 * 
	
	* @Title: control
	
	* @Description: 统一入口
	
	* @param @param qq
	* @param @throws IOException
	* @param @throws FileNotFoundException
	* @param @throws InterruptedException    设定文件
	
	* @return void    返回类型
	
	* @throws
	 */
	public static void control(QQ qq) throws IOException,
			FileNotFoundException, InterruptedException {
		List<String> readLines = IOUtils.readLines(new FileInputStream(new File("C:\\Users\\zhangguojing\\Desktop\\qq2.txt")));

//		List<String> readLines = new ArrayList<String>();
//		readLines.add("379809397");
//		readLines.add("573876095");
//		readLines.add("482000254");
		
		Set<String> qqSet=new HashSet<String>();
		//qq放到set里
		for (String q : readLines) {
			if(StringUtils.isNotBlank(q)){
				String[] qN=q.split("=");
				qqSet.add(qN[qN.length-1]);
			}
		}
		int success=0;
		int error=0;
		for (String qqNum : qqSet) {//最多20个。。 
			
			/**
			 * 添加好友
			 */
//			String addFread=qq.addFread(qqNum);
//			String message=RegexUtil.replaceStartEnd(addFread, "message\":\"","\",");
//			if(addFread.contains("申请已发送，请等待对方在QQ上确认")||addFread.contains("添加QQ好友成功")){
//				success++;
//				//点赞
//				System.out.println(qqNum+".添加好友请求发送成功，等待对方上线....");
//			}else{
//				error++;
//				System.out.println(qqNum+",请求失败:"+message);
//			}
			
			Thread.sleep(3000);
		}
		System.out.println("成功:"+success+"个，失败:"+error+"个");
		
	}
	
}
