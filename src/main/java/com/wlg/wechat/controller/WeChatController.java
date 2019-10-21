package com.wlg.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.wlg.wechat.utils.HttpClientUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@RequestMapping("/wechat")
public class WeChatController {

    //需修改为自己的app环境
    public String GETTOKEN = "" +
            "https://api.weixin.qq.com/sns/oauth2/access_token?appid=应用ID&secret=应用密钥&code=CODE&grant_type=authorization_code";

    public String USERINFO = "" +
            "https://api.weixin.qq.com/sns/userinfo?access_token=TOKEN&openid=OPENID";


    /**
     * 使用a标签进行登录
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("login");
    }

    /**
     * 使用微信通过的js创建二维码登录
     * @return
     */
    @RequestMapping("/login2")
    public ModelAndView login2(){
        return new ModelAndView("login2");
    }

    /**
     * 用户扫描确认登录后的回调执行，此处应对应redirect_uri
     * 注意：要保证回调域名和当前网络环境通畅，如果想要本地测试，
     * 可以修改hosts文件映射一下本地和回调域名，保证回调是调的本地这个方法↓↓↓↓
     * @param code
     * @param state
     * @return
     */
    @RequestMapping("/callBack")
    public ModelAndView callBack(String code, String state){
        //回调获得code，通过用户授权的code去获取微信令牌
        String token = HttpClientUtil.get(GETTOKEN.replaceAll("CODE", code));
        Map map = JSON.parseObject(token);
        //获取到了关键的令牌和openid后，
        //就可以正式开始查询微信用户的信息，完成我们要做的微信绑定
        String access_token = (String) map.get("access_token");
        String openid = (String) map.get("openid");
        String userInfo = HttpClientUtil.get(USERINFO.replaceAll("TOKEN", access_token).replaceAll("OPENID", openid));
        Map info = JSON.parseObject(userInfo);
        return new ModelAndView("personal","userInfo",info);
    }
}
