package namhyun.account_book.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import namhyun.account_book.dto.AccountBookDto;
import namhyun.account_book.service.AccountBookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountBookController {

    private final AccountBookService accountBookService;

    @PostMapping
    public ResponseEntity<AccountBookDto> pay(@Valid @RequestBody AccountBookDto accountBookDto) {
//        AccountBookDto accountBook = accountBookService.pay(accountBookDto);
//        return ResponseEntity.ok(accountBook.getId() + " created");
        return ResponseEntity.ok(accountBookService.pay(accountBookDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountBookDto> getAccountBook(@PathVariable Long id) {
        return ResponseEntity.ok(accountBookService.getAccountBookById(id));
    }

    @PutMapping
    public ResponseEntity<AccountBookDto> updateAccountBook(@Valid @RequestBody AccountBookDto accountBookDto) {
        return ResponseEntity.ok(accountBookService.updateAccountBook(accountBookDto));
    }
}
