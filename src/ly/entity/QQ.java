package ly.entity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import ly.util.GetMIMA;
import ly.util.RegexUtil;
import ly.util.Security;
import ly.util.StringUtil;
import ly.util.Util;

import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.TextPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class QQ extends HttpService{
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
	protected String js_ver="10270";
	
	String u3="https%3A%2F%2Fqzs.qzone.qq.com%2Fqzone%2Fv5%2Floginsucc.html%3Fpara%3Dizone";
	//qq空间
	String u1="http%3A%2F%2Fqzs.qq.com%2Fqzone%2Fv5%2Floginsucc.html%3Fpara%3Dizone";
	
	//qq空间
	String u2="http%3A%2F%2Fqzs.qq.com%2Fqzone%2Fv5%2Floginsucc.html%3Fpara%3Dizone";
	
//	String $qqa = "http://ui.ptlogin2.qq.com/cgi-bin/login?hide_title_bar=1&low_login=0&qlogin_auto_login=1&no_verifyimg=1&link_target=blank&appid=549000912&style=12&target=self&s_url=http%3A//qzs.qq.com/qzone/v5/loginsucc.html?para=izone&pt_qr_app=%CA%D6%BB%FAQQ%BF%D5%BC%E4&pt_qr_link=http%3A//z.qzone.com/download.html&self_regurl=http%3A//qzs.qq.com/qzone/v6/reg/index.html&pt_qr_help_link=http%3A//z.qzone.com/download.html";
	
	//20180730 更新
	String $qqa="https://xui.ptlogin2.qq.com/cgi-bin/xlogin?proxy_url=https%3A//qzs.qq.com/qzone/v6/portal/proxy.html&daid=5&&hide_title_bar=1&low_login=0&qlogin_auto_login=1&no_verifyimg=1&link_target=blank&appid=549000912&style=22&target=self&s_url=https%3A%2F%2Fqzs.qzone.qq.com%2Fqzone%2Fv5%2Floginsucc.html%3Fpara%3Dizone&pt_qr_app=%E6%89%8B%E6%9C%BAQQ%E7%A9%BA%E9%97%B4&pt_qr_link=http%3A//z.qzone.com/download.html&self_regurl=https%3A//qzs.qq.com/qzone/v6/reg/index.html&pt_qr_help_link=http%3A//z.qzone.com/download.html&pt_no_auth=0";

	
	//qq邮箱
	String emailu1="https%3A%2F%2Fmail.qq.com%2Fcgi-bin%2Flogin%3Fvt%3Dpassport%26vm%3Dwpt%26ft%3Dloginpage%26target%3D";
	
	//qq邮箱
	String emailu2="https%3A%2F%2Fmail.qq.com%2Fcgi-bin%2Flogin%3Fvt%3Dpassport%26vm%3Dwpt%26ft%3Dloginpage%26target%3D"+"&account=";
	
	String $emaila = "https://xui.ptlogin2.qq.com/cgi-bin/xlogin?appid=522005705&daid=4&s_url=https%3A//mail.qq.com/cgi-bin/login?vt=passport%26vm=wpt%26ft=loginpage%26target=&style=25&low_login=1&proxy_url=https%3A//mail.qq.com/proxy.html&need_qr=0&hide_border=1&border_radius=0&self_regurl=http%3A//zc.qq.com/chs/index.html?type=1&app_id=11005?t=regist&pt_feedback_link=http%3A//support.qq.com/discuss/350_1.shtml&css=https%3A//res.mail.qq.com/zh_CN/htmledition/style/ptlogin_input24e6b9.css";
	 WebClient client = new WebClient(BrowserVersion.INTERNET_EXPLORER_8);
	private void initClient() {
		client.getOptions().setThrowExceptionOnScriptError(false);// js错误是否直接抛出
		client.getOptions().setJavaScriptEnabled(false);// 是否加载js
		client.getOptions().setUseInsecureSSL(true);
		client.getOptions().setCssEnabled(false);// 是否加载css
		DefaultHttpParams.getDefaultParams().setBooleanParameter(HttpMethodParams.SINGLE_COOKIE_HEADER, true);
	}
	public QQ(){initClient();}
	//空间登录
	public QQ(String uin,String password){
		this.uin = uin;
		this.password = password;
		initClient();
	}
	//邮箱登录
	public QQ(String uin,String password,String type){
		this.uin = uin;
		this.password = password;
		if(type.equals("email")){
			$qqa=$emaila;
			u1=emailu1;
			u2=emailu2+uin;
		}
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
        //http://ui.ptlogin2.qq.com/cgi-bin/login?hide_title_bar=1&low_login=0&qlogin_auto_login=1&no_verifyimg=1&link_target=blank&appid=549000912&style=12&target=self&s_url=http%3A//qzs.qq.com/qzone/v5/loginsucc.html?para=izone&pt_qr_app=%CA%D6%BB%FAQQ%BF%D5%BC%E4&pt_qr_link=http%3A//z.qzone.com/download.html&self_regurl=http%3A//qzs.qq.com/qzone/v6/reg/index.html&pt_qr_help_link=http%3A//z.qzone.com/download.html
		String $v = this.requestGet($qqa);
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
        //http://check.ptlogin2.qq.com/check?regmaster=&pt_tea=1&uin=1098622525&appid=549000912&js_ver=10152&js_type=1&login_sig=puBm08m2LHEGW3B8O08sRx9eZCbXHv1QXAL8HBCXp5i6HopB*HphNGFetWjYT917&u1=http%3A%2F%2Fqzs.qq.com%2Fqzone%2Fv5%2Floginsucc.html%3Fpara%3Dizone&r=0.3449928464833647
//       String checkUrl="http://check.ptlogin2.qq.com/check?regmaster=&pt_tea=1&uin={0}&appid={1}&js_ver=10152&js_type=1&login_sig={2}&u1=http%3A%2F%2Fqzs.qq.com%2Fqzone%2Fv5%2Floginsucc.html%3Fpara%3Dizone&r=0.3449928464833647";
//        checkUrl=Util.fillString(checkUrl,$uin, $aid,$login_sig);
//       String $checkResult = this.requestGet(checkUrl);
        String $action = "3-6-"+new Date().getTime();
        //3.获取登录验证信息
//        http://check.ptlogin2.qq.com/check?regmaster=&uin=949102845&appid=549000912&js_ver=10051&js_type=1&login_sig=UcU**IJ7*Tb1oqFs9-NzQ7p187P4QhmPafwtJz5JE4zMXU1mnab0L5Z6uhEeSR4d&u1=http%3A%2F%2Fqzs.qq.com%2Fqzone%2Fv5%2Floginsucc.html%3Fpara%3Dizone&r=0.6652378559988166
        String $url = "http://ssl.ptlogin2.qq.com/check?regmaster=&pt_tea=2&pt_vcode=1&uin={0}&appid={1}&js_ver={2}&js_type=1&login_sig={3}&u1="+u3+"&r={4}";
        $url = Util.fillString($url, $uin, $aid,js_ver,$login_sig,new Random().nextDouble()+"");
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
		//http://ptlogin2.qq.com/login?u=482000254&p=pzr5X1yrhkdPqdd*kH6BrqgoandReWY46l1Cv8EZcKpo2*Qy-wy8mYtjIwX7Br-WaJrJ*QRDLmg*0hQGHahsMBVSEZBIb6bZRnp-mU*7Bzw68uABGgXd30ECRIO0sqjI4Jx7NeNn4C8WQA2Z7ENaktxhH1OMmJMsIr4A9R4DHSiGOxtcpdiyIkaGkLdSTSrI3yW*IhPKlzbKN5wKtQylPg__&verifycode=hfnh&aid=549000912&u1=http%3A%2F%2Fqzs.qq.com%2Fqzone%2Fv5%2Floginsucc.html%3Fpara%3Dizone&h=1&ptredirect=0&ptlang=2052&from_ui=1&dumy=&low_login_enable=0&regmaster=&fp=loginerroralert&action=3-6-1463120700193&mibao_css=&t=1&g=1&js_ver=10152&js_type=1&login_sig=yLZm8Gum2anSm2904z1Kme9-LWmdpSD0s5BhC7TP5H-GSm2Sgk1d3w3eB35COXMg&pt_uistyle=32&pt_randsalt=0&pt_vcode_v1=0&pt_verifysession_v1=&daid=5

		//http://ptlogin2.qq.com/login?u=1098622525&verifycode=!WBB&pt_vcode_v1=0&pt_verifysession_v1=e4739ebdb0f047b431fcbbb6a13cf5566a321202f84450f2baf86d39cfa144365da44b3b84d77fe5e7239893f99c17e4e80d01d0d1083c59&p=7Ijkrg-fzSGTaYNwD0mZD3NneqsaHLp3VkyRBwpSoHyE25-P8QtboZpTSVDynNInH*DEzVbEZzobHzeo7HTe0IkswiHA3dypBc3lAUfF-NRiZb3vZJN-CTIIRi0E*fxcItsvZ1h4pFMIQTU5eBjtsAp1DNukW*D7rxLBnmEyracL1C6Lk9AA6tOzjMntokuQIeb-E6D7naNaK-ZRXwDTig__&pt_randsalt=0&u1=http%3A%2F%2Fqzs.qq.com%2Fqzone%2Fv5%2Floginsucc.html%3Fpara%3Dizone&ptredirect=0&h=1&t=1&g=1&from_ui=1&ptlang=2052&action=2-32-1458810327585&js_ver=10152&js_type=1&login_sig=V2EADoOsy2aIYGbDsom4MRj3CCe9Rny0dWuTqG7OuEoZV7luLUnGUT7obPGIGYK-&pt_uistyle=32&aid=549000912&
		   $url = "https://ssl.ptlogin2.qq.com/login?u={0}&p={1}&verifycode={2}&aid={3}&u1="+u3+"&h=1&ptredirect=0&ptlang=2052&from_ui=1&dumy=&low_login_enable=0&regmaster=&fp=loginerroralert&action={4}&mibao_css=&t=1&g=1&js_ver={5}&js_type=1&login_sig={6}&pt_uistyle=40&pt_randsalt=2&pt_vcode_v1=0&pt_verifysession_v1={7}&daid=5&has_onekey=1&pt_jstoken={8}";
	        $url = Util.fillString($url, $uin, $password, $verify, $aid, $action,js_ver, $login_sig,$verifysession_v1,"3436456284");
        System.out.println($url);
    	$v = this.requestGet($url);
    	
    	$status = RegexUtil.replaceStartEnd($v, "'", "'");
    	System.out.println($v);//打印看下是否搞定了
    	
    	String loginSuccess=RegexUtil.replaceStartEnd($v, "','0','", "'");
    	
    	if(loginSuccess.contains("提交参数错误")){
    		System.out.println("登录失败.........");
    		return -1;
    	}
    	if(loginSuccess.contains("您输入的帐号或密码不正确，请重新输入。")){
    		System.out.println("您输入的帐号或密码不正确，请重新输入。");
    		return -1;
    	}
    	//获取g_tk
    	webClient(loginSuccess);
		//6.设置登录状态
		if("0".equals($status))
    		this.status = 0;
    	else
    		this.status = -1;
		
		return this.status;
	}
	
	public  void webClient(String url) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		client.getPage(url);
		Set<Cookie> cookies = client.getCookieManager().getCookies();
		//5.矫正QQ的Cookie，只提取关键需要Cookie
		String $v = "uin=o0"+this.uin+"; "+("skey="+RegexUtil.replaceStartEnd(this.cookie, "skey=", ";")+"; ptcz="+RegexUtil.replaceStartEnd(this.cookie, "ptcz=", ";")+";");
//		this.setCookie($v);
		for (Cookie cookie : cookies) {
			System.out.println(cookie.getName()+"="+cookie.getValue());
			if(cookie.getName().equals("p_skey")){
				this.g_tk = Security.GetG_TK(cookie.getValue());
				$v="p_skey="+cookie.getValue()+"; "+$v;
			}
		}
		this.setCookie($v);
	}
	//qq点赞
	public String parseQQ(String qq) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		String like_app="https://h5.qzone.qq.com/proxy/domain/taotao.qq.com/cgi-bin/emotion_cgi_msglist_v6?uin={0}&ftype=0&sort=0&pos=0&num=20&replynum=100&g_tk={1}&callback=_preloadCallback&code_version=1&format=jsonp&need_private_comment=1";
		like_app=Util.fillString(like_app, qq,this.g_tk);
		TextPage page = client.getPage(like_app);
		System.out.println(page.getContent());
		String s=RegexUtil.fecth(page.getContent(),"tid\":\"(.*?)\"",1);
		if(StringUtils.isBlank(s)){
			s=RegexUtil.substringMiddle(page.getContent(),"/mood\\/","^");
			s=s.replace("mood\\/","");
			System.out.println(s);
			if(StringUtils.isBlank(s)){
				return "获取赞id失败";
			}
			if(s.contains("对不起,主人设置了保密,您没有权限查看")){
				System.out.println("对不起,主人设置了保密,您没有权限查看");
				return "对不起,主人设置了保密,您没有权限查看";
			}
			if(s.contains("preloadCallback")){
				System.out.println("获取赞id失败");
				return "获取赞id失败";
			}
		}else{
			s=s+".1";
			s=s.replace("\"tid\":\"","");
			System.out.println("tid:"+s);
		}
		return parseQQ(qq, s);
	}
		
	//qq点赞
	public String parseQQ(String qq,String parseId){
		String internal_dolike_app="http://w.qzone.qq.com/cgi-bin/likes/internal_dolike_app?g_tk="+this.g_tk;
		String abstime=System.currentTimeMillis()+"";
		String p="qzreferrer=http%3A%2F%2Fuser.qzone.qq.com%2F{0}&opuin={0}&unikey=http%3A%2F%2Fuser.qzone.qq.com%2F{1}%2Fmood%2F{2}&curkey=http%3A%2F%2Fuser.qzone.qq.com%2F{1}%2Fmood%2F{2}&from=1&appid=311&typeid=0&abstime={3}&fid={2}&active=0&fupdate=1";
		p=Util.fillString(p, this.uin,qq,parseId,abstime);
		String requestPost = this.requestPost(internal_dolike_app,p);
		if(requestPost.contains("code\":0")){
			System.out.println("qq点赞成功........"+qq+"=====parseId==="+parseId);
			return "qq点赞成功........"+qq+"=====parseId==="+parseId;
		}else{
			System.out.println(requestPost);
		}
		return requestPost;
	}
	//qq留言
	public String writeQQ(String qq,String content){
		String internal_dolike_app="https://h5.qzone.qq.com/proxy/domain/m.qzone.qq.com/cgi-bin/new/add_msgb?qzonetoken=bed86fdfc446c1964296e45d7b5bedb147e50a21b20df9b5102a162e31a3f3ce47ee6b929798e3b74bab&g_tk="+this.g_tk;
		String abstime=System.currentTimeMillis()+"";
		String p="qzreferrer=http%3A%2F%2Fctc.qzs.qq.com%2Fqzone%2Fmsgboard%2Fmsgbcanvas.html%23page%3D1&content={0}&hostUin={2}&uin={1}&format=fs&inCharset=utf-8&outCharset=utf-8&iNotice=1&ref=qzone&json=1&g_tk="+this.g_tk;
//		String p="qzreferrer=http%3A%2F%2Fuser.qzone.qq.com%2F{0}&opuin={0}&unikey=http%3A%2F%2Fuser.qzone.qq.com%2F{1}%2Fmood%2F{2}&curkey=http%3A%2F%2Fuser.qzone.qq.com%2F{1}%2Fmood%2F{2}&from=1&appid=311&typeid=0&abstime={3}&fid={2}&active=0&fupdate=1";
		p=Util.fillString(p,content, this.uin,qq,abstime);
		String requestPost = this.requestPost(internal_dolike_app,p);
		if(requestPost.contains("留言成功")){
			System.out.println("qq留言成功........"+qq);
			return "qq留言成功........"+qq;
		}
		return "qq留言失败";
	}
	
	//qq首页 赞
	public String indexParseQQ(String qq){
		String internal_dolike_app="http://w.qzone.qq.com/cgi-bin/likes/internal_dolike_app?g_tk="+this.g_tk;
		String p="qzreferrer=http%3A%2F%2Fuser.qzone.qq.com%2F{1}%2Fmain&appid=7030&face=0&fupdate=1&from=1&query_count=200&opuin={0}&unikey=http%3A%2F%2Fuser.qzone.qq.com%2F{1}&curkey=http%3A%2F%2Fuser.qzone.qq.com%2F{1}&zb_url=http%3A%2F%2Fi.gtimg.cn%2Fqzone%2Fspace_item%2Fpre%2F9%2F64073_1.gif"+this.g_tk;
		p=Util.fillString(p,this.uin,qq);
		String requestPost = this.requestPost(internal_dolike_app,p);
		if(requestPost.contains("code: 0")){
			System.out.println("qq首页 赞成功........"+qq);
			return "qq首页 赞成功........"+qq;
		}
		return "qq首页 赞失败";
	}
	//自动添加好友
	public String addFread(String qq){
		String addFread="http://w.qzone.qq.com/cgi-bin/tfriend/friend_addfriend.cgi?g_tk="+this.g_tk;
		String p="qzreferrer=http%3A%2F%2Fuser.qzone.qq.com%2F{0}%3Fptlang%3D2052&sid=0&ouin={0}&uin={1}&fuin={0}&fupdate=1&rd={2}&strmsg=&groupId=7&realname=&flag=0&chat=&key=&im=0&from=8&from_source=8";
		p=Util.fillString(p,qq,this.uin,Math.random()+"");
		String requestPost = this.requestPost(addFread,p);
		if(requestPost.contains("code: 0")){
			System.out.println("自动添加好友成功........"+qq);
			return "自动添加好友成功........"+qq;
		}
		return "添加失败,"+RegexUtil.replaceStartEnd(requestPost, "message\":\"", "\",");
	}
	//发说说
	public boolean send(String content){
		String send="http://taotao.qzone.qq.com/cgi-bin/emotion_cgi_publish_v6?g_tk="+this.g_tk;
		String p="qzreferrer=http%3A%2F%2Fuser.qzone.qq.com%2F{0}%2Finfocenter%3Fptsig%3D5WBEVNvrmtiTNvTW8Vo*XlmO-yWsUo*l-7EQSCCilvE_&syn_tweet_verson=1&paramstr=1&pic_template=&richtype=&richval=&special_url=&subrichtype=&who=1&con={1}&feedversion=1&ver=1&ugc_right=1&to_tweet=0&to_sign=0&hostuin={0}&code_version=1&format=fs";
		p=Util.fillString(p,this.uin,content);
		String requestPost = this.requestPost(send,p);
		if(requestPost.contains("code: 0")){
			System.out.println("发说说成功........");
			return true;
		}
		return false;
	}
	
	
	//相册访问列表
	public boolean vistorPhoto(String qq){
		String vistorPhoto="http://g.qzone.qq.com/cgi-bin/friendshow/cgi_get_visitor_simple?uin="+qq+"&mask=2&mod=2&fupdate=1&g_tk="+this.g_tk;
		String requestPost = this.requestGet(vistorPhoto);
		if(requestPost.contains("code: 0")){
			System.out.println("相册访问列表成功........"+qq);
			return true;
		}
		return false;
	}
	
	//最近访问
	public boolean vistor(String qq){
		String vistor="http://user.qzone.qq.com/p/r/cgi-bin/main_page_cgi?uin={0}&param=3_{0}_0%7C8_8_{1}_0_1_0_0_1%7C15%7C16&g_tk="+this.g_tk;
		vistor=Util.fillString(vistor,qq,this.uin);
		String requestPost = this.requestPost(vistor,"");
		if(requestPost.contains("code: 0")){
			System.out.println("访问成功........"+qq);
			return true;
		}
		return false;
	}
	
	
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
	
	
}
