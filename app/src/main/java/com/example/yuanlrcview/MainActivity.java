package com.example.yuanlrcview;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.library.entity.LrcBean;
import com.example.library.utils.LrcUtil;
import com.example.library.view.LrcView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LrcView lrcView;
    private MediaPlayer player;
    private String lrc =
                    "[ti:喜欢你]\n" +
                    "[ar:.]\n" +
                    "[al:]\n" +
                    "[by:]\n" +
                    "[offset:0]\n" +
                    "[00:00.10]喜欢你 - G.E.M. 邓紫棋 (Gem Tang)\n" +
                    "[00:00.20]词：黄家驹\n" +
                    "[00:00.30]曲：黄家驹\n" +
                    "[00:00.40]编曲：Lupo Groinig\n" +
                    "[00:00.50]\n" +
                    "[00:12.65]细雨带风湿透黄昏的街道\n" +
                    "[00:18.61]抹去雨水双眼无故地仰望\n" +
                    "[00:24.04]望向孤单的晚灯\n" +
                    "[00:26.91]\n" +
                    "[00:27.44]是那伤感的记忆\n" +
                    "[00:30.52]\n" +
                    "[00:34.12]再次泛起心里无数的思念\n" +
                    "[00:39.28]\n" +
                    "[00:40.10]以往片刻欢笑仍挂在脸上\n" +
                    "[00:45.49]愿你此刻可会知\n" +
                    "[00:48.23]\n" +
                    "[00:48.95]是我衷心的说声\n" +
                    "[00:53.06]\n" +
                    "[00:54.35]喜欢你 那双眼动人\n" +
                    "[00:59.35]\n" +
                    "[01:00.10]笑声更迷人\n" +
                    "[01:02.37]\n" +
                    "[01:03.15]愿再可 轻抚你\n" +
                    "[01:08.56]\n" +
                    "[01:09.35]那可爱面容\n" +
                    "[01:12.40]挽手说梦话\n" +
                    "[01:14.78]\n" +
                    "[01:15.48]像昨天 你共我\n" +
                    "[01:20.84]\n" +
                    "[01:26.32]满带理想的我曾经多冲动\n" +
                    "[01:32.45]屡怨与她相爱难有自由\n" +
                    "[01:37.82]愿你此刻可会知\n" +
                    "[01:40.40]\n" +
                    "[01:41.25]是我衷心的说声\n" +
                    "[01:44.81]\n" +
                    "[01:46.39]喜欢你 那双眼动人\n" +
                    "[01:51.72]\n" +
                    "[01:52.42]笑声更迷人\n" +
                    "[01:54.75]\n" +
                    "[01:55.48]愿再可 轻抚你\n" +
                    "[02:00.93]\n" +
                    "[02:01.68]那可爱面容\n" +
                    "[02:03.99]\n" +
                    "[02:04.73]挽手说梦话\n" +
                    "[02:07.13]\n" +
                    "[02:07.82]像昨天 你共我\n" +
                    "[02:14.53]\n" +
                    "[02:25.54]每晚夜里自我独行\n" +
                    "[02:29.30]随处荡 多冰冷\n" +
                    "[02:35.40]\n" +
                    "[02:37.83]以往为了自我挣扎\n" +
                    "[02:41.62]从不知 她的痛苦\n" +
                    "[02:52.02]\n" +
                    "[02:54.11]喜欢你 那双眼动人\n" +
                    "[03:00.13]笑声更迷人\n" +
                    "[03:02.38]\n" +
                    "[03:03.14]愿再可 轻抚你\n" +
                    "[03:08.77]\n" +
                    "[03:09.33]那可爱面容\n" +
                    "[03:11.71]\n" +
                    "[03:12.41]挽手说梦话\n" +
                    "[03:14.61]\n" +
                    "[03:15.45]像昨天 你共我";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lrcView = findViewById(R.id.lrcView);
        requestPermission();
    }

    private void initPlayer(){
        player = MediaPlayer.create(this,R.raw.love);
        player.start();
    }

    private void initLrc(){
        lrcView.setLrc(lrc).setPlayer(player).draw();
    }

    //申请权限
    private void requestPermission(){
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.
                WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }else {
            initPlayer();
            initLrc();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initPlayer();
                initLrc();
            } else {
                Toast.makeText(this, "拒绝该权限无法使用该程序", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
