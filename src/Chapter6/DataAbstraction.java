package Chapter6;

/**
 * 자료 추상화
 */
public class DataAbstraction {

    public class Point {
        public double x;
        public double y;
    }

    public interface PointInterface {
        double getX();
        double getY();
        void setCartesian(double x, double y);
        double getR();
        double getTheta();
        void setPolar(double r, double theta);
    }
}
