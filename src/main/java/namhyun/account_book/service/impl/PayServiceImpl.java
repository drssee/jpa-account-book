package namhyun.account_book.service.impl;

import namhyun.account_book.service.PayService;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {

    @Override
    public void pay() {
        // 1. account book 저장
        // 2. statistics 업데이트
        // 3. config 조회
        // 4. 메시지 발송여부 true 일 경우 send 저장
        // 5. 한도 알림여부 true 일 경우 경고메시지 작성
    }
}
