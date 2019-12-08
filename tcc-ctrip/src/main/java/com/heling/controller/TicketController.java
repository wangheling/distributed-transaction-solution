package com.heling.controller;

import com.alibaba.fastjson.JSONObject;
import com.heling.HttpClientUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("ticket")
public class TicketController {

    @GetMapping("buy")
    public String buyTicket(@RequestParam("name") String name, @RequestParam("phone") String phone) throws Exception {

        //向川航订票
        String url = "http://localhost:8081/sichuan/airlines/ticket/try";
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("phone", phone);
        params.put("airLine", "B001");
        Map<String, Object> result = HttpClientUtil.doJsonPost(url, JSONObject.toJSONString(params));

        if (!(Boolean) result.get("trySuccess")) {
            return "fail";
        }

        //try成功
        String orderNo = (String) result.get("orderNo");


        //向东航订票
        String url2 = "http://localhost:8080/eastern/airlines/ticket/try";
        Map<String, String> params2 = new HashMap<>();
        params2.put("name", name);
        params2.put("phone", phone);
        params2.put("airLine", "A001");
        Map<String, Object> result2 = HttpClientUtil.doJsonPost(url2, JSONObject.toJSONString(params2));

        if (!(Boolean) result2.get("trySuccess")) {
            //东航try失败，则取消川航的预留机票
            HashMap<String, String> param = new HashMap<>(16);
            param.put("orderNo", orderNo);
            HttpClientUtil.doJsonPost("http://localhost:8081/sichuan/airlines/ticket/cancel", JSONObject.toJSONString(param));
            return "fail";
        }

        //东航try成功，confirm
        String orderNo2 = (String) result2.get("orderNo");

        Map<String, String> param = new HashMap<>(16);
        param.put("orderNo", orderNo);
        //confirm川航机票
        HttpClientUtil.doJsonPost("http://localhost:8081/sichuan/airlines/ticket/confirm", JSONObject.toJSONString(param));

        Map<String, String> param2 = new HashMap<String, String>();
        param2.put("orderNo", orderNo2);
        //confirm东航机票
        HttpClientUtil.doJsonPost("http://localhost:8080/eastern/airlines/ticket/confirm", JSONObject.toJSONString(param2));

        //todo:携程保留订票信息

        return "success";

    }
}
