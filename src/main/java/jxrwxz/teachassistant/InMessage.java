package jxrwxz.teachassistant;

import lombok.AccessLevel;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

//客户端发到服务器的数据
@Data
@NoArgsConstructor(access= AccessLevel.PRIVATE,force=true)
public class InMessage {

    private String from;

    private String to;

    private String content;

}
