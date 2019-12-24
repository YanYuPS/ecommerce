package util.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

//@Component
public class TestUtil {

    public static void main(String[] args) {
//        ApplicationContext context=new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml");
//        TestUtil testUtil=  (TestUtil) context.getBean("testUtil");

        TestUtil testUtil=new TestUtil();


        //replace正则替换
        testUtil.reg();
    }


    //replace正则替换
    public void reg(){
        //例子
        String regex = "(wqnmlgb|cnm|dsb|rnmb|sb|db|djb)";
        String message = "wqnmlgb!你怎么这么mei!com!你真shuai";
        message = message.replaceAll(regex, "***");
        System.out.println(message);

        //自学
        String brandIds = "[{\"id\":36,\"text\":\"大小\"},{\"id\":36,\"text\":\"大小\"}]"; //[{"id":36,"text":"大小"},{"id":37,"text":"大小"}]
        //+或*后跟？表示非贪婪匹配，即尽可能少的匹配
        String reg="\\{\"id\":" + 36 + ",\"text\":\"(.+?)\"\\}";
//        System.out.println(reg);
        brandIds=brandIds.replaceAll(reg,"{\"id\":"+ 20 +",\"text\":\""+ "容量" +"\"}");
        System.out.println(brandIds);


    }


}
