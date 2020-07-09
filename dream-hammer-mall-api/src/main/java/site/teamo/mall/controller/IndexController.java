package site.teamo.mall.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import site.teamo.mall.bean.Carousel;
import site.teamo.mall.bean.Category;
import site.teamo.mall.bean.vo.CategoryVO;
import site.teamo.mall.bean.vo.NewItemsVO;
import site.teamo.mall.common.enums.YesNo;
import site.teamo.mall.common.util.MallJSONResult;
import site.teamo.mall.service.CarouselService;
import site.teamo.mall.service.CategoryService;

import java.util.List;

@Api(value = "首页", tags = "首页")
@RestController
@RequestMapping("/index")
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    private static final String REDIS_KEY_CAROUSEL = "carousel";
    private static final String REDIS_KEY_CATS = "cats";
    private static final String REDIS_KEY_SUB_CATS = "subCat";

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "轮播图", notes = "轮播图")
    @GetMapping("/carousel")
    public MallJSONResult carousel() {

        String carousel = redisTemplate.opsForValue().get(REDIS_KEY_CAROUSEL);

        if(StringUtils.isNotBlank(carousel)){
            List<Carousel> carousels = JSON.parseArray(carousel, Carousel.class);
            return MallJSONResult.ok(carousels);
        }

        List<Carousel> carousels = carouselService.queryAll(YesNo.YES.type);
        redisTemplate.opsForValue().set(REDIS_KEY_CAROUSEL,JSON.toJSONString(carousels));
        return MallJSONResult.ok(carousels);
    }

    @ApiOperation(value = "商品根分类",notes = "商品根分类")
    @GetMapping("/cats")
    public MallJSONResult cats(){
        String cats = redisTemplate.opsForValue().get(REDIS_KEY_CATS);
        if(StringUtils.isNotBlank(cats)){
            return MallJSONResult.ok(JSON.parseArray(cats,Category.class));
        }
        List<Category> categories = categoryService.queryAllRootLevelCat();
        redisTemplate.opsForValue().set(REDIS_KEY_CATS,JSON.toJSONString(categories));
        return MallJSONResult.ok(categories);
    }

    @ApiOperation(value = "商品子分类",notes = "商品子分类")
    @GetMapping("/subCat/{rootCatId}")
    public MallJSONResult subCat(@ApiParam(name = "rootCatId",value = "一级分类Id",required = true) @PathVariable Integer rootCatId){
        if(rootCatId==null){
            return MallJSONResult.errorMsg("分类不存在");
        }
        String subCats = redisTemplate.opsForValue().get(REDIS_KEY_SUB_CATS + ":" + rootCatId);
        if(StringUtils.isNotBlank(subCats)){
            return MallJSONResult.ok(JSON.parseArray(subCats,CategoryVO.class));
        }
        List<CategoryVO> categories = categoryService.getSubCatList(rootCatId);
        redisTemplate.opsForValue().set(REDIS_KEY_SUB_CATS + ":" + rootCatId,JSON.toJSONString(categories));
        return MallJSONResult.ok(categories);
    }

    @ApiOperation(value = "一级分类下6条最新商品",notes = "一级分类下6条最新商品")
    @GetMapping("/sixNewItems/{rootCatId}")
    public MallJSONResult sixNewItems(@ApiParam(name = "rootCatId",value = "一级分类Id",required = true) @PathVariable Integer rootCatId){
        if(rootCatId==null){
            return MallJSONResult.errorMsg("分类不存在");
        }
        List<NewItemsVO> newItemsVOS = categoryService.getSixNewItemsLazy(rootCatId);
        return MallJSONResult.ok(newItemsVOS);
    }
}
