package com.yc.snackoverflow.service;


import com.yc.snackoverflow.data.ColorCustomResponseData;
import com.yc.snackoverflow.data.ColorRequestData;
import com.yc.snackoverflow.model.Color;
import com.yc.snackoverflow.data.ColorResponseData;

import java.util.List;
import java.util.Map;

public interface ColorService {
    List<ColorResponseData> getAllColor();

    Map<String, Color> getColors();

    List<ColorCustomResponseData> getColor();

    void saveColor(ColorRequestData colorRequestData);

    // save color by code
    void saveColorByCode(ColorRequestData colorRequestData);
}
