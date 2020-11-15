package com.education.service.system;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.education.mapper.system.SystemLogMapper;
import com.education.mapper.system.SystemMenuMapper;
import com.education.model.entity.SystemLog;
import com.education.model.entity.SystemMenu;
import com.education.service.BaseService;
import org.springframework.stereotype.Service;



/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/8 14:04
 */
@Service
public class SystemLogService extends BaseService<SystemLogMapper, SystemLog> {

}
