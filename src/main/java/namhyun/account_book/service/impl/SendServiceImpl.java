package namhyun.account_book.service.impl;

import lombok.RequiredArgsConstructor;
import namhyun.account_book.dao.SendDao;
import namhyun.account_book.dto.SendDto;
import namhyun.account_book.service.SendService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SendServiceImpl implements SendService {

    private final SendDao sendDao;

    @Override
    public SendDto saveSend(SendDto sendDto) {
        return sendDao.saveSend(sendDto);
    }
}
