package com.example.demo.simulation.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping
    public ResponseEntity<byte[]> simulateHairstyle(
            @RequestParam("face") MultipartFile faceImage,
            @RequestParam("hair") MultipartFile hairImage,
            @RequestParam("id") String userId) {

        try {
            String _folderPath = "simulatedImg/" + userId; // 유저 id 기반 폴더 경로 설정
                File _folder = new File(_folderPath);
                if (!_folder.exists()) {
                    _folder.mkdirs(); // 폴더가 존재하지 않으면 생성
                }



                

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8000" + "/hair_transfer");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("face", new MultipartInputStreamFileResource(faceImage.getInputStream(), faceImage.getOriginalFilename()));
            body.add("shape", new MultipartInputStreamFileResource(hairImage.getInputStream(), hairImage.getOriginalFilename()));
            body.add("color", new MultipartInputStreamFileResource(hairImage.getInputStream(), hairImage.getOriginalFilename()));

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<byte[]> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, requestEntity, byte[].class);

            // 결과 이미지 저장
            if (response.getStatusCode() == HttpStatus.OK) {
                byte[] imageBytes = response.getBody();
                String folderPath = "simulatedImg/" + userId; // 유저 id 기반 폴더 경로 설정
                File folder = new File(folderPath);
                if (!folder.exists()) {
                    folder.mkdirs(); // 폴더가 존재하지 않으면 생성
                }

                // 콜론(:)을 대시(-)로 바꿔서 타임스탬프 형식 변경
                String timestamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
                File imageFile = new File(folderPath + "/" + timestamp + ".png");
                try (FileOutputStream fos = new FileOutputStream(imageFile)) {
                    fos.write(imageBytes);
                }
            }

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Content-Type", "image/png");
            return new ResponseEntity<>(response.getBody(), responseHeaders, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
