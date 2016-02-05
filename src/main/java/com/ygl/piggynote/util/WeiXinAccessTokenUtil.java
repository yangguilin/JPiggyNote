package com.ygl.piggynote.util;

import com.google.gson.Gson;
import com.ygl.piggynote.bean.WeiXinAccessTokenBean;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yanggavin on 15/6/24.
 */
public final class WeiXinAccessTokenUtil {
    private static final String APP_ID = "wxa40036beeec512b0";
    private static final String SECRET = "d02bea35d66e2d2c9939604b06f8c6f4";
    private static final int ACCESS_TOKEN_EXPIRE_SECONDS = 7200;
    private static Date _getAccessTokenLatestTime = null;
    private static String _accessToken = "";

    public static String getAccessToken(){
        if (_accessToken.isEmpty() || checkAccessTokenExpireStatus()){
            try {
                _accessToken = getAccessTokenFromServer();
                _getAccessTokenLatestTime = new Date();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return _accessToken;
    }

    private static String getAccessTokenFromServer() throws IOException {
        String accessToken = "";
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APP_ID + "&secret=" + SECRET;
        WeiXinAccessTokenBean bean = null;
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String result = EntityUtils.toString(response.getEntity());
            Gson gson = new Gson();
            bean = gson.fromJson(result, WeiXinAccessTokenBean.class);
        }
        if (!bean.getAccess_token().isEmpty() && !bean.getExpires_in().isEmpty()){
            accessToken = bean.getAccess_token();
        }
        return accessToken;
    }

    public static Boolean checkAccessTokenExpireStatus(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(_getAccessTokenLatestTime);
        cal.add(Calendar.SECOND, ACCESS_TOKEN_EXPIRE_SECONDS);
        Date now = new Date();
        return (cal.getTime().getTime() < now.getTime());
    }
}
