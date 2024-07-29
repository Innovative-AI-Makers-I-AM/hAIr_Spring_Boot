package com.example.demo.chat.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @PostMapping("/face-analysis")
    @ResponseBody
    public ResponseEntity<?> analyzeFace(@RequestParam("image") MultipartFile image) {
        // try {
        //     // Python 서버로 파일 전송
        //     String url = "http://localhost:8000/face-analysis";
        //     MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        //     body.add("image", image.getResource());

        //     HttpHeaders headers = new HttpHeaders();
        //     headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        //     HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        //     RestTemplate restTemplate = new RestTemplate();
        //     ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        //     // Python 서버의 응답 반환
        //     return ResponseEntity.ok(response.getBody());
        // } catch (Exception e) {
        //     return ResponseEntity.status(500).body("Error: " + e.getMessage());
        // }





        // python 코드가 완성될때까지 쓰는 임시 답변
        try {
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/hairstyle-recommendations")
    @ResponseBody
    public ResponseEntity<?> getHairstyleRecommendations(@RequestBody Map<String, String> request) {
        System.out.println(request);
        try {
            // Python 서버로 데이터 전송
            String url = "http://localhost:8000/hairstyle-recommendations";
            // String url = "http://192.168.0.254:8000/hairstyle-recommendations";
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            System.out.println(response);
            // Python 서버의 응답 반환
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }





        // python 코드가 완성될때까지 쓰는 임시 답변
        // try {
        //     String hardcodedResponse = """
        // {
        //     "response": "물론이죠! 어떤 휴일이 가장 힐링된다고 느끼시는지 알아보는 것이 중요해요. 몇 가지 질문을 드릴게요:\\n휴식 스타일: 조용한 곳에서 혼자 시간을 보내는 걸 좋아하시나요, 아니면 가족이나 친구들과 어울리는 걸 더 좋아하시나요?\\n활동 수준: 휴일에 활동적인 걸 선호하시나요? 예를 들어, 하이킹, 자전거 타기, 수영 등. 아니면 편안하게 책을 읽거나 영화를 보는 걸 좋아하시나요?\\n자연 속에서의 시간: 자연에서 보내는 시간을 좋아하시나요? 바다, 산, 공원 등 자연 환경에서 시간을 보내는 걸 즐기시나요?\\n문화 및 취미 활동: 예술 갤러리, 박물관 방문, 공예 활동 등 문화적 경험을 즐기시나요?\\n웰니스 활동: 명상, 요가, 마사지 같은 웰니스 활동에 관심이 있으신가요?\\n식사 경험: 특별한 음식을 즐기거나, 새로운 요리를 시도해보는 걸 좋아하시나요?\\n이 질문들에 대한 답변을 바탕으로 더 구체적인 힐링 휴일 계획을 세워볼 수 있을 것 같아요. 어떻게 쉬는 걸 좋아하시는지 알려주시면 감사하겠습니다!",
        //     "images": [
        //         "http://localhost:3000/hair1.jpeg",
        //         "http://localhost:3000/hair1.jpeg",
        //         "http://localhost:3000/hair2.jpg",
        //         "http://localhost:3000/hair1.jpeg"
        //     ]
        // }
        // """;

        //     return ResponseEntity.ok(hardcodedResponse);
        // } catch (Exception e) {
        //     return ResponseEntity.status(500).body("Error: " + e.getMessage());
        // }
    }
}
