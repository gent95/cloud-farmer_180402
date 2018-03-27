package test;

import com.jctl.cloud.manager.weather.entity.Weather;
import com.jctl.cloud.manager.weather.service.WeatherService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring/spring-context*.xml"})
public class Test{
    @Autowired
    private WeatherService weatherService;
    @org.junit.Test
    public void test() {
        List<Weather> weathers = weatherService.getTop8Data();
        List<String> date = new LinkedList<>();
        for (int i = 0; i < weathers.size(); i++) {
            date.add(weathers.get(i).getAddTime().toString().split(" ")[3]);
        }
        for (String s : date) {
            System.out.println("~~~~"+s);
        }
    }
}
