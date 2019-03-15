package com.megamusic.findshow.controller.op;

import com.alibaba.fastjson.JSON;
import com.megamusic.findshow.common.constant.SystemConstantsEnum;
import com.megamusic.findshow.common.utils.CommonUtils;
import com.megamusic.findshow.common.utils.ResponseUtils;
import com.megamusic.findshow.dao.ArtistInfoRepository;
import com.megamusic.findshow.dao.ArtistRepository;
import com.megamusic.findshow.domain.Response;
import com.megamusic.findshow.domain.dto.OpArtistRequestDto;
import com.megamusic.findshow.domain.entity.Artist;
import com.megamusic.findshow.domain.entity.ArtistInfo;
import com.megamusic.findshow.domain.entity.constant.ArtistTypeEnum;
import com.megamusic.findshow.domain.op.ImageVo;
import com.megamusic.findshow.domain.op.OpArtistVo;
import com.megamusic.findshow.domain.vo.CategoryVo;
import com.megamusic.findshow.service.CategoryService;
import com.megamusic.findshow.service.op.OpArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengchao on 2018/2/12.
 */
@Controller("opArtist")
@RequestMapping("/op/artist")
public class ArtistController {

    @Autowired
    private OpArtistService artistService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ArtistInfoRepository artistInfoRepository;


    /**
     * 列表页
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list(Integer pageNum){
        if(pageNum==null){
            pageNum = 0;
        }else{
            pageNum = pageNum-1;
        }
        Page<OpArtistVo> page = artistService.getOpArtistList(pageNum,20);
        ModelAndView mv = new ModelAndView("list");
        mv.addObject("dataList", page.getContent());
        mv.addObject("curPage",page.getNumber()+1);
        mv.addObject("total",page.getTotalPages());
        mv.addObject("active","list");
        mv.setViewName("list");
        return mv;
    }

    @RequestMapping("list/getData")
    @ResponseBody
    public Page<OpArtistVo> ajaxList(Integer pageNum){
        if(pageNum==null){
            pageNum = 0;
        }else{
            pageNum = pageNum-1;
        }
        Page<OpArtistVo> page = artistService.getOpArtistList(pageNum,20);
        return page;
    }

    /**
     * 艺人添加
     * @return
     */
    @RequestMapping("add")
    public ModelAndView add(){

        //获取所有分类
        List<CategoryVo> categoryVoList = categoryService.getAllCategory();

        ModelAndView mv = new ModelAndView("addArtist");
        mv.addObject("active","add");
        mv.addObject("cateList",categoryVoList);
        mv.setViewName("addArtist");
        return mv;
    }

