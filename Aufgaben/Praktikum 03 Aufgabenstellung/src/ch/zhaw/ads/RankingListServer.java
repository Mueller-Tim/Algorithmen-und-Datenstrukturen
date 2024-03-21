package ch.zhaw.ads;

import java.util.*;

public class RankingListServer implements CommandExecutor {

    public List<Competitor> createList(String rankingText) {
        ArrayList<Competitor> rankingList = new ArrayList<>();
        String[] list = rankingText.split("\n");
        for (String competitor: list){
            String[] competitorSplit = competitor.split(";");
            rankingList.add(new Competitor(0, competitorSplit[0], competitorSplit[1]));
        }
        return rankingList;
    }

    public String createSortedText(List<Competitor> competitorList) {
        StringBuilder sortedText = new StringBuilder();
        LinkedList<Competitor> sortedList = sortList(competitorList);

        for (int i = 0; i < sortedList.size(); i++){
            Competitor competitor = sortedList.get(i);
            competitor.setRank(i);
            sortedText.append(i + 1).append(" ").append(competitor.getName()).append(" ").append(competitor.getTime()).append("\n");
        }

        return sortedText.toString();
    }

    private LinkedList<Competitor> sortList(List<Competitor> competitorList){
        LinkedList<Competitor> sortedList = new LinkedList<>();

        for (Competitor competitor : competitorList){
            boolean competitorAdded = false;
            for (int i = 0; i < sortedList.size() && !competitorAdded; i++){
                if(competitor.compareTo(sortedList.get(i)) < 0){
                    sortedList.add(i, competitor);
                    competitorAdded = true;
                }
            }
            if(!competitorAdded){
                sortedList.add(competitor);
            }
        }
        return  sortedList;
    }

    public String createNameList(List<Competitor> competitorList) {
        StringBuilder nameText = new StringBuilder();
        Comparator<Competitor> comparator = new AlphaComparatorCompetitor();
        LinkedList<Competitor> nameList = new LinkedList<>();
        competitorList = sortList(competitorList);

        for (Competitor competitor : competitorList){
            boolean competitorAdded = false;
            for (int i = 0; i < nameList.size() && !competitorAdded; i++){
                if(comparator.compare(competitor, nameList.get(i)) < 0){
                    nameList.add(i, competitor);
                    competitorAdded = true;
                }
            }
            if(!competitorAdded){
                nameList.add(competitor);
            }
        }

        for (int i = 0; i < nameList.size(); i++){
            Competitor competitor = nameList.get(i);
            nameText.append(0).append(" ").append(competitor.getName()).append(" ").append(competitor.getTime()).append("\n");
        }

        return nameText.toString();
    }

    public String execute(String rankingList) {
        List<Competitor> competitorList = createList(rankingList);
        return "Rangliste\n" + createSortedText(competitorList);
    }
}
