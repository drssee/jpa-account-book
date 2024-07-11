package namhyun.account_book.common;

import namhyun.account_book.dto.SendDto;
import namhyun.account_book.dto.SendResult;
import org.springframework.stereotype.Component;

import java.util.List;

public class DummySendManager implements SendManager {

    @Override
    public SendResult doSend(List<SendDto> sendDtoList) {
        System.out.println("send start");
        SendResult sendResult = new SendResult();
        sendResult.setSize(sendDtoList.size());
        // 전부 성공한다고 가정
        sendResult.setSuccess(sendDtoList.size());
        sendResult.setFail(0);
        sendDtoList.forEach(e -> {
            System.out.println(e.getSendTime());
            sendResult.addSendTimes(e.getId(), e.getSendTime());
        });
        System.out.println("send end");

        return sendResult;
    }
}
