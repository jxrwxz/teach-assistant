package jxrwxz.teachassistant.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(produces="application/json")
@CrossOrigin(origins = "*")
public class BeforeChatController {

    private Long toTeacherId;
    private List ss;

    @PostMapping("/stuHasContactedTea")
    public void stuHasContactedTea(HttpServletRequest request){

    }
}
