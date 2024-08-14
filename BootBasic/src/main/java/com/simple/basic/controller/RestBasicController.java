package com.simple.basic.controller;

import com.simple.basic.command.MemoVO;
import com.simple.basic.command.TestVO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController // Controller + ResponseBody (컨트롤러에서 응답을 요청이 들어온곳으로 바꿈) 합성어
public class RestBasicController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/hello2")
    public String[] hello2() {
        return new String[]{"Hello World", "Hello World2"};
    }


    // get 방식 요청받기 - 일반컨트롤러에서 받는형식과 똑같은 방법으로 가능함
    // 1st
//    @GetMapping("/getData")
//    public String getData() {
//        System.out.println();
//
//        return "Hello World3";
//    }

    // 2nd
    @GetMapping("/getData")
    public String getData() {
        System.out.println();

        return "Hello World3";
    }

    // pathVariable 방식
    // http://localhost:8181/getData2/1/홍길동
    @GetMapping("/getData2/{num}/{name}")
    public String getData2(@PathVariable int num, @PathVariable String name) {
        System.out.println(num + ". " + name);

        return "success";
    }

    // 반환을 json 형식으로 하려면 map 타입이나 vo 객체를 쓰면 된다. (list, 배열도 된다.)
    // Jackson-databind 라이브러리가 필요 (스프링부트에선 기본 포함)
    // 1st
//    @GetMapping("/returnData")
//    public TestVO returnData() {
//        return new TestVO(1, "서버에서 반환", 20, "서울시");
//    }

    // 2nd
    @GetMapping("/returnData")
    public Map<String, Integer> returnData() {
        Map<String, Integer> map = new HashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        return map;
    }

    // POST 방식 - 사용자와 제공자 이 둘의 데이터를 주고 받는 규약이 정확히 지켜져야 한다.
    // form 형식 데이터 전송 -
    @PostMapping("/getForm")
    public String getForm(TestVO vo) {
        System.out.println(vo.toString());
        return "success form";
    }

    // json 형식으로 데이터 전송
    // vo로 입력 받는건 form 타입에서 된다.
    // @RequestBody - json 데이터를 -> 자바 오브젝트로 변형해서 맵핑

    // 1 vo로 json 받기
//    @PostMapping("/getJSON")
//    public String getJSON(@RequestBody TestVO vo) {
//        System.out.println(vo.toString());
//        return "success json";
//    }

    // 2 map 으로 json 받기@PostMapping("/getJSON") - 데이터 불일치, 클라이언트의 잘못된 값 전송도 맵핑되기 때문에 웬만하면 쓰면 안된다.
    public String getJSON(@RequestBody TestVO vo) {
        System.out.println(vo.toString());
        return "success json";
    }

    // @PutMapping (수정), @DeleteMapping (삭제) - Post 방식과 거의 유사


    // consumes - 반드시 이 타입으로 보내라!
    // producer - 이 타입으로 줄게
    // 기본값 "application/json"
    @PostMapping(value = "/getResult", produces = "text/html;charset=UTF-8", consumes = "application/json")
    public String getResult(@RequestBody TestVO vo) {
        System.out.println(vo.toString());
        return "<h3>success result</h3>";
    }


    // 응답문서 명확하게 작성하기 ResponseEntity<데이터타입>
    @PostMapping("/getEntity")
    @CrossOrigin({"http://127.0.0.1:5500"})
    public ResponseEntity<TestVO> getEntity(@RequestBody TestVO v) {
        System.out.println(v.toString());
        TestVO vo = new TestVO(1, "hong", 20, "seoul");

        // 1st
//        ResponseEntity<TestVO> entity = new ResponseEntity<>(vo, HttpStatus.BAD_REQUEST);

        // 2nd
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer JSON Web token"); // 키, 값
        headers.add("Content-Type", "application/json");
//        headers.add("Access-Control-Allow-Origin", "http://example.com");

        ResponseEntity<TestVO> entity = new ResponseEntity<>(vo, headers, HttpStatus.OK);

        return entity;
    }

    // 실습
    /*
        요청주소 : /api/v1/getDAta
        method : get
        요청 파라미터 : sno(숫자), name(문자)
        응답 파라미터 : MemoVO
        헤더 담을 내용 : HttpStatus.OK
    */
    @GetMapping("/api/v1/getData")
    @CrossOrigin({"http://127.0.0.1:5500"})
    public ResponseEntity<MemoVO> getData(@RequestParam("sno") int sno, @RequestParam("name") String name) {
        System.out.println(sno + ". " + name);
        MemoVO memoVO = new MemoVO();
        memoVO.setMemo("success");
        memoVO.setMno(sno);

        return new ResponseEntity<>(memoVO, HttpStatus.OK);
    }

    /*
        요청주소 : /api/v1/getInfo
        method : post
        요청파라미터 : MemoVO
        응답 파라미터 : List<MemoVO> 타입
        헤더 담을 내용 HttpStatus.OK
    */
    @PostMapping("/api/v1/getInfo")
    @CrossOrigin({"http://127.0.0.1:5500"})
    public ResponseEntity<List<MemoVO>> getInfo(@RequestBody MemoVO vo) {
        List<MemoVO> list = new ArrayList<>();
        list.add(vo);

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");

        return new ResponseEntity<>(list, headers, HttpStatus.OK);
    }
}
