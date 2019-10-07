# Yuan-LrcView



[![](https://img.shields.io/badge/作者-jsyjst-blue.svg)](https://blog.csdn.net/qq_41979349)

简约风的歌词控件，效果图如下：

<div align="left">
<img src="https://github.com/jsyjst/Yuan-LrcView/raw/master/screenshots/gif3.gif" height="500" width="350" >
</div>

# 如何添加

### Gradle的引用方式

#### 1.在Project的build.gradle 中添加仓库地址

```
//JitPack仓库
maven { url 'https://jitpack.io' }
```

##### 示例：

```
allprojects {
	repositories {
		...
      		//JitPack仓库
		maven { url 'https://jitpack.io' }
	}
}
```

#### 2.在Module目录下的build.gradle中添加依赖

```
implementation 'com.github.jsyjst:Yuan-LrcView:LrcView-1.2'
```

##### 示例

```
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'com.android.support:appcompat-v7:28.0.0'
    implementation 'com.android.support.constraint:constraint-layout:1.1.3'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'

    implementation 'com.github.jsyjst:Yuan-LrcView:LrcView-1.2'
}
```

# 使用方法

## 1.第一步，在布局文件中声明

```xml
    <com.example.library.view.LrcView
        android:id="@+id/lrcView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:lineSpacing="40dp"
        app:textSize="18sp"
        app:lrcTextColor="@color/colorPrimary"
        app:highLineTextColor="@color/highTextColor"
        />
```

**属性说明:这些属性不设置的话为默认值**

| 属性名            | 说明                     | 默认值     |
| ----------------- | ------------------------ | ---------- |
| textSize          | 歌词大小                 | 16sp       |
| lineSpacing       | 行间距，即歌词之间的距离 | 35dp       |
| lrcTextColor      | 歌词的颜色               | Color.GRAY |
| highLineTextColor | 当前播放歌词的颜色       | Color.BLUE |

## 2.第二步，在Java代码中传入歌词字符串和MediaPlayer对象,然后调用draw方法进行绘制

```java
private LrcView lrcView;
private MediaPlayer player;
private String lrc;
.....
lrcView.setLrc(lrc).setPlayer(player).draw();
```

**说明**

传入的lrc必须是标准的歌词格式并且为字符串，目前来说只支持下列这种歌词格式：

```xml
[ti:喜欢你]
[ar:.]
[al:]
[by:]
[offset:0]
[00:00.10]喜欢你 - G.E.M. 邓紫棋 (Gem Tang)
[00:00.20]词：黄家驹
[00:00.30]曲：黄家驹
[00:00.40]编曲：Lupo Groinig
[00:00.50]
[00:12.65]细雨带风湿透黄昏的街道
[00:18.61]抹去雨水双眼无故地仰望
[00:24.04]望向孤单的晚灯
[00:26.91]
[00:27.44]是那伤感的记忆
[00:30.52]
[00:34.12]再次泛起心里无数的思念
[00:39.28]
[00:40.10]以往片刻欢笑仍挂在脸上
[00:45.49]愿你此刻可会知
[00:48.23]
[00:48.95]是我衷心的说声
[00:53.06]
[00:54.35]喜欢你 那双眼动人
[00:59.35]
[01:00.10]笑声更迷人
[01:02.37]
[01:03.15]愿再可 轻抚你
[01:08.56]
[01:09.35]那可爱面容
[01:12.40]挽手说梦话
[01:14.78]
[01:15.48]像昨天 你共我
[01:20.84]
[01:26.32]满带理想的我曾经多冲动
[01:32.45]屡怨与她相爱难有自由
[01:37.82]愿你此刻可会知
[01:40.40]
[01:41.25]是我衷心的说声
[01:44.81]
[01:46.39]喜欢你 那双眼动人
[01:51.72]
[01:52.42]笑声更迷人
[01:54.75]
[01:55.48]愿再可 轻抚你
[02:00.93]
[02:01.68]那可爱面容
[02:03.99]
[02:04.73]挽手说梦话
[02:07.13]
[02:07.82]像昨天 你共我
[02:14.53]
[02:25.54]每晚夜里自我独行
[02:29.30]随处荡 多冰冷
[02:35.40]
[02:37.83]以往为了自我挣扎
[02:41.62]从不知 她的痛苦
[02:52.02]
[02:54.11]喜欢你 那双眼动人
[03:00.13]笑声更迷人
[03:02.38]
[03:03.14]愿再可 轻抚你
[03:08.77]
[03:09.33]那可爱面容
[03:11.71]
[03:12.41]挽手说梦话
[03:14.61]
[03:15.45]像昨天 你共我
```
