package com.simple.basic.memo;

import com.simple.basic.command.MemoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("memoService")
public class MemoServiceImpl implements MemoService {
    @Autowired
    MemoMapper memoMapper;

    @Override
    public void inputMemo(MemoVO memoVO) {
        memoMapper.inputMemo(memoVO);
    }

    @Override
    public List<MemoVO> getMemos() {
        return memoMapper.getMemos();
    }
}
