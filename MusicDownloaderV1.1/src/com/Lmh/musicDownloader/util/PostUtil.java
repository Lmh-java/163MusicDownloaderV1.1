package com.Lmh.musicDownloader.util;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
//import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.Lmh.musicDownloader.model.Music;
import com.alibaba.fastjson.*;

import sun.misc.BASE64Encoder;

public class PostUtil {

	// api
	// api
	public static String api(String a,String cookie) throws Exception {

		String result = null;
/*		String myCookie ="_iuqxldmzr_=32;"
				+ " _ntes_nnid=52d7b41e35242e8a57a15eddcdf08c5b,1530937551168;"
				+ " _ntes_nuid=52d7b41e35242e8a57a15eddcdf08c5b; "
				+ "usertrack=ezq0o1tETxuG5HaFBTawAg==;"
				+ " __remember_me=true; "
				+ "UM_distinctid=165576137d0611-0fa495e9b92ddb-5e442e19-1fa400-165576137d121b; "
				+ "vjuids=-4a816575e.16557613926.0.ef984e28484d7;"
				+ " vjlast=1534769314.1534769314.30;"
				+ " vinfo_n_f_l_n3=2867847f4ecc21bc.1.0.1534769314101.0.1534769317821; "
				+ "mail_psc_fingerprint=dee5abc43a85df77c62223eca56915ff;"
				+ " __f_=1536457866164;"
				+ " P_INFO=flzsjs163@163.com|1536457751|2|other|00&99|gud&1536457366&mail163#gud&440200#10#0#0|&0|163&urs&mail163|flzsjs163@163.com;"
				+ " WM_TID=nB0KUYwThohoApTzsZtNI6otxKRXu5gO;"
				+ " __utmz=94650624.1537695595.20.7.utmcsr=baidu|utmccn=(organic)|utmcmd=organic;"
				+ " __utmc=94650624; "
				+ "JSESSIONID-WYYY=9q9NkX1ZlgvlWsEWRlC%2BiggY0Y2hQ3wMCCwGKgN0iqR5scgAddnQWwCk7%5C%2B%2BGobFnGmtalVZDmuChhOhi%2FFfKkjRCWc%2FN6FANH%5C5jvlU7NbTSHd8FxfODJy0K%5CcWJl2UbQH4deauZAt3XBQBagJoZ2i%5CVPsipAwl%2BiurhpSl45OEj9Y0%3A1537707706207;"
				+ " __utma=94650624.1657657276.1534810628.1537697464.1537705907.22;"
				+ " WM_NI=LdQ0VsNUekQFS%2B5EFhLn7NmFmUm%2BR2O1Z%2FlRMvLPbN2D0%2Fw%2FbZeP7vGhLh80NGhhW7d%2FavwRz1KBaXGelPmXvjYjP%2BtU4lCZi%2BpLN5s0U7ktm4pR1fOWxtMP13OqDL9RUEQ%3D; WM_NIKE=9ca17ae2e6ffcda170e2e6eeadd76baabda9b2f17bb4868fb7d44b878b9e84f76a968fc087e26d9c98bca9c72af0fea7c3b92a90bc9fd1d5798e8bab8cd44e91f5fba6c56490b2f9aff04eaf8ba3ccd13a8def96d0ef5a8faa84b1e146f29c00d0f625a286abb1b67e85b88489bb3c8e99a889d63da8f1af97f65abaef85a3e45485bba4b1f054b6b6978bd321b88bfc8dc252abf5c0adcd3fb1f09bd8ea80afecc0b3b46e8d89b895d05cf3ed8192c748fb8e9ba6d437e2a3; MUSIC_U=e8fdff1b8e197705132e621ef442e840e03e15d76a435e221ff57432fb962f31b077ae4a47fe04c430092cebd9fdaf28c3ff67a55407542887d0cfa1abf7041430462163724ab68cbf122d59fa1ed6a2;"
				+ " __csrf=7c8c959657fc0ee7f906a43143bf124c;"
				+ " __utmb=94650624.14.10.1537705907; "
				+ "playerid=31728339";
*/
//		String detail = a;
		a = a.split(":::")[0];
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		// json
		// String first_param = "{rid:\"\", offset:\"offset_param\",
		// total:\"true\", limit:\"20\", csrf_token:\"\"}";
		String first_param = "{ids:\"[" + a + "]\", br: \"320000\", csrf_token:\"\"}";
//		System.out.println(first_param);
		// first_param = first_param.replace("offset_param", offset + "");
		// 参数加密
		// 16位随机字符串，直接FFF
		// String secKey = new BigInteger(100, new
		// SecureRandom()).toString(32).substring(0, 16);
		String secKey = "FFFFFFFFFFFFFFFF";
		// 两遍ASE加密
		String encText = aesEncrypt(aesEncrypt(first_param, "0CoJUm6Qyw8W8jud"), secKey);
		String encSecKey = rsaEncrypt();
		HttpPost httpPost = new HttpPost(
				"http://music.163.com/weapi/song/enhance/player/url?csrf_token=7c8c959657fc0ee7f906a43143bf124c");
		httpPost.addHeader("Referer", "http://music.163.com/");
		httpPost.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
		httpPost.addHeader("Cookie",cookie);
		List<NameValuePair> ls = new ArrayList<NameValuePair>();
		ls.add(new BasicNameValuePair("params", encText));
		ls.add(new BasicNameValuePair("encSecKey", encSecKey));
		UrlEncodedFormEntity paramEntity = new UrlEncodedFormEntity(ls, "utf-8");
		httpPost.setEntity(paramEntity);
		response = httpclient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String json = EntityUtils.toString(entity, "utf-8").toString();
			// System.out.println(json);
			JSONObject jsStr = JSON.parseObject(json);
			String json1 = jsStr.getString("data").replace("[", "").replace("]", "");
			JSONObject jsStr1 = JSON.parseObject(json1);
//			System.out.println(jsStr1.getString("url"));
			// list2.add(detail+":::"+jsStr1.getString("url"));
			result = jsStr1.getString("url");
		}
		response.close();
		httpclient.close();
		return result;
	}

	/**
	 * ASE-128-CBC加密模式可以需要16位
	 *
	 * @param src
	 *            加密内容
	 * @param key
	 *            密钥
	 * @return
	 */
	public static String aesEncrypt(String src, String key) throws Exception {
		String encodingFormat = "UTF-8";
		String iv = "0102030405060708";
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] raw = key.getBytes();
		SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
		
		// 使用CBC模式，需要一个向量vi，增加加密算法强度
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
		byte[] encrypted = cipher.doFinal(src.getBytes(encodingFormat));
		return new BASE64Encoder().encode(encrypted);
		
	}

	public static String rsaEncrypt() {
		String secKey = "257348aecb5e556c066de214e531faadd1c55d814f9be95fd06d6bff9f4c7a41f831f6394d5a3fd2e3881736d94a02ca919d952872e7d0a50ebfa1769a7a62d512f5f1ca21aec60bc3819a9c3ffca5eca9a0dba6d6f7249b06f5965ecfff3695b54e1c28f3f624750ed39e7de08fc8493242e26dbc4484a01c76f739e135637c";
		return secKey;

	}
	
	public static ArrayList<Music> doPost(ArrayList<Music> name,String cookie) throws Exception{
		
		for(int x=0;x<name.size();x++){
			String url =api(name.get(x).getId(),cookie);
			name.get(x).setUrl(url);
		}
		
		return name;
	}

}
