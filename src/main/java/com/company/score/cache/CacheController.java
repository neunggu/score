package com.company.score.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = {"/cache"})
public class CacheController {

    private final CacheService cacheService;

    @GetMapping("/names")
    public ResponseEntity<?> names(){
        return ResponseEntity.ok(cacheService.getCacheNames());
    }

    @GetMapping("/all")
    public ResponseEntity<?> cache(){
        Collection<String> cacheNames = cacheService.getCacheNames();
        List<Object> cacheContents = new ArrayList<>();
        for (String name:cacheNames) {
            cacheContents.add(cacheService.getCache(name));
        }
        return ResponseEntity.ok(cacheContents);
    }

    @GetMapping("/names/{name}")
    public ResponseEntity<?> cacheContent(@PathVariable String name){
        return ResponseEntity.ok(cacheService.getCache(name));
    }

    @GetMapping("/clear")
    public ResponseEntity<?> clearAll(){
        return ResponseEntity.ok(cacheService.clearAll());
    }

    @GetMapping("/clear/{name}")
    public ResponseEntity<?> clear(@PathVariable String name){
        return ResponseEntity.ok(cacheService.clear(name));
    }

}
