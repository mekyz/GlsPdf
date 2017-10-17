package test;



import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;




public class TestDemo {
	

    public static void main(String[] args) throws JSONException {
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            public void run() {
//                System.out.println("-------设定要指定任务--------");
//            }
//        }, 2000);// 设定指定的时间time,此处为2000毫秒
//        json
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("userAcont","123");
        System.out.println(jsonObject);
    }
}
