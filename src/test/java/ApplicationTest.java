
import com.megamusic.findshow.Application;
import com.megamusic.findshow.dao.AreaRepository;
import com.megamusic.findshow.dao.ArtistRepository;
import com.megamusic.findshow.dao.ResCategoryRepository;
import com.megamusic.findshow.dao.ResContentRepository;
import com.megamusic.findshow.domain.entity.Area;
import com.megamusic.findshow.domain.entity.Artist;
import com.megamusic.findshow.domain.entity.constant.EntityTypeEnum;
import com.megamusic.findshow.service.ArtistService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by maita on 17/6/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)// 指定spring-boot的启动类
public class ApplicationTest {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ResCategoryRepository resCategoryRepository;

    @Autowired
    private ResContentRepository resContentRepository;

    @Autowired
    private AreaRepository areaRepository;



    @Test
    public void test() throws Exception {
        Area area = new Area();
        area.setId(1L);
        area.setCityId(1L);
        area.setName("测试场地");

        area.setTag("我是场地の标签");
        area.setSpTag("我是场地の特性标签");

        area.setAddrDistrict("昌平区");
        area.setAddrStreet("回龙观东大街");
        area.setAddrDesc("北京市昌平区回龙观流星花园3区西门水蜜桃保健会所");
        area.setAddrTag("地址标签-水蜜桃");

        area.setAreaDescription("专注大保健专注大保健专注大保健专注大保健专注大保健专注大保健专注大保健");
        area.setLaunchedActivities("曾举办：各种大保健");

        area.setConferenceNumber(10L);
        area.setNumberOfPersons(999L);
        area.setGuestRoomNumber(99L);
        area.setSquare(998L);

        area.setEquipment("音箱舞台设备,KTV包房,大屏幕,停车场,会议室,客房,餐区,露台,接送车,私人影院,草坪空地,演讲台,室内浴缸,室内有窗,位于地下");
        area.setService("桌游电游,SPA按摩,演绎歌手,视频会议,签到台,定制茶歇,儿童设施,可携带宠物,可吸烟");

        area.setImages("http://p4.music.126.net/o0UdVQj7Ayh_uBot-C0-gg==/18588343581098159.jpg?param=200y200,http://p4.music.126.net/9CDqXJo3jrIsYikSLAwYGQ==/488183162739351.jpg?param=200y200");

        area.setStatus(0);
        area.setCreated(System.currentTimeMillis());
        area.setUpdated(System.currentTimeMillis());
        area.setSort(9);

        areaRepository.save(area);

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

    private String getRandomImage(){
        List<String> list = new ArrayList<String>();
        list.add("http://p4.music.126.net/o0UdVQj7Ayh_uBot-C0-gg==/18588343581098159.jpg?param=200y200");
        list.add("http://p3.music.126.net/zWdB0SlXw35S6adhEP4qXA==/3407386539057275.jpg?param=200y200");
        list.add("http://p4.music.126.net/9CDqXJo3jrIsYikSLAwYGQ==/488183162739351.jpg?param=200y200");
        list.add("http://p3.music.126.net/WR3iJrqfi4gE55go1Hv4AA==/844424930131996.jpg?param=200y200");

        Integer size = list.size();
        Random random = new Random();
        return list.get(random.nextInt(size));
    }


}
