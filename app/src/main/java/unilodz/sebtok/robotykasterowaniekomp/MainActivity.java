package unilodz.sebtok.robotykasterowaniekomp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import unilodz.sebtok.robotykasterowaniekomp.BluetoothModule.BluetoothActivity;
import unilodz.sebtok.robotykasterowaniekomp.MenuItems.Item;
import unilodz.sebtok.robotykasterowaniekomp.MenuItems.ItemAdapter;
import unilodz.sebtok.robotykasterowaniekomp.MenuItems.RecyclerItemOnClickListener;
import unilodz.sebtok.robotykasterowaniekomp.ReadAndWriteToFileModule.WriteToTxtFile;
import unilodz.sebtok.robotykasterowaniekomp.ShowImageModule.ShowGalleryImage;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<Item> itemList;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        itemList = new ArrayList<>();
        adapter = new ItemAdapter(this, itemList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareMenuItems();

        try {
            Glide.with(this).load(R.drawable.logo_uni).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

        recyclerView.addOnItemTouchListener(
                new RecyclerItemOnClickListener(context, recyclerView ,new RecyclerItemOnClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                       switch (position) {
                           case 0:
                               Toast.makeText(context, "BluetoothClicked", Toast.LENGTH_SHORT).show();
                               Intent intent = new Intent(MainActivity.this, BluetoothActivity.class);
                               startActivity(intent);
                               break;
                           case 1:
                               Toast.makeText(context, "NFCClicked", Toast.LENGTH_SHORT).show();
                               break;
                           case 2:
                               Toast.makeText(context, "WifiClicked", Toast.LENGTH_SHORT).show();
                               break;
                           case 3:
                               Intent intentGallery = new Intent(MainActivity.this, ShowGalleryImage.class);
                               startActivity(intentGallery);
                               break;
                           case 4:
                               Intent intentWriteToFile = new Intent(MainActivity.this, WriteToTxtFile.class);
                               startActivity(intentWriteToFile);
                               break;
                       }
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("Robotyka i Sterowanie komputerowe");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * Adding menu items
     */
    private void prepareMenuItems() {
        int[]itemLogo = new int[]{
                R.drawable.icon_bluetooth,
                R.drawable.nfc_logo,
                R.drawable.wifi_logo,
                R.drawable.gallery_logo,
                R.drawable.album5,
                R.drawable.album6
             };

        Item a = new Item("Bluetooth", itemLogo[0]);
        itemList.add(a);

        a = new Item("NFC", itemLogo[1]);
        itemList.add(a);

        a = new Item("WiFi", itemLogo[2]);
        itemList.add(a);

        a = new Item("Galeria", itemLogo[3]);
        itemList.add(a);

        a = new Item("Zapis do Pliku", itemLogo[4]);
        itemList.add(a);

        a = new Item("Odczyt Z Pliku", itemLogo[5]);
        itemList.add(a);

        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


}
