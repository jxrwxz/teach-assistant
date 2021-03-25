package jxrwxz.teachassistant;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

//服务端往客户端发的数据
@Data
@NoArgsConstructor(access= AccessLevel.PRIVATE,force=true)
public class OutMessage {

    private String from;

    private String content;

}
