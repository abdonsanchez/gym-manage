package com.gym.access.controller;

import com.gym.access.model.AccessLog;
import com.gym.access.service.AccessControlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/access")
@RequiredArgsConstructor
public class AccessControlController {

    private final AccessControlService accessService;

    @PostMapping("/check-in")
    public ResponseEntity<AccessLog> checkIn(@RequestParam Long memberId,
            @RequestParam(defaultValue = "qr") String method,
            @RequestParam(defaultValue = "Main Branch") String location) {
        return ResponseEntity.ok(accessService.registerAccess(memberId,
                AccessLog.AccessMethod.valueOf(method.toLowerCase()), AccessLog.AccessType.check_in, location));
    }

    @PostMapping("/check-out")
    public ResponseEntity<AccessLog> checkOut(@RequestParam Long memberId,
            @RequestParam(defaultValue = "qr") String method) {
        return ResponseEntity.ok(accessService.registerAccess(memberId,
                AccessLog.AccessMethod.valueOf(method.toLowerCase()), AccessLog.AccessType.check_out, "Main Branch"));
    }

    @GetMapping("/logs/{memberId}")
    public ResponseEntity<List<AccessLog>> getLogs(@PathVariable Long memberId) {
        return ResponseEntity.ok(accessService.getLogsByMember(memberId));
    }
}
