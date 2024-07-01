package namhyun.account_book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class AccountBookServiceTest {

    @Autowired
    AccountBookService accountBookService;

    //1. accountbook 이 제대로 저장됐는지
    //2-1. statistics 가 없을 경우 제대로 저장됐는지
    //2-2. statistics 가 있을 경우 제대로 저장됐는지
    //3. config 가 제대로 저장됐는지
    //4-1. send 설정이 있을 경우 제대로 저장이 됐는지
    //4-2. send 설정이 없으면 저장이 안되는지
}
