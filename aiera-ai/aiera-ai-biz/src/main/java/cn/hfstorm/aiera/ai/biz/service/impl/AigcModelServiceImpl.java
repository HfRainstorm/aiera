package cn.hfstorm.aiera.ai.biz.service.impl;

import cn.hfstorm.aiera.ai.biz.entity.AigcModel;
import cn.hfstorm.aiera.ai.biz.enums.ModelTypeEnum;
import cn.hfstorm.aiera.ai.biz.mapper.AigcModelMapper;
import cn.hfstorm.aiera.ai.biz.service.AigcModelService;
import cn.hfstorm.aiera.common.core.utils.QueryPage;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tycoding
 * @since 2024/1/19
 */
@Service
@RequiredArgsConstructor
public class AigcModelServiceImpl extends ServiceImpl<AigcModelMapper, AigcModel> implements AigcModelService {

    @Override
    public List<AigcModel> getChatModels() {
        List<AigcModel> list = baseMapper.selectList(Wrappers.<AigcModel>lambdaQuery().eq(AigcModel::getType,
                ModelTypeEnum.CHAT.name()));
        list.forEach(this::hide);
        return list;
    }


    @Override
    public List<AigcModel> list(AigcModel data) {
        List<AigcModel> list = this.list(Wrappers.<AigcModel>lambdaQuery().eq(StrUtil.isNotBlank(data.getType()),
                AigcModel::getType, data.getType()).eq(StrUtil.isNotBlank(data.getProvider()), AigcModel::getProvider
                , data.getProvider()));
        list.forEach(this::hide);
        return list;
    }

    @Override
    public Page<AigcModel> page(AigcModel data, QueryPage queryPage) {
        Page<AigcModel> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        Page<AigcModel> iPage = this.page(page, Wrappers.<AigcModel>lambdaQuery().eq(AigcModel::getProvider,
                data.getProvider()));
        iPage.getRecords().forEach(this::hide);
        return iPage;
    }

    @Override
    public AigcModel selectById(String id) {
        AigcModel model = this.getById(id);
        hide(model);
        return model;
    }

    private void hide(AigcModel model) {
        if (model == null || StrUtil.isBlank(model.getApiKey())) {
            return;
        }
        String key = StrUtil.hide(model.getApiKey(), 3, model.getApiKey().length() - 4);
        model.setApiKey(key);

        if (StrUtil.isBlank(model.getSecretKey())) {
            return;
        }
        String sec = StrUtil.hide(model.getSecretKey(), 3, model.getSecretKey().length() - 4);
        model.setSecretKey(sec);
    }
}

