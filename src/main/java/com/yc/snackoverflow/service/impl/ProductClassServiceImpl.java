package com.yc.snackoverflow.service.impl;

import com.yc.snackoverflow.data.ProductClassDto;
import com.yc.snackoverflow.exception.WebErrorEnum;
import com.yc.snackoverflow.model.ProductClass;
import com.yc.snackoverflow.reposity.ProductClassDao;
import com.yc.snackoverflow.service.ProductClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductClassServiceImpl implements ProductClassService {

    private final ProductClassDao productClassDao;

    @Override
    public void create(List<String> nameList) {
        ensureNoExistingProductClasses(nameList);
        save(nameList);
    }

    @Override
    @Transactional
    public List<String> list(List<String> nameList, Boolean isAlive) {
        if (isAlive == null) {
            return listAll(nameList);
        } else {
            return productClassDao.list(nameList).stream()
                    .filter(productClass -> !productClass.getAlive())
                    .map(ProductClass::getName)
                    .collect(Collectors.collectingAndThen(
                            Collectors.toList(),
                            list -> {
                                if (list.isEmpty()) {
                                    throw WebErrorEnum.PRODUCT_CLASS_NOT_FOUND.exception();
                                }
                                return list;
                            }
                    ));
        }
    }

    private List<String> listAll(List<String> nameList) {
        return productClassDao.list(nameList).stream()
                .map(ProductClass::getName)
                .collect(Collectors.toList());
    }

    @Override
    public void updateAlive(List<ProductClassDto> nameList) {
        List<ProductClass> productClasses = productClassDao.list(nameList.stream()
                .map(ProductClassDto::getName)
                .toList());
        if (productClasses.isEmpty()) {
            throw WebErrorEnum.PRODUCT_CLASS_NOT_FOUND.exception();
        }
        for (ProductClass productClass : productClasses) {
            productClass.setAlive(nameList.stream()
                    .filter(productClassDto -> productClassDto.getName().equals(productClass.getName()))
                    .findFirst()
                    .map(ProductClassDto::getAlive)
                    .orElseThrow(WebErrorEnum.PRODUCT_CLASS_NOT_FOUND::exception));
            productClassDao.save(productClass);
        }
    }

    private void ensureNoExistingProductClasses(List<String> nameList) {
        List<String> list = this.listAll(nameList);
        if (!list.isEmpty()) {
            throw WebErrorEnum.PRODUCT_CLASS_ALREADY_EXISTS.exception(list.stream().map(name -> name + ";").collect(Collectors.joining()));
        }
    }

    private void save(List<String> nameList) {
        List<ProductClass> productClasses = nameList.stream()
                .map(name -> ProductClass.builder()
                        .name(name)
                        .alive(false)
                        .build())
                .collect(Collectors.toList());
        productClassDao.saveAll(productClasses);
    }
}