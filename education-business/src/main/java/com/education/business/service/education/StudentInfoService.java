package com.education.business.service.education;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.business.mapper.education.StudentInfoMapper;
import com.education.business.service.BaseService;
import com.education.common.model.PageInfo;
import com.education.common.model.StudentInfoImport;
import com.education.common.utils.Md5Utils;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.ResultCode;
import com.education.common.utils.SpellUtils;
import com.education.model.dto.StudentInfoDto;
import com.education.model.entity.GradeInfo;
import com.education.model.entity.StudentInfo;
import com.education.model.request.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 学员管理
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/21 18:16
 */
@Service
public class StudentInfoService extends BaseService<StudentInfoMapper, StudentInfo> {

    @Autowired
    private GradeInfoService gradeInfoService;

    public PageInfo<StudentInfoDto> selectPageList(PageParam pageParam, StudentInfo studentInfo) {
        Page<StudentInfoDto> page = new Page<>(pageParam.getPageNumber(), pageParam.getPageSize());
        return selectPage(baseMapper.selectPageList(page, studentInfo));
    }

    public void updatePassword(StudentInfoDto studentInfoDto) {
        String password = studentInfoDto.getPassword();
        String encrypt = studentInfoDto.getEncrypt();
        password = Md5Utils.getMd5(password,  encrypt);
        studentInfoDto.setPassword(password);
        super.updateById(studentInfoDto);
    }

    public void importStudentFromExcel(List<StudentInfoImport> studentList) throws Exception {
        List<GradeInfo> gradeInfoList = gradeInfoService.list();
        Map<String, Integer> gradeInfoMap = new HashMap<>();
        gradeInfoList.forEach(gradeInfo -> {
            gradeInfoMap.put(gradeInfo.getName(), gradeInfo.getId());
        });
        List<StudentInfo> studentInfoList = new ArrayList<>();
        Date now = new Date();
        for (StudentInfoImport studentInfoImport : studentList) {
            StudentInfo studentInfo = new StudentInfo();
            Integer gradeInfoId = gradeInfoMap.get(studentInfoImport.getGradeName());
            if (ObjectUtils.isEmpty(gradeInfoId)) {
                continue;
            }
            studentInfo.setGradeInfoId(gradeInfoId);
            studentInfo.setSex("男".equals(studentInfo.getSex()) ? ResultCode.SUCCESS : ResultCode.FAIL);
            String name = studentInfoImport.getName();
            studentInfo.setName(name);
            studentInfo.setAge(studentInfoImport.getAge());
            studentInfo.setAddress(studentInfoImport.getAddress());
            String loginName = SpellUtils.getSpellHeadChar(name); // 获取登录名
            String encrypt = Md5Utils.encodeSalt(Md5Utils.generatorKey());
            String password = Md5Utils.getMd5(loginName, encrypt); //生成默认密码
            studentInfo.setPassword(password);
            studentInfo.setEncrypt(encrypt);
            studentInfo.setLoginName(loginName);
            studentInfo.setMotherName(studentInfoImport.getMotherName());
            studentInfo.setFatherName(studentInfoImport.getFatherName());
            studentInfo.setCreateDate(now);
            studentInfoList.add(studentInfo);
        }
        super.saveBatch(studentInfoList);
    }
}
