package ch.zhaw.ads;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

public class Competitor implements Comparable<Competitor> {
    private final String name;
    private final String time;
    private int rank;

    public Competitor(int rank, String name, String time)  {
        this.rank = rank;
        this.name = name;
        this.time = time;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    private static long parseTime(String s)  {
        try {
            DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date date = sdf.parse(s);
            return date.getTime();
        } catch (Exception e) {System.err.println(e);}
        return 0;
    }

    private static String timeToString(int time) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(new Date(time));
    }

    public String toString() {
        return ""+ rank + " "+name+" "+time;
    }

    @Override
    public int compareTo(Competitor o) {
        return (int) (parseTime(this.time) - parseTime(o.time));
    }

    @Override
    public boolean equals (Object o) {
        return o instanceof Competitor && this.time == ((Competitor) o).time;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(time);
    }

}

class AlphaComparatorCompetitor implements Comparator<Competitor> {

    @Override
    public int compare(Competitor o1, Competitor o2) {
        int comparison = 0;
        boolean finished = false;
        if(o1.getName().equals(o2.getName())){
            comparison = o1.compareTo(o2);
            finished = true;
        }
        for (int i = 0; !finished && i < o1.getName().length() && i < o2.getName().length(); i++){
            if(o1.getName().charAt(i) != o2.getName().charAt(i)){
                comparison = o1.getName().charAt(i) - o2.getName().charAt(i);
                finished = true;
            }
        }
        return comparison;
    }
}
