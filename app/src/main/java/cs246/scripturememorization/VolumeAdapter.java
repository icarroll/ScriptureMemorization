package cs246.scripturememorization;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class VolumeAdapter extends RecyclerView.Adapter<VolumeAdapter.VolumeAdapterViewHolder> {

    private List<Volumes> volumesList;

    private final VolumeAdapterOnClickHandler clickHandler;

    private Context context;

    public interface VolumeAdapterOnClickHandler {
        void onClick(String volumeUrl, String movieKey);
    }

    public VolumeAdapter(Context context, VolumeAdapterOnClickHandler clickHandler) {
        this.clickHandler = clickHandler;
        this.context = context;
    }

    public class VolumeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView volumeTitleTextView;

        public VolumeAdapterViewHolder(View view) {
            super(view);
            volumeTitleTextView = view.findViewById(R.id.volume_title);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String volumeTitle = volumesList.get(adapterPosition).getVolumeTitle();
            String volumeId = volumesList.get(adapterPosition).getVolumeId();
            clickHandler.onClick(volumeTitle, volumeId);
        }
    }

    @NonNull
    @Override
    public VolumeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();

        int layoutIdForListItem = R.layout.volume_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new VolumeAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull VolumeAdapterViewHolder holder, int position) {
        Volumes currentVolume = volumesList.get(position);
        holder.volumeTitleTextView.setText(currentVolume.getVolumeTitle());
    }

    @Override
    public int getItemCount() {
        if(volumesList == null) {
            return 0;
        }
        return volumesList.size();
    }

    public void setVolumesList(List<Volumes> volumes) {
        volumesList = volumes;
        notifyDataSetChanged();
    }
}
