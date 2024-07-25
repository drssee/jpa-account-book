package namhyun.account_book.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import namhyun.account_book.dto.AccountBookDto;
import namhyun.account_book.dto.ConfigDto;
import namhyun.account_book.service.ConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
@RequiredArgsConstructor
public class ConfigController {

    private final ConfigService configService;

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody ConfigDto configDto) {
        ConfigDto savedConfig = configService.saveConfig(configDto);
        return ResponseEntity.ok(savedConfig.getId() + " saved");
    }
}
