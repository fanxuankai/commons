package com.fanxuankai.commons.util;

import cn.hutool.core.codec.Base64;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;

/**
 * 钉钉客户端助手
 *
 * @author fanxuankai
 */
public class DingTalkClientHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DingTalkClientHelper.class);
    private final DingTalkClient client;

    public DingTalkClientHelper(DingTalkClient client) {
        this.client = client;
    }

    /**
     * 发送机器人 text 类型消息
     *
     * @param content 消息文本
     */
    public void sendRobotText(String content) {
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("text");
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(content);
        request.setText(text);
        try {
            client.execute(request);
        } catch (ApiException e) {
            LOGGER.error("钉钉推送异常", e);
        }
    }

    /**
     * 发送机器人 markdown 类型消息
     *
     * @param title 首屏会话透出的展示内容
     * @param text  markdown 格式的消息内容
     */
    public void sendRobotMarkdown(String title, String text) {
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("markdown");
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setTitle(title);
        markdown.setText(text);
        request.setMarkdown(markdown);
        try {
            client.execute(request);
        } catch (ApiException e) {
            LOGGER.error("钉钉推送异常", e);
        }
    }

    /**
     * 发送机器人 link 类型消息
     *
     * @param title      首屏会话透出的展示内容
     * @param text       link 格式的消息内容
     * @param messageUrl 内容链接
     * @param picUrl     图片链接
     */
    public void sendRobotLink(String title, String text, String messageUrl, String picUrl) {
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("link");
        OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
        link.setTitle(title);
        link.setText(text);
        link.setMessageUrl(messageUrl);
        link.setPicUrl(picUrl);
        request.setLink(link);
        try {
            client.execute(request);
        } catch (ApiException e) {
            LOGGER.error("钉钉推送异常", e);
        }
    }

    /**
     * 发送机器人 feedCard 类型消息
     *
     * @param title      首屏会话透出的展示内容
     * @param messageUrl 内容链接
     * @param picUrl     图片链接
     */
    public void sendRobotFeedCard(String title, String messageUrl, String picUrl) {
        OapiRobotSendRequest.Links links = new OapiRobotSendRequest.Links();
        links.setTitle(title);
        links.setMessageURL(messageUrl);
        links.setPicURL(picUrl);
        sendRobotFeedCard(Collections.singletonList(links));
    }

    /**
     * 发送机器人 feedCard 类型消息
     *
     * @param links /
     */
    public void sendRobotFeedCard(List<OapiRobotSendRequest.Links> links) {
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("feedCard");
        OapiRobotSendRequest.Feedcard feedCard = new OapiRobotSendRequest.Feedcard();
        feedCard.setLinks(links);
        request.setFeedCard(feedCard);
        try {
            client.execute(request);
        } catch (ApiException e) {
            LOGGER.error("钉钉推送异常", e);
        }
    }

    public static DingTalkClientHelper newInstance(String url, String accessToken, String secret) {
        return new DingTalkClientHelper(dingTalkClient(url, accessToken, secret));
    }

    public static DingTalkClient dingTalkClient(String url, String accessToken, String secret) {
        return new DefaultDingTalkClient(serviceUrl(url, accessToken, secret));
    }

    public static String serviceUrl(String url, String accessToken, String secret) {
        String serviceUrl = url;
        serviceUrl += "?access_token=" + accessToken;
        if (StringUtils.hasText(secret)) {
            try {
                long timestamp = System.currentTimeMillis();
                String stringToSign = timestamp + "\n" + secret;
                Mac mac = Mac.getInstance("HmacSHA256");
                mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
                byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
                String sign = URLEncoder.encode(Base64.encode(signData), "UTF-8");
                serviceUrl += "&timestamp=" + timestamp;
                serviceUrl += "&sign=" + sign;
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException e) {
                LOGGER.error("钉钉客户端初始化异常", e);
            }
        }
        return serviceUrl;
    }
}
