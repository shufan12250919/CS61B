package byog.Core;

import java.util.Random;

public class GenerateMap {
    public static void main(String[] args) {
        //Random r = new Random(Integer.parseInt(args[0]));
        Random r = new Random();
        r = new Random();
        int w = r.nextInt(30) + 30;
        int h = r.nextInt(30) + 30;
        Map map = new Map(w, h, r);
        map.present();
    }
}
