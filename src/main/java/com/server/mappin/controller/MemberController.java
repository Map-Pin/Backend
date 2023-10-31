package com.server.mappin.controller;

import com.server.mappin.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/auth/kakao/callback")
    public ResponseEntity<?> join(@RequestParam(value = "code") String code){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
