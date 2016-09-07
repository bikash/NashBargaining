package example.NashBargaining;

import java.util.Random;

/**
 * Represents a Poisson Process that models the probability of an event
 * happens a number of times in a given time interval.
 * 
 * @see https://en.wikipedia.org/wiki/Poisson_distribution
 * @see https://en.wikipedia.org/wiki/Poisson_point_process
 * 
 * @author Manoel Campos da Silva Filho
 */
public class PoissonProcess {
    /**
     * @see #getLambda() 
     */
    private double lambda;
    
    /**
     * @see #getK() 
     */
    private int k;
    
    /**
     * Uniform random number generator.
     */
    private final Random rand;
    
    /**
     * Creates a new Poisson process that considers you want to check
     * the probability of 1 event ({@link #getK() k}) to happen at each time.
     * 
     * @param lambda average number of events by interval
     * @param seed the seed to initialize the uniform random number generator
     * @see #setK(int) 
     */
    public PoissonProcess(double lambda, long seed){
        this.k = 1;
        this.setLambda(lambda);
        this.rand = new Random(seed);
    }
    
    /**
     * 
     * @return the average number of events that happen at each single time unit. 
     * It is also called the event rate or rate parameter.
     * 
     * If the unit is minute, it means the average number of arrivals
     * at each minute.
     */
    public double getLambda(){
        return lambda;
    }
    
    /**
     * Sets the average number of events that happen at each single time unit.  
     * @param lambda the value to set
     */
    private void setLambda(double lambda) {
        this.lambda = lambda;
    }
    
    /**
     * Computes the factorial of a number n
     * @param n the number to compute the factorial
     * @return the factorial of n
     */
    public long factorial(int n){
        long result = 1;
        for(int i = n; i >= 1; i--) {
            result *= i;
        }
        
        return result;
    }
    
    /**
     * 
     * @return the probability to arrive {@link #getK() K} events in the current time.
     * @see https://en.wikipedia.org/wiki/Poisson_distribution
     */
    public double probabilityToArriveNextKEvents(){
        //computes the Probability Mass Function (PMF) of the Poisson distribution
        return (Math.pow(getLambda(), k)*Math.exp(-getLambda()))/factorial(k);    
    }
    
    /**
     * Indicates if at the current time, {@link #getK() K} events have happened,
     * considering the probability of these K events to happen
     * in a time interval.
     * 
     * @return true if the K events have happened at current time, false otherwise
     * @see #probabilityOfKEventsToHappen() 
     */
    public boolean haveKEventsHappened(){
        return rand.nextDouble() >= probabilityToArriveNextKEvents();
    }

    /**
     * 
     * @return the number of events to check the probability to happen
     * in a time interval.
     */
    public int getK() {
        return k;
    }

    /**
     * Sets the number of events to check the probability to happen
     * in a time time.
     * @param k the value to set
     */
    public void setK(int k) {
        this.k = k;
    }
    
    /**
     * 
     * @return the mean time between arrival of two events.
     */
    public double getInterarrivalMeanTime(){
        final double oneMinute = 1.0;
        return oneMinute/lambda;
    }
    
    /**
     * Time length of each simulation in minutes.
    */
    private static final int SIMULATION_TIME_LENGHT = 25;
    
    /**
     * Number of simulations to run.
     */
    private static final int NUMBER_OF_SIMULATIONS = 1000;

    /**
     * Average number of customers that arrives per minute.
     * The value of 0.4 customers per minute means that 1 customer will arrive 
     * at every 2.5 minutes.
     * It means that 1 minute / 0.4 customer per minute = 1 customer at every 2.5 minutes.
     * This is the interarrival time (in average).
     */
    private static final double MEAN_CUSTOMERS_ARRIVAL_PER_MINUTE=0.4; 

    /**
     * Starts simulations of customers arrivals in a Poisson process.
     * 
     * @param args 
     */
    public static void main(String args[]){
        int customersArrivedInAllSimulations = 0;
        PoissonProcess poisson = null;
        for(int i = 0; i < NUMBER_OF_SIMULATIONS; i++){
            long seed=System.currentTimeMillis();
            poisson = new PoissonProcess(MEAN_CUSTOMERS_ARRIVAL_PER_MINUTE, seed);
            System.out.printf("Simulation number %d\n", i);
            customersArrivedInAllSimulations += runSimulation(poisson, false);
        }
        
        double mean = customersArrivedInAllSimulations/(double)NUMBER_OF_SIMULATIONS;
        System.out.printf("\nArrived customers average after %d simulations: %.2f\n",
                NUMBER_OF_SIMULATIONS, mean);
        if(poisson != null){
            System.out.printf(
                "%.2f customers expected by each %d minutes of simulation with interarrival time of %.2f minutes\n",
                 poisson.getLambda()*SIMULATION_TIME_LENGHT, SIMULATION_TIME_LENGHT, 
                 poisson.getInterarrivalMeanTime());
        }        
    }

    /**
     * Simulates the arrival of customers for a given time period.
     * 
     * @param poisson the PoissonProcess object that will compute the customer arrivals probabilities
     * @param showCustomerArrivals if the arrival of each customer has to be shown
     * @return the number of arrived customers
     */
    private static int runSimulation(PoissonProcess poisson, boolean showCustomerArrivals) {
        int arrivedCustomers = 0;
        
        /*We want to check the probability of 1 customer to arrive at each
        single minute. The default k value is 1, so we dont need to set it.*/
        for(int minute = 0; minute < SIMULATION_TIME_LENGHT; minute++){
            if(poisson.haveKEventsHappened()){
                arrivedCustomers += poisson.getK();
                if (showCustomerArrivals) {
                    System.out.printf(
                        "%d customers arrived at minute %d\n",
                        poisson.getK(), minute,  poisson.probabilityToArriveNextKEvents());
                }
            }
        }
        
        System.out.printf(
                "\t%d customers arrived in %d minutes\n", arrivedCustomers, SIMULATION_TIME_LENGHT);
        System.out.printf("\tArrival rate: %.2f customers per minute. Customers interarrival time: %.2f minutes in average\n", 
                poisson.getLambda(), poisson.getInterarrivalMeanTime());
        
        return arrivedCustomers;
    }
}
