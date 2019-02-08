package mviel.pmdm.provajobsservices;


import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;


public class PlacesLliuresJobService extends JobService {
    boolean isWorking = false;
    boolean jobCancelled = false;
    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        Log.d("Manel","Inicie el treball "+jobParameters.getJobId());
        isWorking = true;
        iniciaTascaEnThread(jobParameters);
        return isWorking;
    }

    private void iniciaTascaEnThread(final JobParameters jobParameters) {
        new Thread(new Runnable() {
            public void run() {
                doWork(jobParameters);
            }
        }).start();

    }

    private void doWork(JobParameters jobParameters) {

        Log.d("Manel", "Canteeeeeeee!!");


        isWorking = false;
        boolean needsReschedule = true;
        jobFinished(jobParameters, needsReschedule);
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {

        Log.d("Manel","Acabe el treball "+jobParameters.getJobId());
        jobCancelled = true;
        boolean needsReschedule = isWorking;
        jobFinished(jobParameters, needsReschedule);
        return false;

    }
}


