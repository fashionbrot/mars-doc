package com.github.fashionbrot.doc.controller;


import com.github.fashionbrot.doc.SpringDocConfigurationProperties;
import com.github.fashionbrot.doc.RespVo;
import com.github.fashionbrot.doc.annotation.ApiIgnore;
import com.github.fashionbrot.doc.cookie.ResponseCookie;
import com.github.fashionbrot.doc.cookie.SameSiteEnum;
import com.github.fashionbrot.doc.event.DocApplicationListener;
import com.github.fashionbrot.doc.req.MarsApiReq;
import com.github.fashionbrot.doc.util.ObjectUtil;
import com.github.fashionbrot.doc.vo.DocVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author fashionbrot
 */
@ApiIgnore
@Controller
public class MarsDocController implements BeanFactoryAware {

    private SpringDocConfigurationProperties properties;

    private HttpServletRequest request;
    private HttpServletResponse response;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public MarsDocController(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    private final static String COOKIE_USERNAME = "mars-doc-username";
    private final static String COOKIE_PASSWORD = "mars-doc-password";

    /**
     * 获取接口文档
     * @param req 参数
     * @return RespVo
     */
    @ResponseBody
    @RequestMapping("/mars/api")
    public RespVo<List<DocVo>> marsApi(MarsApiReq req){

        if (ObjectUtil.isEmpty(properties.getUsername()) && ObjectUtil.isEmpty(properties.getPassword())) {
            return RespVo.success(DocApplicationListener.getDocVoList());
        }else{
            if (ObjectUtil.isEmpty(req.getUsername())){
                req.setUsername(getCookieValue(COOKIE_USERNAME));
            }
            if (ObjectUtil.isEmpty(req.getPassword())){
                req.setPassword(getCookieValue(COOKIE_PASSWORD));
            }

            if (properties.getUsername().equals(req.getUsername()) && properties.getPassword().equals(req.getPassword())){

                if (req.getRememberDay()==null){
                    req.setRememberDay(45*60);
                }else{
                    req.setRememberDay(req.getRememberDay()*24*60);
                }
                writeCookie(req.getUsername(),req.getPassword(),req.getRememberDay());

                return RespVo.success(DocApplicationListener.getDocVoList());
            }else{
                return RespVo.fail("请登录",-10);
            }
        }
    }

    private void writeCookie(String username, String password, Integer rememberDay) {
        response.addHeader(ResponseCookie.SET_COOKIE,ResponseCookie.builder()
                .domain(request.getServerName())
                .httpOnly(true)
                .maxAge(Duration.ofSeconds(rememberDay))
                .path("/")
                .sameSite(SameSiteEnum.STRICT)
                .secure(true)
                .name(COOKIE_USERNAME)
                .value(username)
                .build().toString());
        response.addHeader(ResponseCookie.SET_COOKIE,ResponseCookie.builder()
                .domain(request.getServerName())
                .httpOnly(true)
                .maxAge(Duration.ofSeconds(rememberDay))
                .path("/")
                .sameSite(SameSiteEnum.STRICT)
                .secure(true)
                .name(COOKIE_PASSWORD)
                .value(password)
                .build().toString());
    }

    private String getCookieValue(String name){
        Cookie[] cookies = request.getCookies();
        if (ObjectUtil.isNotEmpty(cookies)){
            Optional<Cookie> first = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals(name)).findFirst();
            if (first.isPresent() ){
                Cookie cookie = first.get();
                if (cookie!=null){
                    return cookie.getValue();
                }
            }
        }
        return "";
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        SingletonBeanRegistry beanRegistry = null;
        if (beanFactory instanceof SingletonBeanRegistry) {
            beanRegistry = (SingletonBeanRegistry) beanFactory;
        } else if (beanFactory instanceof AbstractApplicationContext) {
            beanRegistry = ((AbstractApplicationContext) beanFactory).getBeanFactory();
        }
        if (beanFactory.containsBean(SpringDocConfigurationProperties.BEAN_NAME)) {
            properties = (SpringDocConfigurationProperties) beanRegistry.getSingleton(SpringDocConfigurationProperties.BEAN_NAME);
        }
    }
}
