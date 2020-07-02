package site.teamo.mall.controller.usercenter;


import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.teamo.mall.bean.Users;
import site.teamo.mall.bean.bo.usercenter.CenterUserBO;
import site.teamo.mall.common.util.CookieUtils;
import site.teamo.mall.common.util.MallJSONResult;
import site.teamo.mall.resources.FileUpload;
import site.teamo.mall.service.usercenter.CenterUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Api(value = "用户信息", tags = "用户信息")
@RestController
@RequestMapping("/userInfo")
public class UserCenterUserController {

    @Autowired
    private CenterUserService centerUserService;

    @Autowired
    private FileUpload fileUpload;

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息")
    @PostMapping("/update")
    public MallJSONResult update(
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam String userId,
            @ApiParam(name = "centerUserBO", value = "用户信息BO")
            @RequestBody @Valid CenterUserBO centerUserBO,
            HttpServletRequest request,
            HttpServletResponse response,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> map = new HashMap<>();
            bindingResult.getFieldErrors().stream().forEach(e -> {
                map.put(e.getField(), e.getDefaultMessage());
            });
            return MallJSONResult.errorMap(map);
        }

        Users user = centerUserService.updateUserInfo(userId, centerUserBO);
        user.cleanUpSensitiveInformation();
        CookieUtils.setCookie(request, response, "user", JSON.toJSONString(user), true);
        return MallJSONResult.ok();
    }

    @ApiOperation(value = "用户头像上传", tags = "用户头像上传")
    @PostMapping("/uploadFace")
    public MallJSONResult uploadFace(
            @ApiParam(name = "userId",value = "用户id")
            @RequestParam
            String userId,
            @ApiParam(name = "file",value = "用户头像文件")
            MultipartFile file,
            HttpServletRequest request,
            HttpServletResponse response) {

        if(file==null){
            return MallJSONResult.errorMsg("文件不能为空");
        }

        String fileName = file.getOriginalFilename();

        String[] fileNames = fileName.split("\\.");
        if(!StringUtils.endsWithIgnoreCase("png",fileNames[fileNames.length-1])&&!StringUtils.endsWithIgnoreCase("jpg",fileNames[fileNames.length-1])){
            return MallJSONResult.errorMsg("文件名不合法");
        }

        String newFileName = "face-"+userId+"."+fileNames[fileNames.length-1];

        String filePath = fileUpload.getImageUserFaceLocation()+ File.separator+userId+File.separator+newFileName;

        File outFile = new File(filePath);

        if(outFile.getParentFile() != null){
            outFile.getParentFile().mkdirs();
        }

        FileOutputStream outputStream =null;
        InputStream inputStream = null;
        try {
             outputStream = new FileOutputStream(outFile);
             inputStream = file.getInputStream();
            StreamUtils.copy(inputStream,outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(inputStream);
        }

        Users users = centerUserService.updateUserFace(userId,fileUpload.getImageServerUrl()+ "/"+userId+"/"+newFileName+"?time="+System.currentTimeMillis());
        users.cleanUpSensitiveInformation();
        CookieUtils.setCookie(request,response,"user",JSON.toJSONString(users),true);

        return MallJSONResult.ok(users);
    }

}
