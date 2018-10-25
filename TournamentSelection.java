import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TournamentSelection implements Selection {

    private int matingPoolSize; // parameter
    private int numberOfParticipants; // parameter
    private Random rnd;

    public TournamentSelection(int matingPoolSize, int numberOfParticipants) {

        this.matingPoolSize = matingPoolSize;
        this.numberOfParticipants = numberOfParticipants;
        rnd = new Random();
    }

    @Override
    public List<Individual> selectIndividuals(List<Individual> individuals, int populationSize) {

        List<Individual> participants = new ArrayList<>();
        List<Individual> matingPool = new ArrayList<>();

        int currentMember = 1;
        int currentParticipant;


        while (currentMember <= matingPoolSize) {
            currentParticipant = 1;
            while (currentParticipant <= numberOfParticipants) {
                participants.add(individuals.get(rnd.nextInt(individuals.size())));
                currentParticipant++;
            }

            Collections.sort(participants);

            matingPool.add(participants.get(0));
            currentMember++;
            participants.clear();
        }


        return matingPool;
    }
}