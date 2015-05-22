package org.noorganization.instalist.view;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.software.shell.fab.ActionButton;

import org.noorganization.instalist.GlobalApplication;
import org.noorganization.instalist.R;
import org.noorganization.instalist.controller.IListController;
import org.noorganization.instalist.controller.implementation.ControllerFactory;
import org.noorganization.instalist.controller.implementation.ListController;
import org.noorganization.instalist.model.ShoppingList;
import org.noorganization.instalist.view.datahandler.SelectedProductDataHandler;
import org.noorganization.instalist.view.decoration.DividerItemListDecoration;
import org.noorganization.instalist.view.fragment.ProductListDialogFragment;
import org.noorganization.instalist.view.fragment.ShoppingListOverviewFragment;
import org.noorganization.instalist.view.listadapter.ShoppingListAdapter;
import org.noorganization.instalist.view.listadapter.ShoppingListOverviewAdapter;

import java.util.List;

/**
 * MainShoppingListView handles the display of an selected shoppinglist, so that the corresponding
 * items of this list are shown to the user.
 * <p/>
 * Is dependant on the selected list.
 *
 * @author TS
 */
public class MainShoppingListView extends ActionBarActivity {

    private final static String LOG_TAG = MainShoppingListView.class.getName();
    public final static String KEY_LISTNAME = "list_name";

    private Toolbar mToolbar;
    private ListView mLeftSideListView;

    /**
     * For creation an icon at the toolbar for toggling the navbar in and out.
     */
    private ActionBarDrawerToggle mNavBarToggle;

    /**
     * Layout reference of the side drawer navbar.
     */
    private DrawerLayout mDrawerLayout;

    /**
     * Title of the toolbar.
     */
    private String mTitle;

    /**
     * Name of the current list
     */
    private String mCurrentListName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_shopping_list_view);

        // init and setup toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout_container);
        mLeftSideListView = (ListView) findViewById(R.id.list_view_left_side_navigation);

        // fill the list with selectable lists
        mLeftSideListView.setAdapter(new ShoppingListOverviewAdapter(this, GlobalApplication.getInstance().getShoppingListNames()));
        mDrawerLayout.setFitsSystemWindows(true);

        // navbar custom design of toolbar
        mNavBarToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.nav_drawer_open,
                R.string.nav_drawer_close
        ) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mToolbar.setTitle(mTitle);
                // check if options menu has changed
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (mToolbar.getTitle() != null) {
                    mTitle = mToolbar.getTitle().toString();
                    mToolbar.setTitle(R.string.choose_list);
                }
                // check if options menu has changed
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mNavBarToggle);

        if (savedInstanceState == null) {
            selectList(GlobalApplication.getInstance().getShoppingListNames().get(0));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        mToolbar.inflateMenu(R.menu.menu_toolbar_main_listview);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // The action bar home/up action should open or close the navbar.
        // ActionBarDrawerToggle will take care of this.
        if (mNavBarToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // swtich which action item was pressed
        switch (id) {
            case R.id.list_items_sort_by_amount:
                // say controller there is a statechange
                break;
            case R.id.list_items_sort_by_name:
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mNavBarToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mNavBarToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {

        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

    }

    // --------------------------------------------------------------------------------
    // own public methods
    // --------------------------------------------------------------------------------

    /**
     * Creates a new fragment with the listentries of the given listname.
     *
     * @param listName, name of the list that content should be shown.
     */
    public void selectList(String listName) {

        // always close the drawer
        mDrawerLayout.closeDrawer(mLeftSideListView);

        // list is the same as the current one
        // no need to do then something
        if (listName == mCurrentListName) {
            return;
        }

        // decl
        Bundle args;
        Fragment fragment;

        // init
        mCurrentListName = listName;

        args = new Bundle();
        args.putString(KEY_LISTNAME, listName);

        fragment = new ShoppingListOverviewFragment();
        fragment.setArguments(args);

        changeFragment(fragment);
    }

    /**
     * Get the assigned toolbar reference.
     * @return the reference of the toolbar.
     */
    public Toolbar getToolbar(){
        return mToolbar;
    }

    /**
     * Get the assigned DrawerLayout, use it for locking it on fragments.
     * @return the reference to the DrawerLayout.
     */
    public DrawerLayout getDrawerLayout(){
        return mDrawerLayout;
    }
    /**
     * Changes from the current fragment to the given fragment.
     * Adds the current fragment to the backstack.
     *
     * @param fragment the fragment that should be created.
     */
    public void changeFragment(Fragment fragment) {
        // create transaction to new fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    /**
     * Sets the text of the toolbar title, when activity is updated.
     *
     * @param _Title, the title of the toolbar
     */
    public void setToolbarTitle(String _Title) {
        mTitle = _Title;
    }

    /**
     * Add the selected products to list? or say that we should update the view.
     */
    public void addProductsToList() {

    }
}
