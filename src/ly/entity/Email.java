package ly.entity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import ly.util.GetMIMA;
import ly.util.HttpUtil;
import ly.util.RegexUtil;
import ly.util.Security;
import ly.util.Util;

import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class Email extends HttpService{
	/** QQ帐号 **/
	private String uin;
	/** QQ密码 **/
	private String password;
	/** QQ-ID **/
	private String aid;
	/** 登录验证字符串 **/
	private String login_sig;
	/** 登录状态 0=登录成功，否则失败**/
	private int status;
	/** 验证码 **/
	private String verify;
	/** 随机数 **/
	private String random;
	/** QQ空间请求的G_TK**/
	protected String g_tk;
	
	String sid;
	
	 String qm_antisky;
	
	String u1="https%3A%2F%2Fmail.qq.com%2Fcgi-bin%2Flogin%3Fvt%3Dpassport%26vm%3Dwpt%26ft%3Dloginpage%26target%3D";
	
	String type;
	
	public Email(){}
	
	public Email(String uin,String password){
		this.uin = uin;
		this.password = password;
	}
	
	/**
	 * QQ登录
	 * 
	 * 前提需要数据
	 *      1. uin       QQ号
	 *      2. password  QQ密码
	 *      
	 * 局限：
	 *      暂时不支持输入验证码登录
	 *      
	 * 
	 * 1.进入登录页面
	 * 2.在该页面截取     1.aid 2.js_ver 3.login_sig
	 * 3.获取登录验证信息
	 * 4.登录
	 * 
	 * @return 是否登录成功  登录成功=0  否则=-1
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws FailingHttpStatusCodeException 
	 * 
	 * @see util.RegexUtil    解析网页字符串
	 * @see util.Util         用于填充字符串工具
	 * @see util.Security  QQ密码加密
	 * 
	 */
	public int login() throws FailingHttpStatusCodeException, MalformedURLException, IOException{
        //1.进入登录页面,获取Cookie
        //https://xui.ptlogin2.qq.com/cgi-bin/xlogin?appid=522005705&daid=4&s_url=https%3A//mail.qq.com/cgi-bin/login?vt=passport%26vm=wpt%26ft=loginpage%26target=&style=25&low_login=1&proxy_url=https%3A//mail.qq.com/proxy.html&need_qr=0&hide_border=1&border_radius=0&self_regurl=http%3A//zc.qq.com/chs/index.html?type=1&app_id=11005?t=regist&pt_feedback_link=http%3A//support.qq.com/discuss/350_1.shtml&css=https%3A//res.mail.qq.com/zh_CN/htmledition/style/ptlogin_input24e6b9.css
  		String $a = "https://xui.ptlogin2.qq.com/cgi-bin/xlogin?appid=522005705&s_url=https%3A//mail.qq.com/cgi-bin/login?vt=passport%26vm=wpt%26ft=loginpage%26target=&style=25&low_login=1&proxy_url=https%3A//mail.qq.com/proxy.html&need_qr=0&hide_border=1&border_radius=0&self_regurl=http%3A//zc.qq.com/chs/index.html?type=1&app_id=11005?t=regist&pt_feedback_link=http%3A//support.qq.com/discuss/350_1.shtml&css=https%3A//res.mail.qq.com/zh_CN/htmledition/style/ptlogin_input24e6b9.css";
		String $v = this.requestGet($a);
		String content = $v;
		
		//2.在该页面截取  1.aid 2.js_ver 3.login_sig
//        $v = RegexUtil.replaceStartEnd($v, "login_sig", "clientip");
//        $v = RegexUtil.replaceStartEnd($v, "\"", "\"");
        
        //从head中取得 login_sig  
		$v=RegexUtil.replaceStartEnd(this.cookie, "pt_login_sig=", ";");
		String $login_sig = $v;
		System.out.println($login_sig);
		
        $v = content;
        $v = RegexUtil.replaceStartEnd($v, "appid:", "lang");
        $v = RegexUtil.replaceStartEnd($v, "\"", "\"");
        String $aid = $v;
        String $uin = this.uin;
        String $password = this.password;
        
         String $action = "3-6-"+new Date().getTime();
        //"https://ssl.ptlogin2.qq.com/ptqrlogin?u1=https%3A%2F%2Fmail.qq.com%2Fcgi-bin%2Flogin%3Fvt%3Dpassport%26vm%3Dwpt%26ft%3Dloginpage%26target%3D&ptredirect=1&h=1&t=1&g=1&from_ui=1&ptlang=2052&action=0-0-1458714155256&js_ver=10152&js_type=1&login_sig=Z*cfYQQZU66C94eaU8*vNKkR-6SMGoJxMqLtKthUf9hPwIF02oipOXHPzA2XudWX&pt_uistyle=25&aid=522005705&daid=4&";
//        String qrLogin="https://ptlogin2.qq.com/ptqrlogin?u1={0}&ptredirect=1&h=1&t=1&g=1&from_ui=1&ptlang=2052&action={1}&js_ver={2}&js_type=1&login_sig={3}&pt_uistyle=25&aid={4}&daid=4&";
//         qrLogin = Util.fillString(qrLogin, u1, $action,"10152",$login_sig,$aid);
//        $v = this.requestGet(qrLogin);
        //http://check.ptlogin2.qq.com/check?regmaster=&pt_tea=1&uin=1098622525&appid=549000912&js_ver=10152&js_type=1&login_sig=puBm08m2LHEGW3B8O08sRx9eZCbXHv1QXAL8HBCXp5i6HopB*HphNGFetWjYT917&u1=http%3A%2F%2Fqzs.qq.com%2Fqzone%2Fv5%2Floginsucc.html%3Fpara%3Dizone&r=0.3449928464833647
//       String checkUrl="http://check.ptlogin2.qq.com/check?regmaster=&pt_tea=1&uin={0}&appid={1}&js_ver=10152&js_type=1&login_sig={2}&u1=http%3A%2F%2Fqzs.qq.com%2Fqzone%2Fv5%2Floginsucc.html%3Fpara%3Dizone&r=0.3449928464833647";
//        checkUrl=Util.fillString(checkUrl,$uin, $aid,$login_sig);
//       String $checkResult = this.requestGet(checkUrl);
        
        //3.获取登录验证信息
//       https://ssl.ptlogin2.qq.com/check?regmaster=&pt_tea=1&pt_vcode=0&uin=1098622525&appid=522005705&js_ver=10152&js_type=1&login_sig=P4HDmiEt7aK8xfjELU2UsILo1Vz9RD6oHWq4We*aQI05bm0jekJ-KkjPVMqptzcu&u1=https%3A%2F%2Fmail.qq.com%2Fcgi-bin%2Flogin%3Fvt%3Dpassport%26vm%3Dwpt%26ft%3Dloginpage%26target%3D&r=0.5316109200939536
        String $url = "https://ssl.ptlogin2.qq.com/check?regmaster=&pt_tea=1&pt_vcode=0&uin={0}&appid={1}&js_ver={2}&js_type=1&login_sig={3}&u1={4}&r={5}";
        $url = Util.fillString($url, $uin, $aid,"10152",$login_sig,u1,new Random().nextDouble()+"");
      System.out.println(this.cookie);
        $v = this.requestGet($url);
        String $status = RegexUtil.replaceStartEnd($v, "'", "'");
        String $verify = RegexUtil.replaceStartEnd($v, ",'", "'");
        System.out.println("验证码得取:=============="+$verify);
        String $hexqq = RegexUtil.replaceStartEnd($v, ",'\\\\x", "'");
        System.out.println($hexqq);
        
        // 获取$verifysession_v1 
        String $verifysession_v1=RegexUtil.replaceStartEnd(this.cookie, "ptvfsession=", ";");
        System.out.println($verifysession_v1);
        
		//如果有验证码，一定要在密码加密前搞定哟
		if($verify.length()>5){
			System.out.println("请进入"+Util.root+"\\verifyTemp\\verify.jpg"+"查看验证码，且在控制台输入验证码→回车");
        	$url = "http://captcha.qq.com/getimage?uin={0}&aid={1}&{2}";
        	$url = Util.fillString($url, $uin, $aid, new Random().nextDouble()+"");
        	this.requestDownload($url, Util.root+"\\verifyTemp\\verify.jpg");
        	//不解释，控制台输入验证码，突然感觉自己好水。。。话又说回来，如果使用B/S架构，这里还要重构。。额，好吧。。。
    		Scanner scanner = new Scanner(System.in);
    		$verify = scanner.nextLine();
        }
        //获取加密后的密码    加密算法更改
//		$password = Security.GetPassword($hexqq,$password,$verify);
//        $password="w2G25oGjyW9Sq2FhaIzD*baOgbV00nGFZh-Ptw1n51KYo*ka1H9mBMBfEWe9vbG1srUcpmA*9D*F6ni-9kaZxXJ7ugtr4kdpMjgl4xbO1cYkIUmawe8pSi4px1A2u7*HObKFdHLnak1Quibw-EZhw4Yh5EwhtkafNRwJOqKhpDPekA0HWkSpgWDzmvV*CAvqMpmX-n*cqL9l4lxpEnIjgQ__";
		try {
			$hexqq=Security.hexchar2bin($hexqq.replace("\\x", "").toUpperCase());
			$password=GetMIMA.password($hexqq, $password, $verify);
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        //4.登录
        //https://ssl.ptlogin2.qq.com/login?u=1098622525&verifycode=!HLT&pt_vcode_v1=0&pt_verifysession_v1=b902f769c6793f&p=adMsFrwvcqAWludTU0t__&pt_randsalt=0&u1=&ptredirect=1&h=1&t=1&g=1&from_ui=1&ptlang=2052&action=3-10-1458712871796&js_ver=10152&js_type=1&login_sig=P4HDmiEt7aK8xfjELU2UsILo1Vz9RD6oHWq4We*aQI05bm0jekJ-KkjPVMqptzcu&pt_uistyle=25&aid=522005705&daid=4&
        $url = "https://ssl.ptlogin2.qq.com/login?u={0}&p={1}&verifycode={2}&pt_vcode_v1=0&aid={3}&u1="+u1+"&account="+uin+"&pt_randsalt=0&h=1&g=1&ptredirect=0&ptlang=2052&from_ui=1&action={4}&js_ver={5}&js_type=1&login_sig={6}&pt_uistyle=25&pt_randsalt=0&pt_vcode_v1=0&pt_verifysession_v1={7}&daid=4";
        $url = Util.fillString($url, $uin, $password, $verify, $aid, $action,"10152", $login_sig,$verifysession_v1);
        System.out.println($url);
    	$v = this.requestGet($url);
    	
    	$status = RegexUtil.replaceStartEnd($v, "'", "'");
    	System.out.println($v);//打印看下是否搞定了
    	
    	if($v.contains("登录成功")){
    		String replaceStartEnd = RegexUtil.replaceStartEnd($v, ",'0','", "'");
//    		$v=this.requestGet(replaceStartEnd,false);

			//模拟请求
			webClient(replaceStartEnd);
    	}
    	
		System.out.println(this.cookie);
		//6.设置登录状态
		if("0".equals($status))
    		this.status = 0;
    	else
    		this.status = -1;
		
		
		return this.status;
	}
	
	static WebClient client = new WebClient(BrowserVersion.FIREFOX_17);
	static{
		client.getOptions().setThrowExceptionOnScriptError(false);// js错误是否直接抛出
		client.getOptions().setJavaScriptEnabled(false);// 是否加载js
		client.getOptions().setUseInsecureSSL(true);
		client.getOptions().setCssEnabled(false);// 是否加载css
		DefaultHttpParams.getDefaultParams().setBooleanParameter(HttpMethodParams.SINGLE_COOKIE_HEADER, true);
	}
	public  void webClient(String url) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		HtmlPage page = client.getPage(url);
		if(page.asXml().contains("收件箱")){
			this.sid=RegexUtil.replaceStartEnd(page.asXml(),"sid=", "&amp;");
			Set<Cookie> cookies = client.getCookieManager().getCookies();
			List<String> list=new ArrayList<String>();
			for (Cookie cookie : cookies) {
				list.add(cookie.getName()+","+cookie.getValue());
			}
			System.out.println("登录成功...................sid="+this.sid);
		}
		
	}
	
//	public String getEmailList() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
//		String s="https://set3.mail.qq.com/cgi-bin/laddr_lastlist?sid="+HttpUtil.stringEncoder(this.sid)+"&encode_type=js&t=addr_datanew&s=AutoComplete&category=hot&resp_charset=UTF8&ef=js&r="+new Random().nextDouble();
//		HtmlPage page = client.getPage(s);
//		return page.asXml();
//	}
	
	public String getUin() {
		return uin;
	}

	public void setUin(String uin) {
		this.uin = uin;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getLogin_sig() {
		return login_sig;
	}

	public void setLogin_sig(String login_sig) {
		this.login_sig = login_sig;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public String getG_tk() {
		return g_tk;
	}

	public void setG_tk(String g_tk) {
		this.g_tk = g_tk;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getQm_antisky() {
		return qm_antisky;
	}

	public void setQm_antisky(String qm_antisky) {
		this.qm_antisky = qm_antisky;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}
	
	
}
