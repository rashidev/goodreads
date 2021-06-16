package com.developia.goodreads.controller;

import com.developia.goodreads.dao.entity.BucketsEntity;
import com.developia.goodreads.service.BucketService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/buckets")
public class BucketController {
    private BucketService bucketService;

    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @GetMapping("/my-bucket")
    public String getBucket(Model model, Authentication authentication) {
        BucketsEntity bucket = bucketService.getBucket(authentication.getName());

        model.addAttribute("bucket", bucket);

        return "bucket";
    }

    @PostMapping("/add-to-bucket")
    public String addToBucket(@RequestParam Long bookId, Authentication authentication) {
        bucketService.addToBucket(authentication.getName(), bookId);
        return "redirect:/buckets/my-bucket";
    }

    @PostMapping("/delete-from-bucket")
    public String deleteBookFromBucket(@RequestParam Long bookId, Authentication authentication) {
        bucketService.deleteBookFromBucket(authentication.getName(), bookId);
        return "redirect:/buckets/my-bucket";
    }
}
