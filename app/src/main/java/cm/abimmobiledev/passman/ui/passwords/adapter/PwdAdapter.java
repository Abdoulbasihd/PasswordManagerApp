package cm.abimmobiledev.passman.ui.passwords.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import cm.abimmobiledev.passman.database.entity.User;
import cm.abimmobiledev.passman.model.ApplicationModel;
import cm.abimmobiledev.passman.usefull.Util;

public class PwdAdapter extends Adapter<PwdAdapter.PwdViewHolder> {

    private final List<ApplicationModel> applications;
    private final User ownerUser;

    private final String HIDDEN_PASS_STARS = "**********";
    private final String CLIP_PWD_LABEL = "PASSWORD";
    private final String CLIP_UNAME_LABEL = "USERNAME";
    private boolean pwdIsHidden = true;

    public PwdAdapter(List<ApplicationModel> applications, User owner) {
        this.applications = applications;
        this.ownerUser = owner;
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
        holder.appPassword.setText(HIDDEN_PASS_STARS);
        holder.appUsername.setText(application.getUsername());

        
        if (ownerUser!=null && ownerUser.fullAccount && application.isSavedOnline()) {
            holder.appSynced.setText("Synced");
        } else if (ownerUser!=null && ownerUser.isFullAccount()){
            holder.appSynced.setText("Not Yet Synced");
        } else if (ownerUser!=null) {
            holder.appSynced.setText("Offline Account");
        }

        holder.appCard.setOnClickListener(v -> {
            //TODO : opening details ??
            Snackbar.make(holder.appCard.getRootView(), "coming up", Snackbar.LENGTH_LONG).show();
        });

        holder.copyPassword.setOnClickListener(v -> {
            ClipboardManager clipboardManager = (ClipboardManager) holder.copyPassword.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(CLIP_PWD_LABEL, application.getPassword());
            ClipData clipDataUsername = ClipData.newPlainText(CLIP_UNAME_LABEL, application.getUsername());

            clipboardManager.setPrimaryClip(clipDataUsername);
            clipboardManager.setPrimaryClip(clipData);

            Snackbar.make(holder.appCard.getRootView(), holder.appCard.getContext().getString(R.string.copied), Snackbar.LENGTH_LONG).show();
        });

        holder.hideOrViewPassword.setOnClickListener(v->{
            if (pwdIsHidden){
                pwdIsHidden = false;
                holder.appPassword.setText(application.getPassword());
            }
            else  {
                pwdIsHidden = true;
                holder.appPassword.setText(HIDDEN_PASS_STARS);
            }
        });

    }

    @Override
    public int getItemCount() {
        return applications.size();
    }

    public static class PwdViewHolder extends RecyclerView.ViewHolder {

        final TextView appName;
        final TextView appUsername;
        final TextView appPassword;
        final TextView appSynced;
        final ImageView appLogo;
        final ImageView sync;
        final CardView appCard;
        final ImageView hideOrViewPassword;
        final ImageView copyPassword;

        public PwdViewHolder(@NonNull View itemView) {
            super(itemView);

            appName = itemView.findViewById(R.id.app_name);
            appUsername = itemView.findViewById(R.id.app_username);
            appPassword = itemView.findViewById(R.id.app_pass);
            appSynced = itemView.findViewById(R.id.text_if_or_not_off_line);
            appLogo = itemView.findViewById(R.id.app_logo);
            sync = itemView.findViewById(R.id.app_synced_icon);
            appCard = itemView.findViewById(R.id.app_card);
            hideOrViewPassword = itemView.findViewById(R.id.view_or_hide_pass);
            copyPassword = itemView.findViewById(R.id.copy_password);
        }
    }
}
