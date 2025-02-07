package cn.hfstorm.aiera.server.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author : hmy
 * @date : 2025/2/7 17:20
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ApiChatEndpoint {


    @GetMapping("/generate")
    public Map generate(@RequestParam(value = "message",
            defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", message);
    }

}
