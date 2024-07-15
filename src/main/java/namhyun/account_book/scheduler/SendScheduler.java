package namhyun.account_book.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import namhyun.account_book.dto.SendResult;
import namhyun.account_book.service.SendService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class SendScheduler {

    private final SendService sendService;

//      주요 크론 표현식 예제
//    */1 * * * * *: 매 1초마다 실행
//    0 */1 * * * *: 매 1분마다 실행
//    0 0/5 * * * *: 매 5분마다 실행
//    0 0 12 * * *: 매일 12시에 실행
//    0 0 0 * * *: 매일 자정에 실행
//    0 0 0 1 * *: 매월 1일 자정에 실행
//    0 0 9-17 * * MON-FRI: 월요일부터 금요일까지 9시부터 17시까지 매 정각에 실행
    @Scheduled(cron = "0 0/1 * * * *")
    public void send() {
        try {
            SendResult result = sendService.doSend();
            log.info("send size = {}", result.getSize());
            log.info("send success = {}", result.getSuccess());
            log.info("send fail = {}", result.getFail());
        } catch (Exception e) {
            log.error("send() exception - {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
