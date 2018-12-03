import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.megamusic.findshow.Application;
import com.megamusic.findshow.common.constant.ResourceConstant;
import com.megamusic.findshow.dao.ArtistRepository;
import com.megamusic.findshow.dao.CategoryRepository;
import com.megamusic.findshow.dao.ResCategoryRepository;
import com.megamusic.findshow.dao.ResContentRepository;
import com.megamusic.findshow.domain.entity.Artist;
import com.megamusic.findshow.domain.entity.Category;
import com.megamusic.findshow.domain.entity.ResCategory;
import com.megamusic.findshow.domain.entity.ResContent;
import com.megamusic.findshow.domain.entity.constant.ArtistTypeEnum;
import com.megamusic.findshow.domain.entity.constant.ResContentTypeEnum;
import com.megamusic.findshow.domain.trans.WYArtistData;
import com.megamusic.findshow.domain.trans.WYResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

/**
 * Created by maita on 17/8/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)// 指定spring-boot的启动类
public class AppDataInitTest {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ResCategoryRepository resCategoryRepository;

    @Autowired
    private ResContentRepository resContentRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void importArtistData(){
        String jsonStr = "{\"data\":[{\"info\":\"\",\"id\":29321,\"name\":\"Bob Marley\",\"trans\":\"鲍勃.马利\",\"alias\":[],\"albumSize\":54,\"mvSize\":0,\"picId\":834529325511487,\"picUrl\":\"http://p4.music.126.net/fXEZ4-feJxUYYAweBN-1Iw==/834529325511487.jpg\",\"img1v1Url\":\"http://p3.music.126.net/fXEZ4-feJxUYYAweBN-1Iw==/834529325511487.jpg\"},{\"info\":\"\",\"id\":35439,\"name\":\"John Scofield\",\"trans\":\"约翰.斯科菲尔德\",\"alias\":[],\"albumSize\":42,\"mvSize\":0,\"picId\":280375465104479,\"picUrl\":\"http://p3.music.126.net/oLeRI-0XkGsukaosmVv2hQ==/280375465104479.jpg\",\"img1v1Url\":\"http://p3.music.126.net/oLeRI-0XkGsukaosmVv2hQ==/280375465104479.jpg\"},{\"info\":\"新碟《Between》抢鲜上架\",\"id\":45854,\"name\":\"Wes Montgomery\",\"trans\":\"韦斯.蒙哥马利\",\"alias\":[],\"albumSize\":65,\"mvSize\":0,\"picId\":189115999998792,\"picUrl\":\"http://p3.music.126.net/FYj8oMyBTfZW1rE8YucUQA==/189115999998792.jpg\",\"img1v1Url\":\"http://p3.music.126.net/FYj8oMyBTfZW1rE8YucUQA==/189115999998792.jpg\"},{\"info\":\"\",\"id\":41148,\"name\":\"Pat Metheny\",\"trans\":\"帕特-梅思尼\",\"alias\":[],\"albumSize\":46,\"mvSize\":0,\"picId\":628920651122280,\"picUrl\":\"http://p4.music.126.net/TSnZpGGKAy28JIx3c5tPjg==/628920651122280.jpg\",\"img1v1Url\":\"http://p3.music.126.net/TSnZpGGKAy28JIx3c5tPjg==/628920651122280.jpg\"},{\"info\":\"新碟《永远都会在》抢鲜上架\",\"id\":12111,\"name\":\"旅行团\",\"trans\":null,\"alias\":[],\"albumSize\":16,\"mvSize\":9,\"picId\":18770862511360987,\"picUrl\":\"http://p3.music.126.net/hHpJ6SfH4D2bh3FLMAkiNA==/18770862511360987.jpg\",\"img1v1Url\":\"http://p4.music.126.net/hHpJ6SfH4D2bh3FLMAkiNA==/18770862511360987.jpg\"},{\"info\":\"新碟《Who You Selling For》抢鲜上架\",\"id\":102738,\"name\":\"The Pretty Reckless\",\"trans\":null,\"alias\":[],\"albumSize\":14,\"mvSize\":18,\"picId\":134140418605382,\"picUrl\":\"http://p4.music.126.net/A0xDl9OA1DpZiqH7ZjBisw==/134140418605382.jpg\",\"img1v1Url\":\"http://p3.music.126.net/A0xDl9OA1DpZiqH7ZjBisw==/134140418605382.jpg\"},{\"info\":\"新碟《Fake Happy (Edit)》抢鲜上架\",\"id\":41129,\"name\":\"Paramore\",\"trans\":null,\"alias\":[],\"albumSize\":35,\"mvSize\":22,\"picId\":377132488334070,\"picUrl\":\"http://p4.music.126.net/AlLOUqWQ8TVhCFTPK9u6WQ==/377132488334070.jpg\",\"img1v1Url\":\"http://p4.music.126.net/AlLOUqWQ8TVhCFTPK9u6WQ==/377132488334070.jpg\"},{\"info\":\"新碟《OK Computer OKNOTOK 1997 2017》抢鲜上架\",\"id\":99384,\"name\":\"Radiohead\",\"trans\":\"电台司令\",\"alias\":[\"收音机头\",\"无线电迷\"],\"albumSize\":61,\"mvSize\":11,\"picId\":242992069755712,\"picUrl\":\"http://p4.music.126.net/wfy9mtzfAMvn_I8Vnig9sg==/242992069755712.jpg\",\"img1v1Url\":\"http://p3.music.126.net/wfy9mtzfAMvn_I8Vnig9sg==/242992069755712.jpg\"},{\"info\":\"新碟《Madness》抢鲜上架\",\"id\":27639,\"name\":\"All That Remains\",\"trans\":null,\"alias\":[],\"albumSize\":14,\"mvSize\":8,\"picId\":214404767433799,\"picUrl\":\"http://p4.music.126.net/uNdaDUfZfP6L2uX8nJS2-A==/214404767433799.jpg\",\"img1v1Url\":\"http://p4.music.126.net/uNdaDUfZfP6L2uX8nJS2-A==/214404767433799.jpg\"},{\"info\":\"新碟《The Duke》抢鲜上架\",\"id\":38134,\"name\":\"Lamb of God\",\"trans\":\"上帝的羔羊\",\"alias\":[],\"albumSize\":18,\"mvSize\":12,\"picId\":292470092995116,\"picUrl\":\"http://p3.music.126.net/iCL7MyXDF1UPL0SLwH7urg==/292470092995116.jpg\",\"img1v1Url\":\"http://p3.music.126.net/iCL7MyXDF1UPL0SLwH7urg==/292470092995116.jpg\"},{\"info\":\"新碟《The Absolute》抢鲜上架\",\"id\":90521,\"name\":\"Dark Tranquillity\",\"trans\":null,\"alias\":[],\"albumSize\":30,\"mvSize\":0,\"picId\":764160581316010,\"picUrl\":\"http://p3.music.126.net/E6ddUNJ38u79fIwBRFEzqQ==/764160581316010.jpg\",\"img1v1Url\":\"http://p3.music.126.net/E6ddUNJ38u79fIwBRFEzqQ==/764160581316010.jpg\"},{\"info\":\"\",\"id\":12877,\"name\":\"丝绒公路\",\"trans\":null,\"alias\":[\"velvetroad\"],\"albumSize\":2,\"mvSize\":0,\"picId\":18671906464213137,\"picUrl\":\"http://p3.music.126.net/GcGObM0W2H-85xbDxNa7oQ==/18671906464213137.jpg\",\"img1v1Url\":\"http://p3.music.126.net/GcGObM0W2H-85xbDxNa7oQ==/18671906464213137.jpg\"},{\"info\":\"新碟《Breakin' Outta Hell》抢鲜上架\",\"id\":85659,\"name\":\"Airbourne\",\"trans\":null,\"alias\":[],\"albumSize\":8,\"mvSize\":1,\"picId\":327654465077266,\"picUrl\":\"http://p3.music.126.net/kiIRJu3SoVrvhreYI2OChQ==/327654465077266.jpg\",\"img1v1Url\":\"http://p4.music.126.net/kiIRJu3SoVrvhreYI2OChQ==/327654465077266.jpg\"},{\"info\":\"\",\"id\":99997,\"name\":\"Skid Row\",\"trans\":\"穷街乐队\",\"alias\":[],\"albumSize\":14,\"mvSize\":1,\"picId\":5932964743916456,\"picUrl\":\"http://p3.music.126.net/k_bkl0lOSDJ0EbqeRfQlhQ==/5932964743916456.jpg\",\"img1v1Url\":\"http://p4.music.126.net/k_bkl0lOSDJ0EbqeRfQlhQ==/5932964743916456.jpg\"},{\"info\":\"\",\"id\":99989,\"name\":\"Sum 41\",\"trans\":null,\"alias\":[],\"albumSize\":32,\"mvSize\":10,\"picId\":277076930217020,\"picUrl\":\"http://p3.music.126.net/cPwZ9GpwptoXEjIu8sQKoA==/277076930217020.jpg\",\"img1v1Url\":\"http://p3.music.126.net/cPwZ9GpwptoXEjIu8sQKoA==/277076930217020.jpg\"},{\"info\":\"\",\"id\":48450,\"name\":\"Bathory\",\"trans\":null,\"alias\":[],\"albumSize\":18,\"mvSize\":0,\"picId\":5953855464844606,\"picUrl\":\"http://p4.music.126.net/DGVvGmqU1eR2heiKyjID2w==/5953855464844606.jpg\",\"img1v1Url\":\"http://p3.music.126.net/DGVvGmqU1eR2heiKyjID2w==/5953855464844606.jpg\"},{\"info\":\"\",\"id\":91723,\"name\":\"Empyrium\",\"trans\":null,\"alias\":[],\"albumSize\":10,\"mvSize\":0,\"picId\":5952755953218931,\"picUrl\":\"http://p4.music.126.net/5y1zJs3t5J3WKO7Yc-NJ6w==/5952755953218931.jpg\",\"img1v1Url\":\"http://p4.music.126.net/5y1zJs3t5J3WKO7Yc-NJ6w==/5952755953218931.jpg\"},{\"info\":\"\",\"id\":42940,\"name\":\"Stratovarius\",\"trans\":\"灵云乐队\",\"alias\":[],\"albumSize\":33,\"mvSize\":0,\"picId\":219902325576224,\"picUrl\":\"http://p3.music.126.net/49uvRTKN2U6BN3x2KRzU1w==/219902325576224.jpg\",\"img1v1Url\":\"http://p4.music.126.net/49uvRTKN2U6BN3x2KRzU1w==/219902325576224.jpg\"},{\"info\":\"\",\"id\":78203,\"name\":\"Therion\",\"trans\":\"圣兽乐队\",\"alias\":[],\"albumSize\":32,\"mvSize\":0,\"picId\":3435973836817977,\"picUrl\":\"http://p3.music.126.net/jwPGBQaLdCa7CWuwH5nFTA==/3435973836817977.jpg\",\"img1v1Url\":\"http://p4.music.126.net/jwPGBQaLdCa7CWuwH5nFTA==/3435973836817977.jpg\"},{\"info\":\"\",\"id\":42963,\"name\":\"Summoning\",\"trans\":null,\"alias\":[],\"albumSize\":9,\"mvSize\":0,\"picId\":5941760836928461,\"picUrl\":\"http://p4.music.126.net/3iHg0SARKxZh-1I41H_Ikw==/5941760836928461.jpg\",\"img1v1Url\":\"http://p4.music.126.net/3iHg0SARKxZh-1I41H_Ikw==/5941760836928461.jpg\"},{\"info\":\"\",\"id\":32674,\"name\":\"Eternal Tears of Sorrow\",\"trans\":null,\"alias\":[],\"albumSize\":9,\"mvSize\":0,\"picId\":752065953410799,\"picUrl\":\"http://p4.music.126.net/37HBDcPl72efvwTKzHnU0A==/752065953410799.jpg\",\"img1v1Url\":\"http://p3.music.126.net/37HBDcPl72efvwTKzHnU0A==/752065953410799.jpg\"},{\"info\":\"\",\"id\":48493,\"name\":\"Behemoth\",\"trans\":null,\"alias\":[],\"albumSize\":28,\"mvSize\":3,\"picId\":583840674354993,\"picUrl\":\"http://p3.music.126.net/oxO49gv74ShhoqL5LnETuA==/583840674354993.jpg\",\"img1v1Url\":\"http://p3.music.126.net/oxO49gv74ShhoqL5LnETuA==/583840674354993.jpg\"},{\"info\":\"\",\"id\":100047,\"name\":\"Shadows Fall\",\"trans\":\"阴影降临\",\"alias\":[],\"albumSize\":10,\"mvSize\":0,\"picId\":660806488305444,\"picUrl\":\"http://p4.music.126.net/cUXJXzIzG2_uaa5W0RcZHQ==/660806488305444.jpg\",\"img1v1Url\":\"http://p4.music.126.net/cUXJXzIzG2_uaa5W0RcZHQ==/660806488305444.jpg\"},{\"info\":\"新碟《Don't Need You》抢鲜上架\",\"id\":88159,\"name\":\"Bullet for My Valentine\",\"trans\":\"致命情人\",\"alias\":[],\"albumSize\":32,\"mvSize\":4,\"picId\":340848604622005,\"picUrl\":\"http://p4.music.126.net/-cRCt1T4egFUoOmpsAsJzg==/340848604622005.jpg\",\"img1v1Url\":\"http://p3.music.126.net/-cRCt1T4egFUoOmpsAsJzg==/340848604622005.jpg\"},{\"info\":\"\",\"id\":85554,\"name\":\"As I Lay Dying\",\"trans\":\"在我弥留之际\",\"alias\":[],\"albumSize\":11,\"mvSize\":2,\"picId\":745468883638663,\"picUrl\":\"http://p4.music.126.net/4rMzFqH6_DEj9hm081N3xw==/745468883638663.jpg\",\"img1v1Url\":\"http://p3.music.126.net/4rMzFqH6_DEj9hm081N3xw==/745468883638663.jpg\"}],\"hasMore\":true,\"code\":200}";
        WYResponse response = (WYResponse) JSON.parseObject(jsonStr, new TypeReference<WYResponse>() {});
        for( WYArtistData wyArtistData :response.getData() ){
            Artist artist = new Artist();
            artist.setName(wyArtistData.getName());
            artist.setCover(wyArtistData.getPicUrl());
            artist.setDescription("");
            artist.setCreated(System.currentTimeMillis());
            artist.setUpdated(System.currentTimeMillis());
            artist.setType(ArtistTypeEnum.ARTIST);
            artistRepository.save(artist);
        }
    }

    @Test
    public void addResourceCategory(){
        ResCategory resCategory = new ResCategory();
        resCategory.setCreated(System.currentTimeMillis());
        resCategory.setUpdated(System.currentTimeMillis());
        resCategory.setName("首页banner");
        resCategoryRepository.save(resCategory);

        ResCategory resCategory1 = new ResCategory();
        resCategory1.setCreated(System.currentTimeMillis());
        resCategory1.setUpdated(System.currentTimeMillis());
        resCategory1.setName("首页推荐数据");
        resCategoryRepository.save(resCategory1);
    }

    @Test
    public void addResourceContent(){
        ResContent resContentBanner1 = new ResContent();
        resContentBanner1.setCategoryId(ResourceConstant.INDEX_BANNER_CATEGORY_ID);
        resContentBanner1.setContentType(ResContentTypeEnum.H5);
        resContentBanner1.setTitle("SCANDAL");
        resContentBanner1.setContent("http://music.163.com/#/artist?id=21324");
        resContentBanner1.setCreated(System.currentTimeMillis());
        resContentBanner1.setUpdated(System.currentTimeMillis());
        resContentBanner1.setImage("http://p4.music.126.net/4PddpEZXtQ1XHVtP-Wo8fQ==/3396391422778665.jpg?param=640y300");

        ResContent resContentBanner2 = new ResContent();
        resContentBanner2.setCategoryId(ResourceConstant.INDEX_BANNER_CATEGORY_ID);
        resContentBanner2.setContentType(ResContentTypeEnum.H5);
        resContentBanner2.setTitle("Vinnie Vincent Invasion");
        resContentBanner2.setContent("http://music.163.com/#/artist?id=83777");
        resContentBanner2.setCreated(System.currentTimeMillis());
        resContentBanner2.setUpdated(System.currentTimeMillis());
        resContentBanner2.setImage("http://p4.music.126.net/F2Y3NkUf2486fgspymeeZg==/641015279005540.jpg?param=640y300");

        ResContent resContentBanner3 = new ResContent();
        resContentBanner3.setCategoryId(ResourceConstant.INDEX_BANNER_CATEGORY_ID);
        resContentBanner3.setContentType(ResContentTypeEnum.H5);
        resContentBanner3.setTitle("The Stone Roses");
        resContentBanner3.setContent("http://music.163.com/#/artist?id=102012");
        resContentBanner3.setCreated(System.currentTimeMillis());
        resContentBanner3.setUpdated(System.currentTimeMillis());
        resContentBanner3.setImage("http://p3.music.126.net/qSnf9OW4Mv3eoKHalQdZPQ==/336450558106551.jpg?param=640y300");
        resContentRepository.save(resContentBanner1);
        resContentRepository.save(resContentBanner2);
        resContentRepository.save(resContentBanner3);

//        for ( int i=0;i<15;i++ ){
//            ResContent resContentList = new ResContent();
//            resContentList.setCategoryId(ResourceConstant.INDEX_RECOMMEND_DATA_RESCOURE_ID);
//            resContentList.setContentType(ResContentTypeEnum.ARTIST);
//            resContentList.setTitle("Metallica"+getRandomString(4)+i);
//            resContentList.setContent("http://music.163.com/#/artist?id=38851");
//            resContentList.setCreated(System.currentTimeMillis());
//            resContentList.setUpdated(System.currentTimeMillis());
//            resContentList.setImage("http://p3.music.126.net/0_V3ZaFbEc4PPf_HPmkmCQ==/501377302267748.jpg?param=200y200");
//            resContentRepository.save(resContentList);
//        }
    }

    @Test
    public void addCategory(){
        /**
         * 流行歌手，流行乐队，主持礼仪，舞蹈模特，
         器乐乐团，摄影摄像，音响灯光，合作场地，
         舞蹈模特，活动策划，艺术私教，全部演绎

         摇滚乐队、爵士乐队、独立音乐、外籍艺人、
         民族歌手、魔术杂技、器乐独奏、民间艺术、
         合作机构、幕后制作、夜场嘉宾、直播网红、
         其他演绎、化妆造型、剧团剧院、助理兼职
         */

        Long time = System.currentTimeMillis();
        Category category6 = new Category();
        category6.setName("爵士乐队");
        category6.setCreated(time);
        category6.setUpdated(time);
        categoryRepository.save(category6);

        Category category7 = new Category();
        category7.setName("外籍艺人");
        category7.setCreated(time);
        category7.setUpdated(time);
        categoryRepository.save(category7);



        Category category8 = new Category();
        category8.setName("民族歌手");
        category8.setCreated(time);
        category8.setUpdated(time);
        categoryRepository.save(category8);


        Category category9 = new Category();
        category9.setName("魔术杂技");
        category9.setCreated(time);
        category9.setUpdated(time);
        categoryRepository.save(category9);

        Category category10 = new Category();
        category10.setName("器乐独奏");
        category10.setCreated(time);
        category10.setUpdated(time);
        categoryRepository.save(category10);


        Category category11 = new Category();
        category11.setName("民间艺术");
        category11.setCreated(time);
        category11.setUpdated(time);
        categoryRepository.save(category11);

        Category category12 = new Category();
        category12.setName("夜场嘉宾");
        category12.setCreated(time);
        category12.setUpdated(time);
        categoryRepository.save(category12);


//        Long time = System.currentTimeMillis();
//        Category category = new Category();
//        category.setName("摇滚乐队");
//        category.setCreated(time);
//        category.setUpdated(time);
//        categoryRepository.save(category);
//
//        Category category2 = new Category();
//        category2.setName("独立音乐");
//        category2.setCreated(time);
//        category2.setUpdated(time);
//        categoryRepository.save(category2);
//
//        Category category3 = new Category();
//        category3.setName("器乐乐团");
//        category3.setCreated(time);
//        category3.setUpdated(time);
//        categoryRepository.save(category3);
//
//        Category category4 = new Category();
//        category4.setName("流行歌手");
//        category4.setCreated(time);
//        category4.setUpdated(time);
//        categoryRepository.save(category4);
//
//        Category category5 = new Category();
//        category5.setName("流行乐队");
//        category5.setCreated(time);
//        category5.setUpdated(time);
//        categoryRepository.save(category5);

    }


    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
