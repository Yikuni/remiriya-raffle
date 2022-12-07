import com.yikuni.mc.remiyaraffle.raffle.RaffleChest;
import com.yikuni.mc.remiyaraffle.raffle.RaffleItem;
import org.junit.Test;

import java.lang.reflect.Method;

public class TTest {
    @Test
    public void testClass(){
        Method[] methods = RaffleItem.class.getDeclaredMethods();
        for (Method method: methods){
            System.out.println(method.getName());
        }
    }
}
