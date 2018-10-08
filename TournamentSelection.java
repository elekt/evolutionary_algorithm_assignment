import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TournamentSelection implements Selection {

    private int matingPoolSize; // parameter
    private int numberOfParticipants; // parameter
    private Random rnd;

    public TournamentSelection(int matingPoolSize, int numberOfParticipants) {
        if (matingPoolSize % 2 != 0) {
            throw new IllegalArgumentException("The mating pool size should be an even number");
        }

        this.matingPoolSize = matingPoolSize;
        this.numberOfParticipants = numberOfParticipants;
        rnd = new Random();
    }

    @Override
    public List<Individual> selectIndividuals(List<Individual> individuals, int populationSize) {
        if (matingPoolSize > individuals.size()) {
            throw new IllegalArgumentException("The mating pool size cannot be larger than the population");
        }
        if (matingPoolSize < 2) {
            System.out.println(this.getClass().getName());
            throw new IllegalArgumentException("mating pool should contain at least two parents");
        }

        List<Individual> participants = new ArrayList<>();
        List<Individual> matingPool = new ArrayList<>();

        int currentMember = 1;
        int currentParticipant;

//        System.out.println("______________POPULATION");
//        for (int i = 0; i < individuals.size(); i++) {
//            System.out.println(individuals.get(i).getFitness());
//        }
//        System.out.println("\n");

        while (currentMember <= matingPoolSize) {
            currentParticipant = 1;
            while (currentParticipant <= numberOfParticipants) {
                participants.add(individuals.get(rnd.nextInt(individuals.size())));
                currentParticipant++;
            }

            Collections.sort(participants);

//            System.out.println("______________TOURNAMENT");
//            for (int i = 0; i < participants.size(); i++) {
//                System.out.println(participants.get(i).getFitness());
//
//            }
//            System.out.println("....WINNER:" + participants.get(0).getFitness() + "\n");

            matingPool.add(participants.get(0));
            currentMember++;
            participants.clear();
        }


//        System.out.println("______________MATING POOL");
//        for (int i = 0; i < matingPoolSize; i++) {
//            System.out.println(matingPool.get(i).getFitness());
//
//        }
//        System.out.println("\n");


        return matingPool;
    }
}