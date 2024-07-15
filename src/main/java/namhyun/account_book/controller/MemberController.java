package namhyun.account_book.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import namhyun.account_book.dto.MemberDto;
import namhyun.account_book.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDto> getMemberById(@PathVariable String memberId) {
        return ResponseEntity.ok(memberService.getMemberById(memberId));
    }

    @PostMapping
    public ResponseEntity<String> createMember(@Valid @RequestBody MemberDto memberDto) {
        MemberDto savedMember = memberService.saveMember(memberDto);
        return ResponseEntity.ok(savedMember.getId() + " created");
    }

    @PutMapping
    public ResponseEntity<String> updateMember(@Valid @RequestBody MemberDto memberDto) {
        MemberDto updatedMember = memberService.updateMember(memberDto);
        return ResponseEntity.ok(updatedMember.getId() + " updated");
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<String> deleteMember(@PathVariable String memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.ok(memberId + " deleted");
    }

}
