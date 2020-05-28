package site.teamo.mall.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.teamo.mall.bean.Items;
import site.teamo.mall.bean.ItemsImg;
import site.teamo.mall.bean.ItemsParam;
import site.teamo.mall.bean.ItemsSpec;
import site.teamo.mall.bean.vo.ItemInfoVO;
import site.teamo.mall.common.util.MallJSONResult;
import site.teamo.mall.service.ItemService;

import java.util.List;

@Api(value = "商品接口",tags = "商品接口")
@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "商品详情",notes = "商品详情")
    @GetMapping("/info/{itemId}")
    public MallJSONResult sixNewItems(@ApiParam(name = "itemId",value = "商品id",required = true) @PathVariable String itemId){
        if(StringUtils.isBlank(itemId)){
            return MallJSONResult.errorMsg("商品不存在");
        }
        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgList = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecList = itemService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);
        return MallJSONResult.ok(
                ItemInfoVO.builder()
                        .item(item)
                        .itemImgList(itemsImgList)
                        .itemSpecList(itemsSpecList)
                        .itemParams(itemsParam)
                        .build()
        );
    }

    @ApiOperation(value = "不同等级商品评价数量",notes = "不同等级商品评价数量")
    @GetMapping("/commentLevel")
    public MallJSONResult commentLevel(@ApiParam(name = "itemId",value = "商品id",required = true) @RequestParam String itemId){
        if(StringUtils.isBlank(itemId)){
            return MallJSONResult.errorMsg("商品不存在");
        }
        return MallJSONResult.ok(itemService.queryCommentCount(itemId));
    }

    @ApiOperation(value = "商品评价",notes = "商品评价")
    @GetMapping("/comments")
    public MallJSONResult comments(
            @ApiParam(name = "itemId",value = "商品id",required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level",value = "评价等级",required = false)
            @RequestParam Integer level,
            @ApiParam(name = "page",defaultValue = "1",value = "页码",required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize",defaultValue = "10",value = "页面大小",required = false)
            @RequestParam Integer pageSize){
        if(StringUtils.isBlank(itemId)){
            return MallJSONResult.errorMsg("商品不存在");
        }
        return MallJSONResult.ok(itemService.queryPagedItemComments(itemId,level,page,pageSize));
    }


    @ApiOperation(value = "商品搜索",notes = "商品搜索")
    @GetMapping("/search")
    public MallJSONResult search(
            @ApiParam(name = "keywords",value = "关键字",required = true)
            @RequestParam String keywords,
            @ApiParam(name = "sort",value = "排序方式",required = false)
            @RequestParam String sort,
            @ApiParam(name = "page",defaultValue = "1",value = "页码",required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize",defaultValue = "20",value = "页面大小",required = false)
            @RequestParam Integer pageSize){
        return MallJSONResult.ok(itemService.searchItems(keywords,sort,page,pageSize));
    }

    @ApiOperation(value = "通过三级分类商品搜索",notes = "通过三级分类商品搜索")
    @GetMapping("/catItems")
    public MallJSONResult catItems(
            @ApiParam(name = "catId",value = "三级分类id",required = true)
            @RequestParam Integer catId,
            @ApiParam(name = "sort",value = "排序方式",required = false)
            @RequestParam String sort,
            @ApiParam(name = "page",defaultValue = "1",value = "页码",required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize",defaultValue = "20",value = "页面大小",required = false)
            @RequestParam Integer pageSize){
        return MallJSONResult.ok(itemService.searchItemsByThirdCat(catId,sort,page,pageSize));
    }

    @ApiOperation(value = "根据商品规格id查询商品",notes = "根据商品规格id查询商品")
    @GetMapping("/refresh")
    public MallJSONResult refresh(
            @ApiParam(name = "itemSpecIds",value = "商品规格id",required = true)
            @RequestParam String itemSpecIds){
        if(StringUtils.isBlank(itemSpecIds)){
            return MallJSONResult.ok();
        }
        return MallJSONResult.ok(itemService.queryItemBySpecIds(itemSpecIds));
    }
}
