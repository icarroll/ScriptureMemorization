package cs246.scripturememorization;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookAdapterViewHolder> {

    private List<Books> booksList;

    private final BookAdapterOnClickHandler clickHandler;

    private Context context;

    public interface BookAdapterOnClickHandler {
        void onClick(String bookId, String bookKey);
    }

    public BookAdapter(Context context, BookAdapterOnClickHandler clickHandler) {
        this.clickHandler = clickHandler;
        this.context = context;
    }

    public class BookAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView bookTitleTextView;

        public BookAdapterViewHolder(View view) {
            super(view);
            bookTitleTextView = view.findViewById(R.id.book_title);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String bookTitle = booksList.get(adapterPosition).getBookTitle();
            String bookId = booksList.get(adapterPosition).getBookId();
            clickHandler.onClick(bookTitle, bookId);
        }
    }

    @NonNull
    @Override
    public BookAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();

        int layoutIdForListItem = R.layout.book_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new BookAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull BookAdapterViewHolder holder, int position) {
        Books currentBook = booksList.get(position);
        holder.bookTitleTextView.setText(currentBook.getBookTitle());
    }

    @Override
    public int getItemCount() {
        if(booksList == null) {
            return 0;
        }
        return booksList.size();
    }

    public void setBooksList(List<Books> books) {
        booksList = books;
        notifyDataSetChanged();
    }
}
