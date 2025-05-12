package com.yc.snackoverflow.service.impl;


import com.yc.snackoverflow.data.ColorCustomResponseData;
import com.yc.snackoverflow.data.ColorRequestData;
import com.yc.snackoverflow.data.ColorResponseData;
import com.yc.snackoverflow.mapper.CartonMapper;
import com.yc.snackoverflow.model.Color;
import com.yc.snackoverflow.reposity.CmsColorDao;
import com.yc.snackoverflow.service.ColorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {

    @Autowired
    private CmsColorDao cmsColorDao;

    @Autowired
    private CartonMapper cartonMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ColorResponseData> getAllColor() {
        List<Color> list = cmsColorDao.findAll();
        return list.stream().map(this::entityToResponseData).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ColorCustomResponseData> getColor() {
        List<ColorCustomResponseData> colorProjectionList = cmsColorDao.findColor();
        return colorProjectionList;
//                .stream()
//                .map(cartonMapper::toCmsCartonChangeToToteIssueResponseData)
//                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Color> getColors() {
        List<Color> list = cmsColorDao.findAll();
        return list
                .stream()
                .collect(Collectors.toMap(Color::getCode, Function.identity()));
    }

    @Override
    public void saveColor(ColorRequestData colorRequestData) {
        Color color = new Color(colorRequestData.getName(), colorRequestData.getNameZh(), colorRequestData.getNameEn());
        cmsColorDao.save(color);
    }

    @Override
    public void saveColorByCode(ColorRequestData colorRequestData) {

    }

    private ColorResponseData entityToResponseData(Color color) {
        return ColorResponseData.builder().code(color.getCode()).enName(color.getEnName()).zhName(color.getZhName()).build();
    }
}
