
import com.megamusic.findshow.Application;
import com.megamusic.findshow.dao.ArtistRepository;
import com.megamusic.findshow.dao.ResCategoryRepository;
import com.megamusic.findshow.dao.ResContentRepository;
import com.megamusic.findshow.domain.entity.Artist;
import com.megamusic.findshow.domain.entity.constant.ArtistTypeEnum;
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



    @Test
    public void test() throws Exception {

        for( int i=0;i<10;i++ ){
            Artist artist = new Artist();
            String username = getRandomString(5);
            artist.setName(username);
            artist.setType(ArtistTypeEnum.FIELD);
            artist.setDescription(getRandomString(20));
            if( i%2==0 ){
                artist.setSchedule(false);
            }else {
                artist.setSchedule(true);
            }
            artistRepository.save(artist);
        }

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
