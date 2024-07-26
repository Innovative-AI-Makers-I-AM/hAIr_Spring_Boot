package com.example.demo.simulation.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
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
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/simulation")
public class SimulationController {

    @PostMapping
    public ResponseEntity<byte[]> simulateHairstyle(
            @RequestParam("face") MultipartFile faceImage,
            @RequestParam("hair") MultipartFile hairImage) {
                
        try {
            RestTemplate restTemplate = new RestTemplate();
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://192.168.0.249:8000" + "/hair_transfer");

            // http 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // http 바디 설정
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("face", new MultipartInputStreamFileResource(faceImage.getInputStream(), faceImage.getOriginalFilename()));
            body.add("shape", new MultipartInputStreamFileResource(hairImage.getInputStream(), hairImage.getOriginalFilename()));
            body.add("color", new MultipartInputStreamFileResource(hairImage.getInputStream(), hairImage.getOriginalFilename()));
            
            System.out.println("=========================");
            System.out.println(faceImage);
            System.out.println(hairImage);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // 파이썬 서버에 사진들 전송 후 response 받기
            ResponseEntity<byte[]> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, requestEntity, byte[].class);

            System.out.println("=========================");
            System.out.println(response);

            // 클라이언트에 response 전달
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Content-Type", "image/png");
            return new ResponseEntity<>(response.getBody(), responseHeaders, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // simulateHairstyle 메소드에서 쓰이는 클래스
    // MultipartFile을 InputStreamResource로 변환하는 데 사용
    public class MultipartInputStreamFileResource extends InputStreamResource {

        private final String filename;
    
        public MultipartInputStreamFileResource(InputStream inputStream, String filename) {
            super(inputStream);
            this.filename = filename;
        }
    
        @Override
        public String getFilename() {
            return this.filename;
        }
    
        @Override
        public long contentLength() throws IOException {
            return -1; // We do not know the content length in advance
        }
    }
}
