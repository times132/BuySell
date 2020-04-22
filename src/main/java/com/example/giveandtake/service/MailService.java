package com.example.giveandtake.service;


import com.example.giveandtake.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Random;

@Service
public class MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Resource(name="mailSender")
    private JavaMailSender sender;



    //이메일 보내기
    public String sendMail(String email, HttpServletRequest request, String mailType) {
        String key = getKey(false, 6);
        String from = "yoo4380@gmail.com";
        String to = email; // 받는 사람 이메일
        String title ="";
        String content = "";
        HttpSession session = request.getSession();
        if(mailType == "join") {
            title = "[BUYSELL] 회원 인증 이메일 입니다."; // 제목
            content = System.getProperty("line.separator") + //한줄씩 줄간격을 두기위해 작성
                    System.getProperty("line.separator") +
                    "안녕하세요 회원님 BUYSELL을 찾아주셔서 감사합니다"
                    + System.getProperty("line.separator") +
                    System.getProperty("line.separator") +
                    " 인증번호는 " + key + " 입니다. "
                    + System.getProperty("line.separator") +
                    System.getProperty("line.separator") +
                    "받으신 인증번호를 홈페이지에 입력해 주시면 다음으로 넘어갑니다."; // 내용

        }

        if(mailType.equals("findpw")){
            title = "[BUYSELL] 비밀번호 찾기 임시비밀번호 이메일 입니다.";    //제목
            content =
                            System.getProperty("line.separator")+

                            System.getProperty("line.separator")+

                            "안녕하세요 회원님 BUYSELL를 찾아주셔서 감사합니다"

                            +System.getProperty("line.separator")+

                            System.getProperty("line.separator")+

                            "임시비밀번호는 " + key + " 입니다. "

                            +System.getProperty("line.separator")+

                            System.getProperty("line.separator")+

                            "임시비밀번호는 반드시 변경해주시기 바랍니다."; // 내용
        }

        logger.info(from + to +title +content);
        try {

            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message,
                    true, "UTF-8");

            messageHelper.setFrom(from); // 보내는사람 생략하면 정상작동을 안함
            messageHelper.setTo(to); // 받는사람 이메일
            messageHelper.setSubject(title); // 메일제목은 생략이 가능하다
            messageHelper.setText(content); // 메일 내용
            sender.send(message);
            logger.info("메일을 보냈습니다.");


            session.setAttribute("key", key);
            return "true";



        } catch (Exception e) {
            System.out.println(e);
            return "메일보내기를 실패하였습니다. 존재하지 않는 이메일일 수 있습니다.";
        }
    }


    // 난수를 이용한 키 생성
    private boolean lowerCheck;
    private int size;

    public String getKey(boolean lowerCheck, int size){
        this.lowerCheck = lowerCheck;
        this.size = size;
        return init();
    }

    //이메일 난수만드는 메소드
    private String init()  {
        Random ran = new Random();
        StringBuffer sb = new StringBuffer();
        int num = 0;

        do {
            num = ran.nextInt(75) + 48;
            if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
                sb.append((char) num);
            } else {
                continue;
            }

        } while (sb.length() < size);
        if (lowerCheck) {
            return sb.toString().toLowerCase();
        }
        return sb.toString();
    }


    public boolean checkCode(HttpServletRequest request, String codeKey, String email){
     HttpSession session = request.getSession();
     String key = (String) session.getAttribute("key");
        System.out.println(email);
        System.out.println(key);
         if(codeKey.equals(key)){
             System.out.println("일치#############");
             session.removeAttribute("key");
             return true;
        }
        System.out.println("불일치#############");
         return false;
    }
}
