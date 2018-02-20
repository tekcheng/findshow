package com.megamusic.findshow.controller.op;

import com.megamusic.findshow.common.constant.SystemConstantsEnum;
import com.megamusic.findshow.common.utils.CommonUtils;
import com.megamusic.findshow.common.utils.ResponseUtils;
import com.megamusic.findshow.dao.ArtistRepository;
import com.megamusic.findshow.domain.Response;
import com.megamusic.findshow.domain.entity.Artist;
import com.megamusic.findshow.domain.entity.constant.ArtistTypeEnum;
import com.megamusic.findshow.domain.op.ImageVo;
import com.megamusic.findshow.domain.op.OpArtistVo;
import com.megamusic.findshow.domain.vo.CategoryVo;
import com.megamusic.findshow.service.ArtistService;
import com.megamusic.findshow.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengchao on 2018/2/12.
 */
@Controller("opArtist")
@RequestMapping("/op/artist")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArtistRepository artistRepository;





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
     * @param name
     * @param desc
     * @param cateId
     * @param sort
     * @param videos
     * @param images
     * @return
     */
    @RequestMapping(value = "addDo", method = { RequestMethod.POST })
    public String addDo(@RequestParam(name = "artist_name") String name,
                          @RequestParam(name = "artist_desc") String desc,
                          @RequestParam(name = "artist_cate") String cateId,
                          @RequestParam(name = "artist_sort") String sort,
                          @RequestParam(name = "artist_video") String videos,
                          @RequestParam List<String> images ){

        Artist artist = new Artist();
        artist.setName(name);
        artist.setDescription(desc);
        artist.setVideoInfo(videos);
        artist.setCreated(System.currentTimeMillis());
        artist.setUpdated(System.currentTimeMillis());
        artist.setType(ArtistTypeEnum.ARTIST);

        if( !StringUtils.isEmpty(cateId) )
            artist.setCategoryId(Long.valueOf(cateId));

        if( !StringUtils.isEmpty(sort) )
            artist.setSort(Integer.valueOf(sort));

        if( !CollectionUtils.isEmpty(images) ){
            artist.setCover(images.get(0));
            StringBuffer imgSb = new StringBuffer();
            for( int i=0;i<images.size();i++ ){
                imgSb.append(images.get(i));
                if(i!=images.size()-1){
                    imgSb.append(",");
                }
            }
            artist.setImages(imgSb.toString());
        }
        artistRepository.save(artist);

        return "redirect:/op/artist/list";
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

        ModelAndView mv = new ModelAndView("editArtist");
        mv.addObject("submenu","editArtist");
        mv.addObject("cateList",categoryVoList);
        mv.addObject("artist",artist);

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
    public String editDo(@RequestParam(name = "artist_id") String id,
                           @RequestParam(name = "artist_name") String name,
                           @RequestParam(name = "artist_desc") String desc,
                           @RequestParam(name = "artist_cate") String cateId,
                           @RequestParam(name = "artist_sort") String sort,
                           @RequestParam(name = "artist_video") String videos,
                           @RequestParam List<String> images   ){
        //TODO 错误处理
        if(StringUtils.isEmpty(id))
            return "redirect:/op/artist/list";

        //省事先查出来
        Artist sourceArtist = artistRepository.findOne(Long.valueOf(id));

        Artist artist = new Artist();
        artist.setId(Long.valueOf(id));
        artist.setName(name);
        artist.setDescription(desc);
        artist.setVideoInfo(videos);
        if(!StringUtils.isEmpty(cateId))
            artist.setCategoryId(Long.valueOf(cateId));
        if(!StringUtils.isEmpty(sort))
            artist.setSort(Integer.valueOf(sort));
        if( !CollectionUtils.isEmpty(images) ){
            artist.setCover(images.get(0));
            StringBuffer imgSb = new StringBuffer();
            for( int i=0;i<images.size();i++ ){
                imgSb.append(images.get(i));
                if(i!=images.size()-1){
                    imgSb.append(",");
                }
            }
            artist.setImages(imgSb.toString());
        }
        artist.setUpdated(System.currentTimeMillis());
        artist.setCreated(sourceArtist.getCreated());
        artistRepository.save(artist);
        return "redirect:/op/artist/edit?aid="+id;
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
     * 删除回调（前端使用）
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
