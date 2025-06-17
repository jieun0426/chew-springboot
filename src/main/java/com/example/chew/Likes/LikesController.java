package com.example.chew.Likes;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    @PostMapping("/toggle")
    public Map<String, Object> toggleLike(@RequestBody Map<String, Integer> request, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String memberId = auth.getName(); //로그인한 사용자

        Integer storecode = request.get("storecode");

        Map<String, Object> result = new HashMap<>();
        if (memberId == null || memberId.equals("anonymousUser")) {
            result.put("success", false);
            result.put("message", "로그인이 필요합니다.");
            return result;
        }

        boolean liked = likesService.toggleLike(memberId, storecode);
        int likeCount = likesService.getStoreLikes(storecode);

        result.put("success", true);
        result.put("liked", liked);
        result.put("storelikes", likeCount);
        return result;
    }

    @PostMapping("/check")
    public Map<String, Object> checkLike(@RequestBody Map<String, Integer> request, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String memberId = auth.getName(); //로그인한 사용자
        Integer storecode = request.get("storecode");

        Map<String, Object> result = new HashMap<>();
        if (memberId == null || memberId.equals("anonymousUser")) {
            result.put("success", false);
            result.put("message", "로그인이 필요합니다.");
            return result;
        }

        boolean liked = likesService.isLiked(memberId, storecode);
        int likeCount = likesService.getStoreLikes(storecode);

        result.put("success", true);
        result.put("liked", liked);
        result.put("storelikes", likeCount);
        return result;
    }
}