    /**
     * 保存艺人信息
     * @return
     */
    @RequestMapping(value = "addDo", method = { RequestMethod.POST })
    @ResponseBody
    public Response addDo(@RequestBody OpArtistRequestDto opArtistRequestDto){
        Artist artist = new Artist();
        artist.setName(opArtistRequestDto.getName());
        artist.setDescription(opArtistRequestDto.getDescription());
        artist.setVideoInfo(opArtistRequestDto.getVideo());
        artist.setCreated(System.currentTimeMillis());
        artist.setUpdated(System.currentTimeMillis());
        artist.setType(ArtistTypeEnum.ARTIST.getCode());
        artist.setSort(opArtistRequestDto.getSort());
        artist.setContactPrice(new BigDecimal(5)); // 此处默认查看联系方式价格为5.00元
        artist.setShortDesc(opArtistRequestDto.getShortDesc());
        artist.setShortDesc2(opArtistRequestDto.getShortDesc2());
        artist.setTipTag(opArtistRequestDto.getTipTag());

        if( !StringUtils.isEmpty(opArtistRequestDto.getCateId()) )
            artist.setCategoryId(Long.valueOf(opArtistRequestDto.getCateId()));

        artist.setCover(opArtistRequestDto.getAvatar());
        if( !CollectionUtils.isEmpty(opArtistRequestDto.getImages()) ){
                StringBuffer imgSb = new StringBuffer();
                for(int i = 0; i< opArtistRequestDto.getImages().size(); i++ ){
                    imgSb.append(opArtistRequestDto.getImages().get(i));
                    if(i!= opArtistRequestDto.getImages().size()-1){
                        imgSb.append(",");
                    }
                }
                artist.setImages(imgSb.toString());


        }

        artist.setAge(opArtistRequestDto.getAge());
        artist.setCityId(Integer.valueOf(opArtistRequestDto.getCityId()));
        artist.setPrice(opArtistRequestDto.getPrice());
        artist.setPopularity(opArtistRequestDto.getPopularity());
        artistRepository.save(artist);


        //艺人相关信息
        ArtistInfo artistInfo = new ArtistInfo();
        artistInfo.setArtistId(artist.getId());
        artistInfo.setWeight(opArtistRequestDto.getWeight());
        artistInfo.setHeight(opArtistRequestDto.getHeight());
        artistInfo.setFans(opArtistRequestDto.getFans());
        artistInfo.setMajor(opArtistRequestDto.getMajor());
        artistInfo.setStyle(opArtistRequestDto.getStyle());

        artistInfo.setWeixin(opArtistRequestDto.getWeixin());
        artistInfo.setWeibo(opArtistRequestDto.getWeibo());
        artistInfo.setMobile(opArtistRequestDto.getMobile());
        artistInfo.setDouyin(opArtistRequestDto.getDouyin());

        if(!CollectionUtils.isEmpty(opArtistRequestDto.getExperience()))
            artistInfo.setArtistExperience(JSON.toJSON(opArtistRequestDto.getExperience()).toString());
        if(!CollectionUtils.isEmpty(opArtistRequestDto.getNews()))
            artistInfo.setArtistNews(JSON.toJSON(opArtistRequestDto.getNews()).toString());

        artistInfo.setUpdated(System.currentTimeMillis());
        artistInfo.setCreated(System.currentTimeMillis());
        artistInfoRepository.save(artistInfo);
        //return "redirect:/op/artist/list";
        return ResponseUtils.getSuccessResponse("success");
    }


    /**
     * 艺人编辑
     * @return
     */
    @RequestMapping("edit")
    public ModelAndView edit(String aid){
        //获取所有分类
        List<CategoryVo> categoryVoList = categoryService.getAllCategory();

        Artist artist = artistRepository.findOne(Long.valueOf(aid));
        ArtistInfo artistInfo = artistInfoRepository.findByArtistId(Long.valueOf(aid));

        ModelAndView mv = new ModelAndView("editArtist");
        mv.addObject("submenu","editArtist");
        mv.addObject("cateList",categoryVoList);
        mv.addObject("artist",artist);
        if( artistInfo!=null ){
            mv.addObject("news",JSON.parseObject(artistInfo.getArtistNews(),List.class));
            mv.addObject("experience",JSON.parseObject(artistInfo.getArtistExperience(),List.class));
            mv.addObject("artistInfo",artistInfo);
        } else {
            mv.addObject("artistInfo",new ArtistInfo());
        }


        List<ImageVo> imageVoList = new ArrayList<ImageVo>();
        if( artist!=null &&
                !StringUtils.isEmpty(artist.getImages()) ){
            String[] strImgs = artist.getImages().split(",");
            for( String is:strImgs ){
                ImageVo imageVo = new ImageVo();
                imageVo.setUrl(is);
                imageVo.setId(CommonUtils.MD5(is));
                imageVoList.add(imageVo);
            }
        }
        mv.addObject("imgList",imageVoList);
        mv.addObject("active","edit");
        mv.setViewName("editArtist");
        return mv;
    }

