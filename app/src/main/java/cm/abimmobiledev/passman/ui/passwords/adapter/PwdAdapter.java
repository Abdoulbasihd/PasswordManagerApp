package cm.abimmobiledev.passman.ui.passwords.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import cm.abimmobiledev.passman.R;
import cm.abimmobiledev.passman.model.ApplicationModel;

public class PwdAdapter extends Adapter<PwdAdapter.PwdViewHolder> {

    private List<ApplicationModel> applications;

    public PwdAdapter(List<ApplicationModel> applications) {
        this.applications = applications;
    }

    @NonNull
    @Override
    public PwdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_app, parent, false);

        return new PwdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PwdViewHolder holder, int position) {
        ApplicationModel application = applications.get(position);
        
        holder.appName.setText(application.getName());
        holder.appPassword.setText(application.getPassword());
        
        if (application.isSavedOnline()) {
            holder.appSynced.setText("Synced");
        }

        holder.appCard.setOnClickListener(v -> {
            //TODO : opening details ??
            Snackbar.make(holder.appCard.getRootView(), "coming up", Snackbar.LENGTH_LONG).show();
        });

    }

    @Override
    public int getItemCount() {
        return applications.size();
    }

    public static class PwdViewHolder extends RecyclerView.ViewHolder {

        final TextView appName;
        final TextView appPassword;
        final TextView appSynced;
        final ImageView appLogo;
        final ImageView sync;
        final CardView appCard;

        public PwdViewHolder(@NonNull View itemView) {
            super(itemView);

            appName = itemView.findViewById(R.id.app_name);
            appPassword = itemView.findViewById(R.id.app_pass);
            appSynced = itemView.findViewById(R.id.text_if_or_not_off_line);
            appLogo = itemView.findViewById(R.id.app_logo);
            sync = itemView.findViewById(R.id.app_synced_icon);
            appCard = itemView.findViewById(R.id.app_card);
        }
    }
}
