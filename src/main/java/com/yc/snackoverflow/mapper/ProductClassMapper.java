package com.yc.snackoverflow.mapper;


import com.yc.snackoverflow.data.ColorCustomResponseData;
import com.yc.snackoverflow.data.ProductClassDto;
import com.yc.snackoverflow.projector.ColorProjection;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ProductClassMapper extends ListMapper
{

	@BeanMapping(ignoreByDefault = true)
	ProductClassDto to(ColorProjection source);

}