    @RequestMapping(value = "editDo", method = { RequestMethod.POST })
    @ResponseBody
    public Response editDo(@RequestBody OpArtistRequestDto opArtistRequestDto){

        //省事先查出来
        Artist sourceArtist = artistRepository.findOne(opArtistRequestDto.getId());

        sourceArtist.setName(opArtistRequestDto.getName());
        sourceArtist.setDescription(opArtistRequestDto.getDescription());
        sourceArtist.setVideoInfo(opArtistRequestDto.getVideo());
        if(!StringUtils.isEmpty(opArtistRequestDto.getCateId()))
            sourceArtist.setCategoryId(Long.valueOf(opArtistRequestDto.getCateId()));
        if(!StringUtils.isEmpty(opArtistRequestDto.getSort()))
            sourceArtist.setSort(opArtistRequestDto.getSort());

        sourceArtist.setCover(opArtistRequestDto.getAvatar());
        if( !CollectionUtils.isEmpty(opArtistRequestDto.getImages()) ){
            if( opArtistRequestDto.getImages().size()>0 ){
                StringBuffer imgSb = new StringBuffer();
                for(int i = 0; i< opArtistRequestDto.getImages().size(); i++ ){
                    imgSb.append(opArtistRequestDto.getImages().get(i));
                    if(i!= opArtistRequestDto.getImages().size()-1){
                        imgSb.append(",");
                    }
                }
                sourceArtist.setImages(imgSb.toString());
            }

        }
        sourceArtist.setShortDesc(opArtistRequestDto.getShortDesc());
        sourceArtist.setShortDesc2(opArtistRequestDto.getShortDesc2());
        sourceArtist.setTipTag(opArtistRequestDto.getTipTag());
        sourceArtist.setAge(opArtistRequestDto.getAge());
        sourceArtist.setCityId(Integer.valueOf(opArtistRequestDto.getCityId()));
        sourceArtist.setPrice(opArtistRequestDto.getPrice());
        sourceArtist.setPopularity(opArtistRequestDto.getPopularity());
        sourceArtist.setUpdated(System.currentTimeMillis());
        artistRepository.save(sourceArtist);


        //艺人相关信息 没有就新建
        ArtistInfo artistInfo = artistInfoRepository.findByArtistId(sourceArtist.getId());
        if( artistInfo==null ){
            artistInfo = new ArtistInfo();
            artistInfo.setArtistId(sourceArtist.getId());

        }
        artistInfo.setWeight(opArtistRequestDto.getWeight());
        artistInfo.setHeight(opArtistRequestDto.getHeight());
        artistInfo.setFans(opArtistRequestDto.getFans());
        artistInfo.setMajor(opArtistRequestDto.getMajor());
        artistInfo.setStyle(opArtistRequestDto.getStyle());

        artistInfo.setWeixin(opArtistRequestDto.getWeixin());
        artistInfo.setWeibo(opArtistRequestDto.getWeibo());
        artistInfo.setMobile(opArtistRequestDto.getMobile());
        artistInfo.setDouyin(opArtistRequestDto.getDouyin());

        if(!CollectionUtils.isEmpty(opArtistRequestDto.getExperience()))
            artistInfo.setArtistExperience(JSON.toJSON(opArtistRequestDto.getExperience()).toString());
        if(!CollectionUtils.isEmpty(opArtistRequestDto.getNews()))
            artistInfo.setArtistNews(JSON.toJSON(opArtistRequestDto.getNews()).toString());

        artistInfo.setUpdated(System.currentTimeMillis());
        artistInfoRepository.save(artistInfo);

        return ResponseUtils.getSuccessResponse("success");
    }

    /**
     * 删除回调（前端使用）
     * @return
     */
    @RequestMapping("delete/img")
    @ResponseBody
    public Response deleteImg(){
        return ResponseUtils.getSuccessResponse("success");
    }

    /**
     * 删除
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public Response delete(String aid){
        if(StringUtils.isEmpty(aid))
            return ResponseUtils.getErrorResponse(SystemConstantsEnum.PARAM_ERROR);
        artistRepository.delete(Long.valueOf(aid));
        return ResponseUtils.getSuccessResponse("success");
    }

}
