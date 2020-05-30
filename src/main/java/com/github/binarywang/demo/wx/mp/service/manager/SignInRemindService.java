package com.github.binarywang.demo.wx.mp.service.manager;

import com.github.binarywang.demo.wx.mp.entity.surce.*;
import com.github.binarywang.demo.wx.mp.enums.*;
import com.github.binarywang.demo.wx.mp.repository.client.ClientUserRepository;
import com.github.binarywang.demo.wx.mp.repository.client.UserSignInRepository;
import com.github.binarywang.demo.wx.mp.repository.manager.SignInRemindRepository;
import com.github.binarywang.demo.wx.mp.repository.manager.UserClassRepository;
import com.github.binarywang.demo.wx.mp.utils.ResultEntity;
import com.github.binarywang.demo.wx.mp.utils.ResultUtils;
import com.github.binarywang.demo.wx.mp.vo.UserClassReq;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SignInRemindService {

    SignInRemindRepository signInRemindRepository;

    /**
     * 分页获取所有信息
     * @return
     */
    public Page<LsSignInRemind> findPage(Map<String, String> params, Pageable pageable) {
        return signInRemindRepository.findPage(params,pageable);
    }


    public List<LsSignInRemind> findAll() {
        return signInRemindRepository.findAll();
    }


    /**
     * 新增用户课程
     *
     * */
    public void save(LsSignInRemind signInRemind) {
        signInRemindRepository.save(signInRemind);
    }

    /**
     * 批量保存用户课程
     *
     * */
    @Transactional(rollbackFor = Exception.class)
    public void saveAll(List<LsSignInRemind> signInRemindList) {
        signInRemindRepository.saveAll(signInRemindList);
    }

    /**
     * 批量保存用户课程
     *
     * */
    @Transactional(rollbackFor = Exception.class)
    public List<LsSignInRemind> findByUserPhone(String phone) {
        return signInRemindRepository.findByUserPhone(phone);
    }

    /**
     * 获取详情
     *
     * */
    @Transactional(rollbackFor = Exception.class)
    public LsSignInRemind findById(String id) {
        Optional<LsSignInRemind> byId = signInRemindRepository.findById(Long.valueOf(id));
        if(byId==null){
            return null;
        }
        LsSignInRemind signInRemind = byId.get();
        //修改已读状态
        signInRemind.setReadState(ReadStateEnum.READ.getCode());
        signInRemind = signInRemindRepository.save(signInRemind);
        return signInRemind;

    }
    /**
     * 获取详情
     *
     * */
    public long findNumByPhone(String phone) {
        return signInRemindRepository.findNumByPhone(phone);
    }
}
