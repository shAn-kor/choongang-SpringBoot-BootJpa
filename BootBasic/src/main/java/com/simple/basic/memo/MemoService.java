package com.simple.basic.memo;

import com.simple.basic.command.MemoVO;

import java.util.List;

public interface MemoService {
    void inputMemo(MemoVO memoVO);

    List<MemoVO> getMemos();
}
