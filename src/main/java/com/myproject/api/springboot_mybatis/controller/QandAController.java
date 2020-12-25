package com.myproject.api.springboot_mybatis.controller;

import com.myproject.api.springboot_mybatis.entity.Question;
import com.myproject.api.springboot_mybatis.serviceImpl.QandAService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.Null;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.myproject.api.springboot_mybatis.controller.QandAController.o1;

@RestController
public class QandAController {

    @Autowired
    private QandAService qandAService;

    static Object o1 = new Object();
    String result = null;
    @RequestMapping(value="/QandA/Answering")
    public String QandA(@Param("data") String question) throws IOException, InterruptedException, JSONException {
            String exe = "python";
            String command = "/root/AnsPy/AnsSys.py";
            String cmdArr = exe + ' '+command +' '+question;
            System.out.println(cmdArr);
        JSONObject object = new JSONObject();
            Process process = Runtime.getRuntime().exec(cmdArr);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            new Thread(){
                public void run(){
                    synchronized (o1){
                        System.out.println("Waiting for ans...");
                        String str = null;
                        while(true){
                            try {
                                if (!((str = in.readLine())==null)) break;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println(str);
                        result = str;
                        o1.notify();
                    }
                }
            }.start();
            synchronized (o1){
                try{
                    System.out.println("Waiting for process to complete...");
                    o1.wait();
                    System.out.println("Complete!");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
//            InputStream is = process.getInputStream();
//            DataInputStream dis = new DataInputStream(is);
//            String str = dis.readLine();
            in.close();
            process.waitFor();
            System.out.println(result);
            object.put("answer",result);
            return object.toString();
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/backend")
    public List<Map> ReadAllQusetion() throws JSONException {
        List<Question> questionList = qandAService.readAllQuestion();
        List<Map> mapList = new ArrayList<>();
        for(Question q : questionList){
            Map<String,Object> resultMap  = new HashMap<String, Object>();
            resultMap.put("id",q.getId());
            resultMap.put("question_pre",q.getQuestion_pre());
            resultMap.put("q_word",q.getQ_word());
            resultMap.put("question_aft",q.getQuestion_aft());
            mapList.add(resultMap);
        }
        return mapList;
    }

}

class QandAThread extends Thread{
    public void run(){
        synchronized (o1){
            System.out.println("Waiting for ans...");

        }
    }
}
