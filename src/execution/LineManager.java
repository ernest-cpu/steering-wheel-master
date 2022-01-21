package execution;

import model.Point;

import java.util.ArrayList;
import java.util.List;

public class LineManager {

    public static ArrayList<Point> transformIntoLine(List<Double> input){
        ArrayList<Point> output = new ArrayList<>();
        for(int i = 0; i<input.size(); i++){
            output.add(new Point(i, input.get(i)));
        }
        return output;
    }

    public static ArrayList<Double> transformIntoArray(ArrayList<Point> input){
        ArrayList<Double> output = new ArrayList<>();
        double maxX = input.get(input.size()-1).getX();
        int lastX = -1;
        for(int i = 0; i<input.size(); i++){
            int x = (int) Math.round(input.get(i).getX());
            if(x==lastX) { // conflict
                double lastValue = input.get(i - 1).getY();
                double newValue = (lastValue + input.get(i).getY()) / 2;
                output.set(x, newValue);
            } else if (x==lastX+1){ // following
                output.add(input.get(i).getY());
                lastX++;
            } else if(x > lastX) { // multiple values
                double lastValue = input.get(i - 1).getY();
                double avgValue = (lastValue + input.get(i).getY()) / 2;
                while (x > lastX) {
                    if  (x==lastX+1) {
                        output.add(input.get(i).getY());
                    } else {
                        output.add(avgValue);
                    }
                    lastX++;
                }
            }
        }
        return output;
    }

}
