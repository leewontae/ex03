package org.zerock.controller;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.SampleVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping(value = "/sample")
@Log4j
public class SampleController {

    @GetMapping(value = "/getText", produces = "text/plain; charset=UTF-8")
    public String getText(){


        log.info("SSSSSSSSSSSSMIME type : " + MediaType.TEXT_PLAIN_VALUE);

        return "안녕하세요";
    }

    @GetMapping(value = "/getSample",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public SampleVO getSample(){
        return new SampleVO(112,"스타","로드");
    }
    // @GetMapping 또는 @RequestMapping의 produces 속성은 반드시 지정해야 하는 것은 아니므로 생략 가능 하다.
    @GetMapping(value = "/getSample2")
    public SampleVO getSample2(){
        return new SampleVO(113,"로켓","라쿤");
    }

    //내부적으로 1부터 10미만까지의 루프를 처리하면서 SampleVO 객체를 만들어서 List<SampleVO>로 만들어 낸다.
    //마찬가지로 xml 보내주고 .json으로 가능 하다. (16.2.3)
    @GetMapping(value = "/getList")
    public List<SampleVO> getList(){
        return IntStream.range(1,10).mapToObj(i->new SampleVO(i,i+"first",i+"last")).collect(Collectors.toList());
    }

    //MAP을 이용하는 경우에는 키에 속하는 데이터는 xml로 변환되는 경우에 태그의이름이 되기 떄문에 문자열을 지정한다.
    @GetMapping(value = "/getMap")
    public Map<String,SampleVO> getMap(){

        Map<String, SampleVO >map = new HashMap<>();

        map.put("first", new SampleVO(111,"그루트","주니어"));

        return map;
    }

    //16.2.4 ResponseEntity  타입
    // responseEntity 는 데이터와 함께 http 헤더의 상태 메세지등을 같이 전당하는 용도로 사용 한다.
    //http의 상태 코드와 에러메시지 등을 함께 데이터를 전달 할 수 있기 떄문에 받는 입장에서는 확실하게 결과를 알수 있다.
//height <150 보다 작으면 bad_gateway(502) 샅태코드와 데이터를 전송 하고 이상이면 200(ok) 메세지를 보낸다.
    // 개발자 도구 F12 통해 확인 가능 하다. 
    @GetMapping(value = "/check",params = {"height","weight"})
    public ResponseEntity<SampleVO> check(Double height, Double weight){
        SampleVO vo = new SampleVO(0,""+height,""+weight);

        ResponseEntity<SampleVO> result = null;

        if(height < 150 ) {
            result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
        }else{
            result = ResponseEntity.status(HttpStatus.OK).body(vo);
        }
        return result;
    }

}
