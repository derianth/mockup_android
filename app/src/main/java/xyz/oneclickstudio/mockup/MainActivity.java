package xyz.oneclickstudio.mockup;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    BannerSlider bannerSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // метрика
        //YandexMetrica.activate(getApplicationContext(), "7a747497-382c-4fc1-a551-887de4f647d0");

        bannerSlider = findViewById(R.id.banner_slider);
        bannerSlider.setIndicatorSize(0);
        //noinspection MismatchedQueryAndUpdateOfCollection
        List<Banner> banners =new ArrayList<>();
        // для локальных изображений
        // banners.add(new DrawableBanner(R.drawable.yourDrawable));
        banners.add(new RemoteBanner("http://batona.net/uploads/posts/2017-09/1504519334_05.jpg"));
        banners.add(new RemoteBanner("http://under35.me/wp-content/uploads/2016/04/Street-Art-%D0%91%D0%B0%D1%80%D1%81%D0%B5%D0%BB%D0%BE%D0%BD%D0%B0-1.jpg"));
        banners.add(new RemoteBanner("https://www.streetartnews.net/wp-content/uploads/2016/01/racoonbordalo.jpg"));
        bannerSlider.setBanners(banners);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, WebActivity.class);
            intent.putExtra("Key1", getResources().getString(R.string.web_link_settings));
            startActivity(intent);
        } else if (id == R.id.nav_news) {
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(this, WebActivity.class);
            intent.putExtra("Key1", getResources().getString(R.string.web_link_history));
            startActivity(intent);
        } else if (id == R.id.nav_help) {
            Intent intent = new Intent(this, WebActivity.class);
            intent.putExtra("Key1", getResources().getString(R.string.web_link_help));
            startActivity(intent);
        } else if (id == R.id.nav_get) {
            Intent intent = new Intent(MainActivity.this, ItemActivity.class);
            intent.putExtra("Link_XML", "news_1");
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void btn_login(View view) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("Key1", getResources().getString(R.string.web_link_login));
        startActivity(intent);
    }

    public void btn_reg(View view) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("Key1", getResources().getString(R.string.web_link_reg));
        startActivity(intent);
    }

    public void btn_promo(View view) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("Key1", getResources().getString(R.string.web_link_promo));
        startActivity(intent);
    }
}
