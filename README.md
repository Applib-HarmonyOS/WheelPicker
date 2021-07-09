# The most popular Android open source project WheelPicker uses
---
Open source address：[https://github.com/open-android/WheelPicker](https://github.com/open-android/WheelPicker "开源项目地址")

 PS: If you think the article is too long, you can also watch the[ video of ](https://www.boxuegu.com/web/html/video.html?courseId=172&sectionId=8a2c9bed5a3a4c7e015a3bbffc6107ed&chapterId=8a2c9bed5a3a4c7e015a3adeb65b03f1&vId=8a2c9bed5a3a4c7e015a3ab4fe0601bb&videoId=F94BC8E95143C10D9C33DC5901307461)the course , dear, there are also high-definition, uncoded benefits in it.

# Running result

![](http://i.imgur.com/TPkIrBJ.gif)

* Love life, love learning, and love to be a code porter. It is more convenient to sort and search, please download the dark horse assistant app


![黑马助手.png](http://upload-images.jianshu.io/upload_images/4037105-f777f1214328dcc4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## Steps for usage

### 1. Add the following code to the build.gradle of the project (as shown below)

	allprojects {
	    repositories {
	        ...
	        maven { url "https://jitpack.io" }
	    }
	}

![](http://oi5nqn6ce.bkt.clouddn.com/itheima/booster/code/jitpack.png)


### 2. Add dependency in Module's build.gradle

     compile 'com.github.open-android:WheelPicker:v1.0.0'


### 3. Copy the following code to xml

    <com.itheima.wheelpicker.WheelPicker
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:wheel_atmospheric="true"
        app:wheel_curved="true"
        app:wheel_cyclic="true"
        app:wheel_selected_item_position="5"
        app:wheel_item_text_color="#66ff0000"
		app:wheel_selected_item_text_color="#6600ffff"/>


    <!--属性解释:
	
        wheel_atmospheric :  条目颜色是否执行衔接处理 效果更好
        wheel_curved ： 是否是弧形状态显示
        wheel_cyclic : 是否可循环
        wheel_selected_item_position ： 默认选中第几个条目
        wheel_item_text_color 未选中的条目颜色
        wheel_selected_item_text_color  选中的条目颜色-->

### 4. Just run it

#### Attention to details:

> A set of data will be included by default, so there is no need to set data. If you want to set new data, you can call the following method

		setData(List data); 

* The detailed usage method is demonstrated in DEMO. If you think this library is not bad, please reward me with a star~~~

* Welcome to follow WeChat public account

![](http://oi5nqn6ce.bkt.clouddn.com/itheima/booster/code/qrcode.png)
