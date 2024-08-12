package com.simple.basic.memo;

import com.simple.basic.command.MemoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// 이게 붙은 인터페이스를 마이바티스가 인식
@Mapper
public interface MemoMapper {
    String hello();

    void inputMemo(MemoVO memoVO);

    List<MemoVO> getMemos();
}
