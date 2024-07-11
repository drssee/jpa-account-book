package namhyun.account_book.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SendResult {

    int size;
    int success;
    int fail;
    @Setter(AccessLevel.NONE)
    List<SendTimes> sendTimes = new ArrayList<>();

    public void addSendTimes(Long id, LocalDateTime sendTime) {
        SendTimes sendTimes = new SendTimes();
        sendTimes.setId(id);
        sendTimes.setSendTime(sendTime);
        this.sendTimes.add(sendTimes);
    }

    @Getter
    @Setter
    public class SendTimes {
        private Long id;
        private LocalDateTime sendTime;
    }
}
