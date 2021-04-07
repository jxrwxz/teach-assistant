package jxrwxz.teachassistant.web;

import jxrwxz.teachassistant.run.ExecuteStringSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
@RequestMapping(produces = "application/json")
@CrossOrigin(origins="*")
public class RunCodeController {

    private Logger logger = LoggerFactory.getLogger(RunCodeController.class);

    @Autowired
    ExecuteStringSourceService executeStringSourceService;

//    private static final String defaultSource="public class Demo {\n"
//            + "    public static void main(String[] args)  {\n"
//            + "    \n"
//            + "    }\n"
//            + "}";


    @GetMapping("/ide")
    public ModelAndView ideView(){
        ModelAndView modelAndView=new ModelAndView("ide");
        return modelAndView;
    }

    @PostMapping("/ide")
    public String runCode(@RequestParam("code") String code,
                        @RequestParam("systemIn") String systemIn) throws IOException{
        String result=executeStringSourceService.execute(code, systemIn);
        result=result.replaceAll(System.lineSeparator(),"<br/>");
        String jsonString="{\"result\":\"" + result + "\"}";
        return jsonString;
    }

}
