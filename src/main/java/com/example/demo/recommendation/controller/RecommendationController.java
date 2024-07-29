package com.example.demo.recommendation.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/recommend")
public class RecommendationController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping
    public ResponseEntity<List<String>> recommendHairstyle(
        @RequestParam("gender") String gender,
        @RequestParam("length") String length,
        @RequestParam("style") String style,
        @RequestParam("face") MultipartFile faceImage) {

            System.out.println("lenght : " + length);
            System.out.println("style : " + style);
            System.out.println("face : " + faceImage);

        try {
            // 파이썬 서버로 보낼 요청 데이터 설정
            String url = "http://localhost:8000/search-hairstyles";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new ByteArrayResource(faceImage.getBytes()) {
                @Override
                public String getFilename() {
                    return faceImage.getOriginalFilename();
                }
            });
            body.add("sex", gender);
            body.add("length", length);
            body.add("styles", style);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // 파이썬 서버로 요청 보내기
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            // 파이썬 서버의 응답 받아서 클라이언트로 돌려주기
            String responseBody = responseEntity.getBody();

            // 응답 데이터를 파싱하여 List<String>으로 변환
            List<String> responseImages = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);

            if (rootNode.has("results")) {
                for (JsonNode resultNode : rootNode.get("results")) {
                    if (resultNode.has("image")) {
                        String base64Image = resultNode.get("image").asText();
                        responseImages.add(base64Image);
                    }
                }
            }

            return new ResponseEntity<>(responseImages, new HttpHeaders(), HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
