package com.github.binarywang.demo.wx.mp.controller.client;

import com.github.binarywang.demo.wx.mp.config.intercepors.LoginInterceptor;
import com.github.binarywang.demo.wx.mp.entity.surce.LsClientUser;
import com.github.binarywang.demo.wx.mp.entity.surce.LsSignInRemind;
import com.github.binarywang.demo.wx.mp.entity.surce.LsUserClass;
import com.github.binarywang.demo.wx.mp.enums.ClassTypeEnum;
import com.github.binarywang.demo.wx.mp.enums.ResultCodeEnum;
import com.github.binarywang.demo.wx.mp.service.manager.SignInRemindService;
import com.github.binarywang.demo.wx.mp.service.manager.UserClassService;
import com.github.binarywang.demo.wx.mp.utils.PageUtils;
import com.github.binarywang.demo.wx.mp.utils.ResultEntity;
import com.github.binarywang.demo.wx.mp.utils.ResultUtils;
import com.github.binarywang.demo.wx.mp.vo.UserClassReq;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/client")
public class SignInRemindController {

    SignInRemindService signInRemindService;

    /**
     * 跳转到用户课程页面
     *
     *  */
    @RequestMapping("/signInRemind/to-list")
    public String toList(){
        return "sys/userClass-list";
    }


    /**
     * 获取个人购买信息
     * @return
     */
    @GetMapping("/signInRemind/findAllById")
    @ResponseBody
    public ResultEntity findAll(HttpServletRequest request) {
        HttpSession session = request.getSession();
        LsClientUser clientUser = (LsClientUser) session.getAttribute(LoginInterceptor.CLIENT_SESSION_KEY);
        if(clientUser==null){
            return ResultUtils.fail(ResultCodeEnum.INTERNAL_ERROR);
        }
        List<LsSignInRemind> signInReminds = signInRemindService.findByUserPhone(clientUser.getPhone());
        if(!CollectionUtils.isEmpty(signInReminds)){
            signInReminds.stream().forEach(item ->{
                 String classType = item.getClassType();
                 if(!StringUtils.isEmpty(classType)){
                     if(ClassTypeEnum.CLASS.getCode().equals(classType)){
                         item.setClassType(ClassTypeEnum.CLASS.getDesc());
                     }else if(ClassTypeEnum.GROUP_COURSE.getCode().equals(classType)){
                         item.setClassType(ClassTypeEnum.GROUP_COURSE.getDesc());
                     }else if(ClassTypeEnum.ONE_ON_ONE.getCode().equals(classType)){
                         item.setClassType(ClassTypeEnum.ONE_ON_ONE.getDesc());
                     }
                 }
            });
        }
        return ResultUtils.success(signInReminds);
    }

}