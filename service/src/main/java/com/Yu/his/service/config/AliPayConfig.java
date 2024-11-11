package com.Yu.his.service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration

@ConfigurationProperties(prefix = "alipay")
public class AliPayConfig {

    // 应用ID,APPID，收款账号既是APPID对应支付宝账号
    public static String appId = "9021000132603149";

    // 商户私钥，PKCS8格式RSA2私钥  刚刚生成的私钥直接复制填写
    public static String merchantPrivateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCOoN2ZFAlTFbbu6NEsMeUGM9ISQh9rrPQBDMD0ghme5R1Uu8bko7XAY89wLG7adYRD5dgyR8/dMwTzRUPk/WBtWp+bmIo/YqsszbBxD7I13dC/X0MCykRTEZSZ219isbKVZf86jjipd0qxXTHI6tDWAizBanjRz1nCv4uXchXHA+hJYiyBid6c+DqReXrz2NFBy7ToRmpmIDJZMgpKwCiA4P/vigwF0GqtQOlTcBpOZ31t9/bAFQ75y/+yEyO36yUlt5U0SH0o6paZNCwyYDNE1OaFOe6zxXnXkPqoW2c+G5yBZVv2sFR/3QI6/VOk++pF9hRZez+auDwGT0A40lVvAgMBAAECggEAESoc4QI/SQdw93l/StFKXZ+45Ued3mh3CPVO91381Vl/WSYr/nIROnEIkthWEuZXn5A2n35y7ozoSJqKXDF9/EsOsDwATzaBoSez1zdrW/4w1AcT2m8bH+AawRVcelUmR8J5n8Nj4Ekh7QxpJGPV9BtY5YH5vk404DILC5PIOSX56aEVvl2YJ/O17be/tMHKa4HQc1tsOMNSMdYhq7m9QNkSwNyHZ1xtpYy4k/sPr5Cs6n1ajS5Bx0ySFtk2OUkmxQ9AgzkTZdXBCYoP/krlPMcwpD/BiEW8V/llFzWFkz4QFOvAbJwtMmxfizDTgrL5irfXm9fXcZimA2OCdpqRwQKBgQDXSBxMdUAkQ9hUXncul0bvxukCAaVJRFI9CThCIrFgtqUAObznQO1+OItMlpqpaa10nXuqrImiT/PPtOgJ05ubnDtKD7PjMMxp7/rcnqdllaZnhsJPTQ3QrymyExwi8ecZK/b9xTD1sasCqJ+Gq/m9C7ICcqI43NXnxfx8LaKZ4QKBgQCpmuYknj4n4LU+miwn7UDscPplF6VUik2eanvTliTBaLfwMncWbQ8qEBOCruNosY93O5b6CVOQk7JLujJelxy86XbrV674VDKYUPdOM8c68zuxa5RroQlfCDaPrg7Ygr9DMkBlQe86f1XNFJw6X4dxf2wlQdjB4hKCmctalRn5TwKBgCEzSsHz1yeAmw0v4Ft8EwGJgIfBrzbkZXZqRJXWedp7cGjffqV/WuD7YWg3VbTIympQmzDQGQ3CHMysjphtXPMbnf0m/MwNy4iO7PY3NYOyF46htre6H2ZOjTScQ9uTyNsV76plTc6mTsRDMw722LXx5sr/2MrCK+7plq2j8A6hAoGAQG/UTkc8sCPkWEN00TqHdw05ZJ0GOEdPVAd6whmCH2UstPpHWmLW5xEyiGzvg0AqlI80Pgy7Deu/SpwUNPHOfkto3eLzW++JRWtFehWb9N6iaDbKLdYB3HQfXpkvlLyf8+SsqIvTWvMz4vWYoWjX6ZwSt1j8BDVi+PF7XA/XzWsCgYAw5ETDEmT1skKx+2DI2hdoXiK0ZYwUJ4JmTmSOHlFGAIwZkVH3Kr+ONqBXJwbd0/qY2ST59z3D6fybZyhcemIFW95NZMFiZYAcPGXL/YdPgsOqYSY9f8DWPBAupjU9nFutIK+6Yer4zzSTaFBbQAmlA/U1PtGskhXZUyxtO0gPBQ==";

    // 支付宝公钥,对应APPID下的支付宝公钥，别填成商户公钥
    public static String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjd/BMIzojT7yr7Y0bFIbXNM3T7DRQLOKvk/Z+pOh1KsEuoQyx1xGeHO/vRbIS/TfQvWJDa5q6jfkTwnyIykWy7q+yazbfJ9SSW+tgXR1S7cUw+rAVUQFNIrgem/nzsRQVSf4IGR/CsoC2yvIiQD0bJhAo9PIgwJd+WLE8GIzBdFeMeg2+OldKywEqhHo7Bws6MFMKyW5r9P+laSYANkEItcjSio37Snc2FePf+1ZsNM+X2XkCMjhI8807si9fBqNzXQrvX6RGIakFcfpv8vkp3ZAOZ/pClEeYj7zy5f613OmVruIw3As7N99YAUrcuPT8txMKPtcHr1yiPqnZ3Yi6wIDAQAB";
    // 服务器异步通知页面路径  需http://格式的完整路径，其实就是支付完成后返回的页面URL
//    public static String notify_url = neturl+"/alipay/notify_url";
    public static String notifyUrl = "http://ajykfw.natappfree.cc/api/alipay/notify";
    // 页面跳转同步通知页面路径 需http://格式的完整路径，其实就是你的一个支付完成后返回的页面URL
//    public static String return_url = neturl+"/alipay/return_url";
    public static String returnUrl = "localhost:7600";
    // 签名方式
    public static String signType = "RSA2";
    // 字符编码格式
    public static String charset = "UTF-8";
    // 支付宝网关
    public static String gatewayUrl = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
}