package com.example.jpa.controller;

import com.example.jpa.entity.MemberMemoDTO;
import com.example.jpa.entity.Memo;
import com.example.jpa.repository.MemoRepository;
import com.example.jpa.util.Criteria;
import com.example.jpa.util.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JpaRestController {
    @Autowired
    private MemoRepository memoRepository;

    @GetMapping("/getList")
    public PageVO<MemberMemoDTO> getList(Criteria cri) {
        System.out.println(cri.toString());
        Pageable pageable = PageRequest.of(cri.getPage() - 1, cri.getAmount());
//        Page<MemberMemoDTO> page = memoRepository.joinPage(cri.getSearchName(), pageable); // jpql
        Page<MemberMemoDTO> page = memoRepository.dslJoinPage(pageable, cri); // queryDSL

        return new PageVO<>(page);
    }
}
