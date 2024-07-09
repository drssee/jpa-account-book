package namhyun.account_book.common;

import namhyun.account_book.dto.SendDto;
import namhyun.account_book.dto.SendResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DummySendManager implements SendManager {

    @Override
    public SendResult doSend(List<SendDto> sendDtoList) {
        System.out.println("send start");
        sendDtoList.forEach(System.out::println);
        SendResult sendResult = new SendResult();
        sendResult.setSize(sendResult.getSize());
        sendResult.setSuccess(sendResult.getSuccess());
        sendResult.setFail(0);
        System.out.println("send end");

        return sendResult;
    }
}
