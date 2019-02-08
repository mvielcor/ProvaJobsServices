package mviel.pmdm.provajobsservices;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    private Switch swSoActivat;
    private  JobInfo jobInfoObj;
    private JobScheduler jobScheduler;
    private ComponentName componentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swSoActivat = (Switch)findViewById(R.id.sw_activa_so);
        swSoActivat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(swSoActivat.isChecked()){
                    //Cal activar el so
                    Log.d("Manel","Active el so");
                    cantaPlacesLliuresJobService();

                }else{
                    //S'està desactivant el so
                    Log.d("Manel","DESACTIVE el so");

                    if(jobInfoObj!=null){
                        jobScheduler.cancel(jobInfoObj.getId());

                        Log.d("Manel","servei desactivat "+jobInfoObj.getId());
                    }
                }
            }
        });
    }

    public void cantaPlacesLliuresJobService(){
         jobScheduler = (JobScheduler)getApplicationContext()
                .getSystemService(JOB_SCHEDULER_SERVICE);

          componentName = new ComponentName(getApplicationContext(),
                PlacesLliuresJobService.class);
         jobInfoObj = new JobInfo.Builder(1, componentName)
                 .setMinimumLatency(5000)
                 .setOverrideDeadline(5000)
                 .setBackoffCriteria(1000,jobInfoObj.BACKOFF_POLICY_LINEAR)
                 .build();

         //Log.d("Manel", "criteri"+(jobInfoObj.getBackoffPolicy()==jobInfoObj.BACKOFF_POLICY_LINEAR?" lineal":"exponencial"));
         int resultat_planificacio = jobScheduler.schedule(jobInfoObj);


            if(resultat_planificacio==jobScheduler.RESULT_SUCCESS){
                Log.d("Manel","Tasca planificada");
            }else{
                Log.d("Manel","Error planificant tasca");

            }
    }
}
