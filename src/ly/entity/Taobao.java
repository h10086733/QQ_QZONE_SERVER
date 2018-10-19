package ly.entity;

import ly.util.RegexUtil;

public class Taobao extends HttpService{
	String uin;
	String password;
	
	String initUrl="https://login.m.taobao.com/login.htm";
	
	public Taobao(){}
	public Taobao(String uin,String password){
		this.uin = uin;
		this.password = password;
	}
	public int login(){
		String requestGet = this.requestGet(initUrl);
		
		String loginUrl=RegexUtil.replaceStartEnd(requestGet, "class=\"mlogin\" action=\"", "\"");
		
		
		//密码加密   参数
		
		String J_Exponent=RegexUtil.replaceStartEnd(requestGet, "J_Exponent\" value=\"", "\"");
		
		String J_Module=RegexUtil.replaceStartEnd(requestGet, "J_Module\"\\s*?value=\"", "\"");
		
		System.out.println(loginUrl);
		System.out.println(J_Exponent);
		System.out.println(J_Module);
		
		return 0;
	}
}
