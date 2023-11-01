package com.server.mappin.controller;

import com.server.mappin.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    @GetMapping("/loginSuccess")
    public ResponseEntity<?> join(@RequestParam(value = "email") String email,
                                  @RequestParam(value = "provider") String provider){
        System.out.println("email = " + email);
        System.out.println("provider = " + provider);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
