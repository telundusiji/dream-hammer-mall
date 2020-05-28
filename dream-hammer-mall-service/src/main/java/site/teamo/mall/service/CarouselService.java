package site.teamo.mall.service;

import site.teamo.mall.bean.Carousel;

import java.util.List;

public interface CarouselService {
    /**
     * 查询所有轮播图列表
     */

    List<Carousel> queryAll(Integer isShow);
}
